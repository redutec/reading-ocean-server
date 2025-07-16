package com.redutec.admin.policy.service;

import com.redutec.core.dto.PolicyDto;

public interface PolicyService {
    /**
     * 정책 등록
     * @param createPolicyRequest 정책 등록 정보를 담은 DTO
     * @return 등록된 정책 정보
     */
    PolicyDto.PolicyResponse create(PolicyDto.CreatePolicyRequest createPolicyRequest);

    /**
     * 조건에 맞는 정책 목록 조회
     * @param findPolicyRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 정책 목록 및 페이징 정보
     */
    PolicyDto.PolicyPageResponse find(PolicyDto.FindPolicyRequest findPolicyRequest);

    /**
     * 특정 정책 조회
     * @param policyId 정책 고유번호
     * @return 특정 정책 응답 객체
     */
    PolicyDto.PolicyResponse get(Long policyId);

    /**
     * 특정 정책 수정
     * @param policyId 수정할 정책의 ID
     * @param updatePolicyRequest 정책 수정 요청 객체
     */
    void update(Long policyId, PolicyDto.UpdatePolicyRequest updatePolicyRequest);

    /**
     * 특정 정책 삭제
     * @param policyId 삭제할 정책의 ID
     */
    void delete(Long policyId);
}