package com.redutec.teachingocean.notice.service;

import com.redutec.core.dto.NoticeDto;

public interface NoticeService {
    /**
     * 조건에 맞는 공지사항 목록 조회
     * @param findNoticeRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 공지사항 목록 및 페이징 정보
     */
    NoticeDto.NoticePageResponse find(NoticeDto.FindNoticeRequest findNoticeRequest);

    /**
     * 특정 공지사항 조회
     * @param noticeId 공지사항 고유번호
     * @return 특정 공지사항 응답 객체
     */
    NoticeDto.NoticeResponse findById(Long noticeId);
}