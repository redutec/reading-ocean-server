package com.redutec.teachingocean.support.notice.service;

import com.redutec.core.repository.NoticeRepository;
import com.redutec.core.specification.NoticeSpecification;
import com.redutec.core.dto.NoticeDto;
import com.redutec.core.mapper.NoticeMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeMapper noticeMapper;
    private final NoticeRepository noticeRepository;

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
        return noticeMapper.toResponseDto(
                noticeRepository.findById(noticeId)
                        .orElseThrow(() -> new EntityNotFoundException("공지사항을 찾을 수 없습니다. noticeId: " + noticeId)));
    }
}