package com.redutec.admin.subscription.institute.mapper;

import com.redutec.admin.subscription.plan.dto.SubscriptionPlanDto;
import com.redutec.core.criteria.SubscriptionPlanCriteria;
import com.redutec.core.entity.SubscriptionPlan;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class SubscriptionInstituteMapper {
    /**
     * CreateSubscriptionPlanRequest DTO를 기반으로 SubscriptionPlan 엔티티를 생성합니다.
     *
     * @param createSubscriptionPlanRequest 구독 상품 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 SubscriptionPlan 엔티티
     */
    public SubscriptionPlan toEntity(SubscriptionPlanDto.CreateSubscriptionPlanRequest createSubscriptionPlanRequest) {
        return SubscriptionPlan.builder()
                .name(createSubscriptionPlanRequest.name())
                .details(createSubscriptionPlanRequest.details())
                .price(createSubscriptionPlanRequest.price())
                .discountPercentage(createSubscriptionPlanRequest.discountPercentage())
                .durationDays(createSubscriptionPlanRequest.durationDays())
                .status(createSubscriptionPlanRequest.status())
                .autoRenew(createSubscriptionPlanRequest.autoRenew())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindSubscriptionPlanRequest 객체를 기반으로
     * SubscriptionPlanCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 구독 상품 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findSubscriptionPlanRequest 구독 상품 검색 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 SubscriptionPlanCriteria 객체
     */
    public SubscriptionPlanCriteria toCriteria(SubscriptionPlanDto.FindSubscriptionPlanRequest findSubscriptionPlanRequest) {
        return new SubscriptionPlanCriteria(
                findSubscriptionPlanRequest.subscriptionPlanIds(),
                findSubscriptionPlanRequest.name(),
                findSubscriptionPlanRequest.details(),
                findSubscriptionPlanRequest.minimumPrice(),
                findSubscriptionPlanRequest.maximumPrice(),
                findSubscriptionPlanRequest.minimumDiscountPercentage(),
                findSubscriptionPlanRequest.maximumDiscountPercentage(),
                findSubscriptionPlanRequest.maximumDurationDays(),
                findSubscriptionPlanRequest.maximumDurationDays(),
                findSubscriptionPlanRequest.statuses(),
                findSubscriptionPlanRequest.autoRenew()
        );
    }

    /**
     * SubscriptionPlan 엔티티를 기반으로 응답용 SubscriptionPlanResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param subscriptionPlan 변환할 SubscriptionPlan 엔티티 (null 가능)
     * @return SubscriptionPlan 엔티티의 데이터를 담은 SubscriptionPlanResponse DTO, subscriptionPlan가 null이면 null 반환
     */
    public SubscriptionPlanDto.SubscriptionPlanResponse toResponseDto(SubscriptionPlan subscriptionPlan) {
        // 할인율이 있다면 할인된 요금을 계산(소수점 없이 반올림 처리)
        Integer priceAfterDiscount = Optional.ofNullable(subscriptionPlan)
                .map(sp -> (int) Math.round(
                        sp.getPrice() * (100 - Optional.ofNullable(sp.getDiscountPercentage()).filter(d -> d > 0).orElse(0)) / 100.0
                ))
                .orElse(null);
        return Optional.ofNullable(subscriptionPlan)
                .map(sp -> new SubscriptionPlanDto.SubscriptionPlanResponse(
                        sp.getId(),
                        sp.getName(),
                        sp.getDetails(),
                        sp.getPrice(),
                        sp.getDiscountPercentage(),
                        priceAfterDiscount,
                        sp.getDurationDays(),
                        sp.getStatus(),
                        sp.getAutoRenew(),
                        sp.getCreatedAt(),
                        sp.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 SubscriptionPlan 엔티티 목록을 SubscriptionPlanPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param subscriptionPlanPage Page 형태로 조회된 SubscriptionPlan 엔티티 목록 (null 가능)
     * @return SubscriptionPlan 엔티티 리스트와 페이지 정보를 담은 SubscriptionPlanPageResponse DTO, subscriptionPlanPage가 null이면 null 반환
     */
    public SubscriptionPlanDto.SubscriptionPlanPageResponse toPageResponseDto(Page<SubscriptionPlan> subscriptionPlanPage) {
        return Optional.ofNullable(subscriptionPlanPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new SubscriptionPlanDto.SubscriptionPlanPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}