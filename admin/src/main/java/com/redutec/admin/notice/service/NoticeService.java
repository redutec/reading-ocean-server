package com.redutec.admin.notice.service;

import com.redutec.admin.notice.dto.NoticeDto;
import com.redutec.core.entity.Notice;

public interface NoticeService {
    /**
     * 공지사항 등록
     * @param createNoticeRequest 공지사항 등록 정보를 담은 DTO
     * @return 등록된 공지사항 정보
     */
    NoticeDto.NoticeResponse create(NoticeDto.CreateNoticeRequest createNoticeRequest);

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

    /**
     * 특정 공지사항 엔티티 조회
     * @param noticeId 공지사항 고유번호
     * @return 특정 공지사항 엔티티 객체
     */
    Notice getNotice(Long noticeId);

    /**
     * 특정 공지사항 수정
     * @param noticeId 수정할 공지사항의 ID
     * @param updateNoticeRequest 공지사항 수정 요청 객체
     */
    void update(Long noticeId, NoticeDto.UpdateNoticeRequest updateNoticeRequest);

    /**
     * 특정 공지사항 삭제
     * @param noticeId 삭제할 공지사항의 ID
     */
    void delete(Long noticeId);
}