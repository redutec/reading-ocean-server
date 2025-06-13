package com.redutec.teachingocean.support.faq.service;

import com.redutec.core.dto.FaqDto;

public interface FaqService {
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
}