package com.redutec.admin.inquiry.student.service;

import com.redutec.core.dto.StudentInquiryDto;

public interface StudentInquiryService {
    /**
     * 조건에 맞는 고객문의(학생) 목록 조회
     * @param findStudentInquiryRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 고객문의(학생) 목록 및 페이징 정보
     */
    StudentInquiryDto.StudentInquiryPageResponse find(StudentInquiryDto.FindStudentInquiryRequest findStudentInquiryRequest);

    /**
     * 특정 고객문의(학생) 조회
     * @param teacherInquiryId 고객문의(학생) 고유번호
     * @return 특정 고객문의(학생) 응답 객체
     */
    StudentInquiryDto.StudentInquiryResponse findById(Long teacherInquiryId);

    /**
     * 특정 고객문의(학생) 수정
     * @param teacherInquiryId 수정할 고객문의(학생)의 ID
     * @param updateStudentInquiryRequest 고객문의(학생) 수정 요청 객체
     */
    void update(Long teacherInquiryId, StudentInquiryDto.UpdateStudentInquiryRequest updateStudentInquiryRequest);

    /**
     * 특정 고객문의(학생) 삭제
     * @param teacherInquiryId 삭제할 고객문의(학생)의 ID
     */
    void delete(Long teacherInquiryId);
}