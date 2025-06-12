package com.redutec.admin.subscription.student.service;

import com.redutec.core.dto.StudentSubscriptionDto;

public interface SubscriptionStudentService {
    /**
     * 구독정보(학생) 등록
     * @param createSubscriptionStudentRequest 구독정보(학생) 등록 정보를 담은 DTO
     * @return 등록된 구독정보(학생) 정보
     */
    StudentSubscriptionDto.SubscriptionStudentResponse create(StudentSubscriptionDto.CreateSubscriptionStudentRequest createSubscriptionStudentRequest);

    /**
     * 조건에 맞는 구독정보(학생) 목록 조회
     * @param findSubscriptionStudentRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 구독정보(학생) 목록 및 페이징 정보
     */
    StudentSubscriptionDto.SubscriptionStudentPageResponse find(StudentSubscriptionDto.FindSubscriptionStudentRequest findSubscriptionStudentRequest);

    /**
     * 특정 구독정보(학생) 조회
     * @param subscriptionStudentId 구독정보(학생) 고유번호
     * @return 특정 구독정보(학생) 응답 객체
     */
    StudentSubscriptionDto.SubscriptionStudentResponse findById(Long subscriptionStudentId);

    /**
     * 특정 구독정보(학생) 수정
     * @param subscriptionStudentId 수정할 구독정보(학생)의 ID
     * @param updateSubscriptionStudentRequest 구독정보(학생) 수정 요청 객체
     */
    void update(Long subscriptionStudentId, StudentSubscriptionDto.UpdateSubscriptionStudentRequest updateSubscriptionStudentRequest);

    /**
     * 특정 구독정보(학생) 삭제
     * @param subscriptionStudentId 삭제할 구독정보(학생)의 ID
     */
    void delete(Long subscriptionStudentId);
}