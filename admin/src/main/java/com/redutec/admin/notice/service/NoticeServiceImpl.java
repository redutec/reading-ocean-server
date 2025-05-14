package com.redutec.admin.notice.service;

import com.redutec.admin.notice.dto.NoticeDto;
import com.redutec.admin.notice.mapper.NoticeMapper;
import com.redutec.admin.notice.service.NoticeService;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.Notice;
import com.redutec.core.repository.NoticeRepository;
import com.redutec.core.specification.NoticeSpecification;
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
public class NoticeServiceImpl implements NoticeService {
    private final NoticeMapper noticeMapper;
    private final NoticeRepository noticeRepository;
    private final FileUtil fileUtil;

    /**
     * 공지사항 등록
     * @param createNoticeRequest 공지사항 등록 정보를 담은 DTO
     * @return 등록된 공지사항 정보
     */
    @Override
    @Transactional
    public NoticeDto.NoticeResponse create(NoticeDto.CreateNoticeRequest createNoticeRequest) {
        return noticeMapper.toResponseDto(noticeRepository.save(noticeMapper.toEntity(createNoticeRequest)));
    }

    /**
     * 조건에 맞는 공지사항 목록 조회
     * @param findNoticeRequest 조회 조건을 담은 DTO
     * @return 조회된 공지사항 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public NoticeDto.NoticePageResponse find(
            NoticeDto.FindNoticeRequest findNoticeRequest
    ) {
        return noticeMapper.toPageResponseDto(noticeRepository.findAll(
                NoticeSpecification.findWith(noticeMapper.toCriteria(findNoticeRequest)),
                (findNoticeRequest.page() != null && findNoticeRequest.size() != null)
                        ? PageRequest.of(findNoticeRequest.page(), findNoticeRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 공지사항 조회
     * @param noticeId 공지사항 고유번호
     * @return 특정 공지사항 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public NoticeDto.NoticeResponse findById(Long noticeId) {
        return noticeMapper.toResponseDto(getNotice(noticeId));
    }

    /**
     * 특정 공지사항 엔티티 조회
     * @param noticeId 공지사항 고유번호
     * @return 특정 공지사항 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Notice getNotice(
            Long noticeId
    ) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new EntityNotFoundException("공지사항을 찾을 수 없습니다. id = " + noticeId));
    }

    /**
     * 특정 공지사항 수정
     * @param noticeId 수정할 공지사항의 ID
     * @param updateNoticeRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long noticeId, NoticeDto.UpdateNoticeRequest updateNoticeRequest) {
        // 수정할 공지사항 엔티티 조회
        Notice notice = getNotice(noticeId);
        // 업로드할 첨부 파일이 있는 경우 업로드하고 파일명을 생성
        String attachedFileName = Optional.ofNullable(updateNoticeRequest.attachedFile())
                .filter(attachedFile -> !attachedFile.isEmpty())
                .map(attachedFile -> {
                    FileUploadResult result = fileUtil.uploadFile(attachedFile, "/notice");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // UPDATE 도메인 메서드로 변환
        notice.updateNotice(
                updateNoticeRequest.domain(),
                updateNoticeRequest.title(),
                updateNoticeRequest.content(),
                attachedFileName,
                updateNoticeRequest.visible(),
                updateNoticeRequest.visibleStartAt(),
                updateNoticeRequest.visibleEndAt()
        );
        // 공지사항 엔티티 UPDATE
        noticeRepository.save(notice);
    }

    /**
     * 특정 공지사항 삭제
     * @param noticeId 삭제할 공지사항의 ID
     */
    @Override
    @Transactional
    public void delete(Long noticeId) {
        noticeRepository.delete(getNotice(noticeId));
    }
}