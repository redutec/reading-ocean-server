package com.redutec.admin.inquiry.teacher.service;

import com.redutec.core.dto.TeacherInquiryDto;

public interface TeacherInquiryService {
    /**
     * 조건에 맞는 고객문의(교사) 목록 조회
     * @param findTeacherInquiryRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 고객문의(교사) 목록 및 페이징 정보
     */
    TeacherInquiryDto.TeacherInquiryPageResponse find(TeacherInquiryDto.FindTeacherInquiryRequest findTeacherInquiryRequest);

    /**
     * 특정 고객문의(교사) 조회
     * @param teacherInquiryId 고객문의(교사) 고유번호
     * @return 특정 고객문의(교사) 응답 객체
     */
    TeacherInquiryDto.TeacherInquiryResponse findById(Long teacherInquiryId);

    /**
     * 특정 고객문의(교사) 수정
     * @param teacherInquiryId 수정할 고객문의(교사)의 ID
     * @param updateTeacherInquiryRequest 고객문의(교사) 수정 요청 객체
     */
    void update(Long teacherInquiryId, TeacherInquiryDto.UpdateTeacherInquiryRequest updateTeacherInquiryRequest);

    /**
     * 특정 고객문의(교사) 삭제
     * @param teacherInquiryId 삭제할 고객문의(교사)의 ID
     */
    void delete(Long teacherInquiryId);
}