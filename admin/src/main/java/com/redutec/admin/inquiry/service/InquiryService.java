package com.redutec.admin.inquiry.service;

import com.redutec.core.dto.InquiryDto;

public interface InquiryService {
    /**
     * 고객문의 등록
     * @param createInquiryRequest 고객문의 등록 정보를 담은 DTO
     * @return 등록된 고객문의 정보
     */
    InquiryDto.InquiryResponse create(InquiryDto.CreateInquiryRequest createInquiryRequest);

    /**
     * 조건에 맞는 고객문의 목록 조회
     * @param findInquiryRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 고객문의 목록 및 페이징 정보
     */
    InquiryDto.InquiryPageResponse find(InquiryDto.FindInquiryRequest findInquiryRequest);

    /**
     * 특정 고객문의 조회
     * @param inquiryId 고객문의 고유번호
     * @return 특정 고객문의 응답 객체
     */
    InquiryDto.InquiryResponse findById(Long inquiryId);

    /**
     * 특정 고객문의 수정
     * @param inquiryId 수정할 고객문의의 ID
     * @param updateInquiryRequest 고객문의 수정 요청 객체
     */
    void update(Long inquiryId, InquiryDto.UpdateInquiryRequest updateInquiryRequest);

    /**
     * 특정 고객문의 삭제
     * @param inquiryId 삭제할 고객문의의 ID
     */
    void delete(Long inquiryId);
}