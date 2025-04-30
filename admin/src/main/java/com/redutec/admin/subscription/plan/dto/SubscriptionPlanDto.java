package com.redutec.admin.subscription.plan.dto;

import com.redutec.core.meta.SubscriptionPlanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class SubscriptionPlanDto {
    @Schema(description = "구독 상품 등록 요청 객체")
    public record CreateSubscriptionPlanRequest(
            @Schema(description = "상품명", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String name,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 1000)
            String details,

            @Schema(description = "가격", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @PositiveOrZero
            Integer price,

            @Schema(description = "할인율", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            @Max(100)
            Integer discountPercentage,

            @Schema(description = "지속일", requiredMode = Schema.RequiredMode.REQUIRED)
            @PositiveOrZero
            Integer durationDays,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            SubscriptionPlanStatus status,

            @Schema(description = "자동 갱신 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @NotNull
            Boolean autoRenew
    ) {}

    @Schema(description = "구독 상품 조회 요청 객체")
    public record FindSubscriptionPlanRequest(
            @Schema(description = "구독 상품 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> subscriptionPlanIds,

            @Schema(description = "상품명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String name,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 1000)
            String details,

            @Schema(description = "최소 가격", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer minimumPrice,

            @Schema(description = "최대 가격", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer maximumPrice,

            @Schema(description = "최소 할인율", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            @Max(100)
            Integer minimumDiscountPercentage,

            @Schema(description = "최대 할인율", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            @Max(100)
            Integer maximumDiscountPercentage,

            @Schema(description = "최소 구독일수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer minimumDurationDays,

            @Schema(description = "최대 구독일수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer maximumDurationDays,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = SubscriptionPlanStatus.class)
            @Enumerated(EnumType.STRING)
            List<SubscriptionPlanStatus> statuses,

            @Schema(description = "자동 갱신 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean autoRenew,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "구독 상품 수정 요청 객체")
    public record UpdateSubscriptionPlanRequest(
            @Schema(description = "상품명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String name,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 1000)
            String details,

            @Schema(description = "가격", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer price,

            @Schema(description = "할인율", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            @Max(100)
            Integer discountPercentage,

            @Schema(description = "지속일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer durationDays,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            SubscriptionPlanStatus status,

            @Schema(description = "자동 갱신 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean autoRenew
    ) {}

    @Schema(description = "구독 상품 응답 객체")
    public record SubscriptionPlanResponse(
            Long subscriptionPlanId,
            String name,
            String details,
            Integer price,
            Integer discountPercentage,
            Integer priceAfterDiscount,
            Integer durationDays,
            SubscriptionPlanStatus status,
            Boolean autoRenew,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "구독 상품 응답 페이징 객체")
    public record SubscriptionPlanPageResponse(
            List<SubscriptionPlanResponse> subscriptionPlans,
            Long totalElements,
            Integer totalPages
    ) {}
}