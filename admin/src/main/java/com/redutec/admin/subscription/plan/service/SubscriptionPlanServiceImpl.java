package com.redutec.admin.subscription.plan.service;

import com.redutec.admin.subscription.plan.dto.SubscriptionPlanDto;
import com.redutec.admin.subscription.plan.mapper.SubscriptionPlanMapper;
import com.redutec.core.entity.SubscriptionPlan;
import com.redutec.core.repository.SubscriptionPlanRepository;
import com.redutec.core.specification.SubscriptionPlanSpecification;
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
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {
    private final SubscriptionPlanMapper subscriptionPlanMapper;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    /**
     * 구독 상품 등록
     * @param createSubscriptionPlanRequest 구독 상품 등록 정보를 담은 DTO
     * @return 등록된 구독 상품 정보
     */
    @Override
    @Transactional
    public SubscriptionPlanDto.SubscriptionPlanResponse create(
            SubscriptionPlanDto.CreateSubscriptionPlanRequest createSubscriptionPlanRequest
    ) {
        return subscriptionPlanMapper.toResponseDto(
                subscriptionPlanRepository.save(
                        subscriptionPlanMapper.toEntity(createSubscriptionPlanRequest)
                )
        );
    }

    /**
     * 조건에 맞는 구독 상품 목록 조회
     * @param findSubscriptionPlanRequest 조회 조건을 담은 DTO
     * @return 조회된 구독 상품 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionPlanDto.SubscriptionPlanPageResponse find(
            SubscriptionPlanDto.FindSubscriptionPlanRequest findSubscriptionPlanRequest
    ) {
        return subscriptionPlanMapper.toPageResponseDto(subscriptionPlanRepository.findAll(
                SubscriptionPlanSpecification.findWith(subscriptionPlanMapper.toCriteria(findSubscriptionPlanRequest)),
                (findSubscriptionPlanRequest.page() != null && findSubscriptionPlanRequest.size() != null)
                        ? PageRequest.of(findSubscriptionPlanRequest.page(), findSubscriptionPlanRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 구독 상품 조회
     * @param subscriptionPlanId 구독 상품 고유번호
     * @return 특정 구독 상품 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionPlanDto.SubscriptionPlanResponse findById(Long subscriptionPlanId) {
        return subscriptionPlanMapper.toResponseDto(getSubscriptionPlan(subscriptionPlanId));
    }

    /**
     * 특정 구독 상품 엔티티 조회
     * @param subscriptionPlanId 구독 상품 고유번호
     * @return 특정 구독 상품 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionPlan getSubscriptionPlan(Long subscriptionPlanId) {
        return subscriptionPlanRepository.findById(subscriptionPlanId)
                .orElseThrow(() -> new EntityNotFoundException("구독 상품를 찾을 수 없습니다. id = " + subscriptionPlanId));
    }

    /**
     * 특정 구독 상품 수정
     * @param subscriptionPlanId 수정할 구독 상품의 ID
     * @param updateSubscriptionPlanRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long subscriptionPlanId,
            SubscriptionPlanDto.UpdateSubscriptionPlanRequest updateSubscriptionPlanRequest
    ) {
        // 수정할 구독 상품 엔티티 조회
        SubscriptionPlan subscriptionPlan = getSubscriptionPlan(subscriptionPlanId);
        // UPDATE 도메인 메서드로 변환
        subscriptionPlan.updateSubscriptionPlan(
                updateSubscriptionPlanRequest.name(),
                updateSubscriptionPlanRequest.details(),
                updateSubscriptionPlanRequest.price(),
                updateSubscriptionPlanRequest.discountPercentage(),
                updateSubscriptionPlanRequest.durationDays(),
                updateSubscriptionPlanRequest.status(),
                updateSubscriptionPlanRequest.autoRenew()
        );
        // 구독 상품 엔티티 UPDATE
        subscriptionPlanRepository.save(subscriptionPlan);
    }

    /**
     * 특정 구독 상품 삭제
     * @param subscriptionPlanId 삭제할 구독 상품의 ID
     */
    @Override
    @Transactional
    public void delete(Long subscriptionPlanId) {
        subscriptionPlanRepository.delete(getSubscriptionPlan(subscriptionPlanId));
    }
}