package com.redutec.admin.subscription.plan.service;

import com.redutec.core.dto.SubscriptionPlanDto;

public interface SubscriptionPlanService {
    /**
     * 구독 상품 등록
     * @param createSubscriptionPlanRequest 구독 상품 등록 정보를 담은 DTO
     * @return 등록된 구독 상품 정보
     */
    SubscriptionPlanDto.SubscriptionPlanResponse create(SubscriptionPlanDto.CreateSubscriptionPlanRequest createSubscriptionPlanRequest);

    /**
     * 조건에 맞는 구독 상품 목록 조회
     * @param findSubscriptionPlanRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 구독 상품 목록 및 페이징 정보
     */
    SubscriptionPlanDto.SubscriptionPlanPageResponse find(SubscriptionPlanDto.FindSubscriptionPlanRequest findSubscriptionPlanRequest);

    /**
     * 특정 구독 상품 조회
     * @param subscriptionPlanId 구독 상품 고유번호
     * @return 특정 구독 상품 응답 객체
     */
    SubscriptionPlanDto.SubscriptionPlanResponse findById(Long subscriptionPlanId);

    /**
     * 특정 구독 상품 수정
     * @param subscriptionPlanId 수정할 구독 상품의 ID
     * @param updateSubscriptionPlanRequest 구독 상품 수정 요청 객체
     */
    void update(Long subscriptionPlanId, SubscriptionPlanDto.UpdateSubscriptionPlanRequest updateSubscriptionPlanRequest);

    /**
     * 특정 구독 상품 삭제
     * @param subscriptionPlanId 삭제할 구독 상품의 ID
     */
    void delete(Long subscriptionPlanId);
}