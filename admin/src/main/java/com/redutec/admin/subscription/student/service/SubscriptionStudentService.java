package com.redutec.admin.subscription.student.service;

import com.redutec.admin.subscription.student.dto.SubscriptionStudentDto;
import com.redutec.core.entity.SubscriptionStudent;

public interface SubscriptionStudentService {
    /**
     * 구독 상품 등록
     * @param createSubscriptionStudentRequest 구독 상품 등록 정보를 담은 DTO
     * @return 등록된 구독 상품 정보
     */
    SubscriptionStudentDto.SubscriptionStudentResponse create(SubscriptionStudentDto.CreateSubscriptionStudentRequest createSubscriptionStudentRequest);

    /**
     * 조건에 맞는 구독 상품 목록 조회
     * @param findSubscriptionStudentRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 구독 상품 목록 및 페이징 정보
     */
    SubscriptionStudentDto.SubscriptionStudentPageResponse find(SubscriptionStudentDto.FindSubscriptionStudentRequest findSubscriptionStudentRequest);

    /**
     * 특정 구독 상품 조회
     * @param subscriptionStudentId 구독 상품 고유번호
     * @return 특정 구독 상품 응답 객체
     */
    SubscriptionStudentDto.SubscriptionStudentResponse findById(Long subscriptionStudentId);

    /**
     * 특정 구독 상품 엔티티 조회
     * @param subscriptionStudentId 구독 상품 고유번호
     * @return 특정 구독 상품 엔티티 객체
     */
    SubscriptionStudent getSubscriptionStudent(Long subscriptionStudentId);

    /**
     * 특정 구독 상품 수정
     * @param subscriptionStudentId 수정할 구독 상품의 ID
     * @param updateSubscriptionStudentRequest 구독 상품 수정 요청 객체
     */
    void update(Long subscriptionStudentId, SubscriptionStudentDto.UpdateSubscriptionStudentRequest updateSubscriptionStudentRequest);

    /**
     * 특정 구독 상품 삭제
     * @param subscriptionStudentId 삭제할 구독 상품의 ID
     */
    void delete(Long subscriptionStudentId);
}