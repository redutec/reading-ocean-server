package com.redutec.core.mapper;

import com.redutec.core.dto.PolicyDto;
import com.redutec.core.criteria.PolicyCriteria;
import com.redutec.core.entity.Policy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class PolicyMapper {
    /**
     * CreatePolicyRequest DTO를 기반으로 Policy 등록 엔티티를 생성합니다.
     *
     * @param createPolicyRequest 정책 등록에 필요한 데이터를 담은 DTO
     * @return 등록할 Policy 엔티티
     */
    public Policy createEntity(PolicyDto.CreatePolicyRequest createPolicyRequest) {
        return Policy.builder()
                .domain(createPolicyRequest.domain())
                .type(createPolicyRequest.type())
                .version(StringUtils.stripToNull(createPolicyRequest.version()))
                .content(StringUtils.stripToNull(createPolicyRequest.content()))
                .effectiveAt(createPolicyRequest.effectiveAt())
                .expiresAt(createPolicyRequest.expiresAt())
                .available(createPolicyRequest.available())
                .build();
    }

    /**
     * UpdatePolicyRequest DTO를 기반으로 Policy 엔티티를 수정합니다.
     *
     * @param policy 수정할 Policy 엔티티
     * @param updatePolicyRequest 정책 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(Policy policy, PolicyDto.UpdatePolicyRequest updatePolicyRequest) {
        Optional.ofNullable(updatePolicyRequest.domain())
                .ifPresent(policy::setDomain);
        Optional.ofNullable(updatePolicyRequest.type())
                .ifPresent(policy::setType);
        Optional.ofNullable(StringUtils.stripToNull(updatePolicyRequest.version()))
                .ifPresent(policy::setVersion);
        Optional.ofNullable(StringUtils.stripToNull(updatePolicyRequest.content()))
                .ifPresent(policy::setContent);
        Optional.ofNullable(updatePolicyRequest.effectiveAt())
                .ifPresent(policy::setEffectiveAt);
        Optional.ofNullable(updatePolicyRequest.expiresAt())
                .ifPresent(policy::setExpiresAt);
        Optional.ofNullable(updatePolicyRequest.available())
                .ifPresent(policy::setAvailable);
    }

    /**
     * 이 메서드는 현재 FindPolicyRequest 객체를 기반으로
     * PolicyCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 정책 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 PolicyCriteria 객체
     */
    public PolicyCriteria toCriteria(PolicyDto.FindPolicyRequest findPolicyRequest) {
        return new PolicyCriteria(
                findPolicyRequest.policyIds(),
                findPolicyRequest.domains(),
                findPolicyRequest.types(),
                findPolicyRequest.available()
        );
    }

    /**
     * Policy 엔티티를 기반으로 응답용 PolicyResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param policy 변환할 Policy 엔티티 (null 가능)
     * @return Policy 엔티티의 데이터를 담은 PolicyResponse DTO, policy가 null이면 null 반환
     */
    public PolicyDto.PolicyResponse toResponseDto(Policy policy) {
        return Optional.ofNullable(policy)
                .map(p -> new PolicyDto.PolicyResponse(
                        p.getId(),
                        p.getDomain(),
                        p.getType(),
                        p.getVersion(),
                        p.getContent(),
                        p.getEffectiveAt(),
                        p.getExpiresAt(),
                        p.getAvailable(),
                        p.getCreatedAt(),
                        p.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Policy 엔티티 목록을 PolicyPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param policyPage Page 형태로 조회된 Policy 엔티티 목록 (null 가능)
     * @return Policy 엔티티 리스트와 페이지 정보를 담은 PolicyPageResponse DTO, policyPage가 null이면 null 반환
     */
    public PolicyDto.PolicyPageResponse toPageResponseDto(Page<Policy> policyPage) {
        return Optional.ofNullable(policyPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new PolicyDto.PolicyPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}