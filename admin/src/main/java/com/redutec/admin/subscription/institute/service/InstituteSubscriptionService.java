package com.redutec.admin.subscription.institute.service;

import com.redutec.core.dto.InstituteSubscriptionDto;

public interface InstituteSubscriptionService {
    /**
     * 구독정보(교육기관) 등록
     * @param createInstituteSubscriptionRequest 구독정보(교육기관) 등록 정보를 담은 DTO
     * @return 등록된 구독정보(교육기관) 정보
     */
    InstituteSubscriptionDto.InstituteSubscriptionResponse create(InstituteSubscriptionDto.CreateInstituteSubscriptionRequest createInstituteSubscriptionRequest);

    /**
     * 조건에 맞는 구독정보(교육기관) 목록 조회
     * @param findInstituteSubscriptionRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 구독정보(교육기관) 목록 및 페이징 정보
     */
    InstituteSubscriptionDto.InstituteSubscriptionPageResponse find(InstituteSubscriptionDto.FindInstituteSubscriptionRequest findInstituteSubscriptionRequest);

    /**
     * 특정 구독정보(교육기관) 조회
     * @param instituteSubscriptionId 구독정보(교육기관) 고유번호
     * @return 특정 구독정보(교육기관) 응답 객체
     */
    InstituteSubscriptionDto.InstituteSubscriptionResponse get(Long instituteSubscriptionId);

    /**
     * 특정 구독정보(교육기관) 수정
     * @param instituteSubscriptionId 수정할 구독정보(교육기관)의 ID
     * @param updateInstituteSubscriptionRequest 구독정보(교육기관) 수정 요청 객체
     */
    void update(Long instituteSubscriptionId, InstituteSubscriptionDto.UpdateInstituteSubscriptionRequest updateInstituteSubscriptionRequest);

    /**
     * 특정 구독정보(교육기관) 삭제
     * @param instituteSubscriptionId 삭제할 구독정보(교육기관)의 ID
     */
    void delete(Long instituteSubscriptionId);
}