package com.redutec.teachingocean.support.inquiry.service;

import com.redutec.core.dto.TeacherInquiryDto;

public interface InquiryService {
    /**
     * 고객센터 - 1:1 문의 - 고객문의 등록
     * @param createTeacherInquiryRequest 고객문의 등록 정보를 담은 DTO
     * @return 등록된 고객문의 정보
     */
    TeacherInquiryDto.TeacherInquiryResponse create(TeacherInquiryDto.CreateTeacherInquiryRequest createTeacherInquiryRequest);

    /**
     * 고객센터 - 1:1 문의 - 고객문의 목록 조회
     * @param findTeacherInquiryRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교사가 등록한 고객문의
     */
    TeacherInquiryDto.TeacherInquiryPageResponse find(TeacherInquiryDto.FindTeacherInquiryRequest findTeacherInquiryRequest);

    /**
     * 고객센터 - 1:1 문의 - 특정 고객문의 조회
     * @param teacherInquiryId 고객문의 고유번호
     * @return 특정 고객문의 응답 객체
     */
    TeacherInquiryDto.TeacherInquiryResponse get(Long teacherInquiryId);

    /**
     * 고객센터 - 1:1 문의 - 특정 고객문의 수정
     * @param teacherInquiryId 수정할 고객문의의 고유번호
     * @param updateTeacherInquiryRequest 고객문의 수정 요청 객체
     */
    void update(Long teacherInquiryId, TeacherInquiryDto.UpdateTeacherInquiryRequest updateTeacherInquiryRequest);
}