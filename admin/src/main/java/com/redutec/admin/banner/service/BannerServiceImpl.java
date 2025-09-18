package com.redutec.admin.banner.service;

import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.dto.BannerDto;
import com.redutec.core.entity.Banner;
import com.redutec.core.mapper.BannerMapper;
import com.redutec.core.repository.BannerRepository;
import com.redutec.core.specification.BannerSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerMapper bannerMapper;
    private final BannerRepository bannerRepository;
    private final FileUtil fileUtil;

    /**
     * 배너 등록
     * - 트랜잭션 롤백 시 업로드된 파일 삭제
     * @param createBannerRequest 배너 등록 정보를 담은 DTO
     * @return 등록된 배너 정보
     */
    @Override
    @Transactional
    public BannerDto.BannerResponse create(BannerDto.CreateBannerRequest createBannerRequest) {
        // 업로드 폴더 지정
        String addPath = "/banner";
        // 첨부 파일이 존재하는 경우 파일을 업로드하고 파일명을 가져오기(파일이 없으면 파일명은 null)
        String attachmentFileName = Optional.ofNullable(createBannerRequest.attachmentFile())
                .filter(attachmentFile -> !attachmentFile.isEmpty())
                .map(attachmentFile -> {
                    FileUploadResult result = fileUtil.uploadFile(attachmentFile, addPath);
                    String newFileName = Paths.get(result.filePath()).getFileName().toString();
                    // 트랜잭션 롤백 시 방금 업로드한 파일 삭제 훅 등록
                    registerRollbackDelete(addPath, newFileName);
                    return newFileName;
                })
                .orElse(null);
        // 배너 등록 후 응답 객체로 리턴
        return bannerMapper.toResponseDto(bannerRepository.save(bannerMapper.createEntity(
                createBannerRequest,
                attachmentFileName
        )));
    }

    /**
     * 조건에 맞는 배너 목록 조회
     * @param findBannerRequest 조회 조건을 담은 DTO
     * @return 조회된 배너 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BannerDto.BannerPageResponse find(BannerDto.FindBannerRequest findBannerRequest) {
        return bannerMapper.toPageResponseDto(bannerRepository.findAll(
                BannerSpecification.findWith(bannerMapper.toCriteria(findBannerRequest)),
                (findBannerRequest.page() != null && findBannerRequest.size() != null)
                        ? PageRequest.of(findBannerRequest.page(), findBannerRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 배너 조회
     * @param bannerId 배너 고유번호
     * @return 특정 배너 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BannerDto.BannerResponse get(Long bannerId) {
        return bannerMapper.toResponseDto(getBanner(bannerId));
    }

    /**
     * 배너 수정
     * - 새 파일 업로드 시: 롤백 시 새 파일 삭제, 커밋 시 기존 파일 삭제
     * @param bannerId 수정할 배너의 ID
     * @param updateBannerRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long bannerId, BannerDto.UpdateBannerRequest updateBannerRequest) {
        // 수정할 배너 엔티티 조회
        Banner banner = getBanner(bannerId);
        // 배너 수정
        String addPath = "/banner";
        bannerMapper.updateEntity(
                banner,
                updateBannerRequest,
                Optional.ofNullable(updateBannerRequest.attachmentFile())
                        .filter(attachmentFile -> !attachmentFile.isEmpty())
                        .map(attachmentFile -> {
                            FileUploadResult result = fileUtil.uploadFile(attachmentFile, addPath);
                            String newFileName = Paths.get(result.filePath()).getFileName().toString();
                            // 롤백 시 새 파일 삭제
                            registerRollbackDelete(addPath, newFileName);
                            // 커밋 시 이전 파일 삭제(파일명이 변경된 경우에만)
                            registerAfterCommitDelete(addPath, banner.getAttachmentFileName(), newFileName);
                            return newFileName;
                        })
                        .orElseGet(banner::getAttachmentFileName)
        );
    }

    /**
     * 배너 삭제
     * - DB 삭제 커밋 이후 실제 파일 삭제
     * @param bannerId 삭제할 배너의 ID
     */
    @Override
    @Transactional
    public void delete(Long bannerId) {
        Banner banner = getBanner(bannerId);
        String fileName = banner.getAttachmentFileName();
        // 엔티티 삭제
        bannerRepository.delete(banner);
        // 커밋 성공 후 물리 파일 삭제
        String addPath = "/banner";
        if (fileName != null && TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    fileUtil.deleteFile(addPath, fileName);
                }
            });
        }
    }

    /**
     * 특정 배너 엔티티 조회
     * @param bannerId 배너 고유번호
     * @return 특정 배너 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Banner getBanner(Long bannerId) {
        return bannerRepository.findById(bannerId)
                .orElseThrow(() -> new EntityNotFoundException("배너를 찾을 수 없습니다. bannerId: " + bannerId));
    }

    /**
     * 트랜잭션 롤백 시 업로드된 새 파일을 삭제하도록 훅을 등록한다.
     *
     * @param addPath     업로드 경로 (예: "/banner")
     * @param newFileName 새로 업로드된 파일명
     */
    private void registerRollbackDelete(String addPath, String newFileName) {
        // 트랜잭션 동기화가 활성화되지 않은 경우 훅을 등록하지 않음
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            return;
        }
        // 트랜잭션 완료 시점(afterCompletion)에 실행될 동작을 등록
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                // 트랜잭션 상태에 따라 파일 처리
                switch (status) {
                    // 롤백된 경우 방금 업로드된 새 파일 삭제
                    case STATUS_ROLLED_BACK -> fileUtil.deleteFile(addPath, newFileName);
                    // 커밋된 경우에는 별도 작업 없음
                    case STATUS_COMMITTED -> { /* 커밋 시 작업 없음 */ }
                    // 그 외 상태는 무시
                    default -> { /* 기타 상태 무시 */ }
                }
            }
        });
    }

    /**
     * 트랜잭션 커밋 후 이전 파일을 삭제하도록 훅을 등록한다.
     * 단, 이전 파일명이 null이 아니고, 새 파일명과 다른 경우에만 동작한다.
     *
     * @param addPath     업로드 경로 (예: "/banner")
     * @param oldFileName 기존 파일명
     * @param newFileName 새로 업로드된 파일명
     */
    private void registerAfterCommitDelete(String addPath, String oldFileName, String newFileName) {
        // 이전 파일명이 없거나, 새 파일명과 동일하다면 삭제할 필요가 없음
        if (oldFileName == null || oldFileName.equals(newFileName)) {
            return;
        }
        // 트랜잭션 동기화가 활성화되지 않은 경우 훅을 등록하지 않음
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            return;
        }
        // 트랜잭션 커밋 시점(afterCommit)에 실행될 동작을 등록
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 커밋이 완료되면 이전 파일 삭제
                fileUtil.deleteFile(addPath, oldFileName);
            }
        });
    }
}