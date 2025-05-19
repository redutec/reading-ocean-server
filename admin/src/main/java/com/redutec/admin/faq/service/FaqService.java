package com.redutec.admin.faq.service;

import com.redutec.core.dto.FaqDto;

public interface FaqService {
    /**
     * 이용안내 등록
     * @param createFaqRequest 이용안내 등록 정보를 담은 DTO
     * @return 등록된 이용안내 정보
     */
    FaqDto.FaqResponse create(FaqDto.CreateFaqRequest createFaqRequest);

    /**
     * 조건에 맞는 이용안내 목록 조회
     * @param findFaqRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 이용안내 목록 및 페이징 정보
     */
    FaqDto.FaqPageResponse find(FaqDto.FindFaqRequest findFaqRequest);

    /**
     * 특정 이용안내 조회
     * @param faqId 이용안내 고유번호
     * @return 특정 이용안내 응답 객체
     */
    FaqDto.FaqResponse findById(Long faqId);

    /**
     * 특정 이용안내 수정
     * @param faqId 수정할 이용안내의 ID
     * @param updateFaqRequest 이용안내 수정 요청 객체
     */
    void update(Long faqId, FaqDto.UpdateFaqRequest updateFaqRequest);

    /**
     * 특정 이용안내 삭제
     * @param faqId 삭제할 이용안내의 ID
     */
    void delete(Long faqId);
}