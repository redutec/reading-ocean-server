package com.redutec.admin.banner.service;

import com.redutec.core.dto.BannerDto;
import com.redutec.core.mapper.BannerMapper;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.Banner;
import com.redutec.core.repository.BannerRepository;
import com.redutec.core.specification.BannerSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param createBannerRequest 배너 등록 정보를 담은 DTO
     * @return 등록된 배너 정보
     */
    @Override
    @Transactional
    public BannerDto.BannerResponse create(BannerDto.CreateBannerRequest createBannerRequest) {
        // 첨부 파일이 존재하는 경우 파일을 업로드하고 파일명을 가져오기(파일이 없으면 파일명은 null)
        String attachmentFileName = Optional.ofNullable(createBannerRequest.attachmentFile())
                .filter(attachmentFile -> !attachmentFile.isEmpty())
                .map(attachmentFile -> {
                    FileUploadResult result = fileUtil.uploadFile(attachmentFile, "/banner");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
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
     * 특정 배너 조회
     * @param bannerId 배너 고유번호
     * @return 특정 배너 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BannerDto.BannerResponse get(Long bannerId) {
        return bannerMapper.toResponseDto(getBanner(bannerId));
    }

    /**
     * 특정 배너 수정
     * @param bannerId 수정할 배너의 ID
     * @param updateBannerRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long bannerId, BannerDto.UpdateBannerRequest updateBannerRequest) {
        // 수정할 배너 엔티티 조회
        Banner banner = getBanner(bannerId);
        // 배너 수정
        bannerMapper.updateEntity(
                banner,
                updateBannerRequest,
                Optional.ofNullable(updateBannerRequest.attachmentFile())
                        .filter(attachmentFile -> !attachmentFile.isEmpty())
                        .map(attachmentFile -> {
                            FileUploadResult result = fileUtil.uploadFile(attachmentFile, "/banner");
                            return Paths.get(result.filePath()).getFileName().toString();
                        })
                        .orElseGet(banner::getAttachmentFileName)
        );
    }

    /**
     * 특정 배너 삭제
     * @param bannerId 삭제할 배너의 ID
     */
    @Override
    @Transactional
    public void delete(Long bannerId) {
        bannerRepository.delete(getBanner(bannerId));
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
}