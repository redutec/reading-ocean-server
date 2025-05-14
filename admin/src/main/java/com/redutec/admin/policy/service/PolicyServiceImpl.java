package com.redutec.admin.policy.service;

import com.redutec.admin.policy.dto.PolicyDto;
import com.redutec.admin.policy.mapper.PolicyMapper;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.Policy;
import com.redutec.core.repository.PolicyRepository;
import com.redutec.core.specification.PolicySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class PolicyServiceImpl implements PolicyService {
    private final PolicyMapper policyMapper;
    private final PolicyRepository policyRepository;
    private final FileUtil fileUtil;

    /**
     * 정책 등록
     * @param createPolicyRequest 정책 등록 정보를 담은 DTO
     * @return 등록된 정책 정보
     */
    @Override
    @Transactional
    public PolicyDto.PolicyResponse create(PolicyDto.CreatePolicyRequest createPolicyRequest) {
        return policyMapper.toResponseDto(policyRepository.save(policyMapper.toEntity(createPolicyRequest)));
    }

    /**
     * 조건에 맞는 정책 목록 조회
     * @param findPolicyRequest 조회 조건을 담은 DTO
     * @return 조회된 정책 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public PolicyDto.PolicyPageResponse find(
            PolicyDto.FindPolicyRequest findPolicyRequest
    ) {
        return policyMapper.toPageResponseDto(policyRepository.findAll(
                PolicySpecification.findWith(policyMapper.toCriteria(findPolicyRequest)),
                (findPolicyRequest.page() != null && findPolicyRequest.size() != null)
                        ? PageRequest.of(findPolicyRequest.page(), findPolicyRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 정책 조회
     * @param policyId 정책 고유번호
     * @return 특정 정책 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public PolicyDto.PolicyResponse findById(Long policyId) {
        return policyMapper.toResponseDto(getPolicy(policyId));
    }

    /**
     * 특정 정책 엔티티 조회
     * @param policyId 정책 고유번호
     * @return 특정 정책 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Policy getPolicy(
            Long policyId
    ) {
        return policyRepository.findById(policyId)
                .orElseThrow(() -> new EntityNotFoundException("정책을 찾을 수 없습니다. id = " + policyId));
    }

    /**
     * 특정 정책 수정
     * @param policyId 수정할 정책의 ID
     * @param updatePolicyRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long policyId, PolicyDto.UpdatePolicyRequest updatePolicyRequest) {
        // 수정할 정책 엔티티 조회
        Policy policy = getPolicy(policyId);
        // UPDATE 도메인 메서드로 변환
        policy.updatePolicy(
                updatePolicyRequest.domain(),
                updatePolicyRequest.type(),
                updatePolicyRequest.version(),
                updatePolicyRequest.content(),
                updatePolicyRequest.effectiveAt(),
                updatePolicyRequest.expiresAt(),
                updatePolicyRequest.available()
        );
        // 정책 엔티티 UPDATE
        policyRepository.save(policy);
    }

    /**
     * 특정 정책 삭제
     * @param policyId 삭제할 정책의 ID
     */
    @Override
    @Transactional
    public void delete(Long policyId) {
        policyRepository.delete(getPolicy(policyId));
    }
}