package com.redutec.admin.subscription.institute.service;

import com.redutec.core.dto.InstituteSubscriptionDto;

public interface SubscriptionInstituteService {
    /**
     * 구독정보(교육기관) 등록
     * @param createSubscriptionInstituteRequest 구독정보(교육기관) 등록 정보를 담은 DTO
     * @return 등록된 구독정보(교육기관) 정보
     */
    InstituteSubscriptionDto.SubscriptionInstituteResponse create(InstituteSubscriptionDto.CreateSubscriptionInstituteRequest createSubscriptionInstituteRequest);

    /**
     * 조건에 맞는 구독정보(교육기관) 목록 조회
     * @param findSubscriptionInstituteRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 구독정보(교육기관) 목록 및 페이징 정보
     */
    InstituteSubscriptionDto.SubscriptionInstitutePageResponse find(InstituteSubscriptionDto.FindSubscriptionInstituteRequest findSubscriptionInstituteRequest);

    /**
     * 특정 구독정보(교육기관) 조회
     * @param subscriptionInstituteId 구독정보(교육기관) 고유번호
     * @return 특정 구독정보(교육기관) 응답 객체
     */
    InstituteSubscriptionDto.SubscriptionInstituteResponse findById(Long subscriptionInstituteId);

    /**
     * 특정 구독정보(교육기관) 수정
     * @param subscriptionInstituteId 수정할 구독정보(교육기관)의 ID
     * @param updateSubscriptionInstituteRequest 구독정보(교육기관) 수정 요청 객체
     */
    void update(Long subscriptionInstituteId, InstituteSubscriptionDto.UpdateSubscriptionInstituteRequest updateSubscriptionInstituteRequest);

    /**
     * 특정 구독정보(교육기관) 삭제
     * @param subscriptionInstituteId 삭제할 구독정보(교육기관)의 ID
     */
    void delete(Long subscriptionInstituteId);
}