package com.redutec.admin.subscription.student.service;

import com.redutec.core.dto.StudentSubscriptionDto;

public interface StudentSubscriptionService {
    /**
     * 구독정보(학생) 등록
     * @param createStudentSubscriptionRequest 구독정보(학생) 등록 정보를 담은 DTO
     * @return 등록된 구독정보(학생) 정보
     */
    StudentSubscriptionDto.StudentSubscriptionResponse create(StudentSubscriptionDto.CreateStudentSubscriptionRequest createStudentSubscriptionRequest);

    /**
     * 조건에 맞는 구독정보(학생) 목록 조회
     * @param findStudentSubscriptionRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 구독정보(학생) 목록 및 페이징 정보
     */
    StudentSubscriptionDto.StudentSubscriptionPageResponse find(StudentSubscriptionDto.FindStudentSubscriptionRequest findStudentSubscriptionRequest);

    /**
     * 특정 구독정보(학생) 조회
     * @param studentSubscriptionId 구독정보(학생) 고유번호
     * @return 특정 구독정보(학생) 응답 객체
     */
    StudentSubscriptionDto.StudentSubscriptionResponse get(Long studentSubscriptionId);

    /**
     * 특정 구독정보(학생) 수정
     * @param studentSubscriptionId 수정할 구독정보(학생)의 ID
     * @param updateStudentSubscriptionRequest 구독정보(학생) 수정 요청 객체
     */
    void update(Long studentSubscriptionId, StudentSubscriptionDto.UpdateStudentSubscriptionRequest updateStudentSubscriptionRequest);

    /**
     * 특정 구독정보(학생) 삭제
     * @param studentSubscriptionId 삭제할 구독정보(학생)의 ID
     */
    void delete(Long studentSubscriptionId);
}