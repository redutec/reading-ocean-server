package com.redutec.admin.subscription.institute.service;

import com.redutec.admin.subscription.institute.dto.SubscriptionInstituteDto;
import com.redutec.core.entity.SubscriptionInstitute;

public interface SubscriptionInstituteService {
    /**
     * 구독 상품 등록
     * @param createSubscriptionInstituteRequest 구독 상품 등록 정보를 담은 DTO
     * @return 등록된 구독 상품 정보
     */
    SubscriptionInstituteDto.SubscriptionInstituteResponse create(SubscriptionInstituteDto.CreateSubscriptionInstituteRequest createSubscriptionInstituteRequest);

    /**
     * 조건에 맞는 구독 상품 목록 조회
     * @param findSubscriptionInstituteRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 구독 상품 목록 및 페이징 정보
     */
    SubscriptionInstituteDto.SubscriptionInstitutePageResponse find(SubscriptionInstituteDto.FindSubscriptionInstituteRequest findSubscriptionInstituteRequest);

    /**
     * 특정 구독 상품 조회
     * @param subscriptionInstituteId 구독 상품 고유번호
     * @return 특정 구독 상품 응답 객체
     */
    SubscriptionInstituteDto.SubscriptionInstituteResponse findById(Long subscriptionInstituteId);

    /**
     * 특정 구독 상품 엔티티 조회
     * @param subscriptionInstituteId 구독 상품 고유번호
     * @return 특정 구독 상품 엔티티 객체
     */
    SubscriptionInstitute getSubscriptionInstitute(Long subscriptionInstituteId);

    /**
     * 특정 구독 상품 수정
     * @param subscriptionInstituteId 수정할 구독 상품의 ID
     * @param updateSubscriptionInstituteRequest 구독 상품 수정 요청 객체
     */
    void update(Long subscriptionInstituteId, SubscriptionInstituteDto.UpdateSubscriptionInstituteRequest updateSubscriptionInstituteRequest);

    /**
     * 특정 구독 상품 삭제
     * @param subscriptionInstituteId 삭제할 구독 상품의 ID
     */
    void delete(Long subscriptionInstituteId);
}