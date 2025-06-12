package com.redutec.core.dto;

import com.redutec.core.meta.SubscriptionPlanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.List;

public class InstituteSubscriptionDto {
    @Schema(description = "구독(교육기관) 등록 요청 객체")
    public record CreateSubscriptionInstituteRequest(
            @Schema(description = "구독 상품 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long subscriptionPlanId,

            @Schema(description = "구독자(교육기관) ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long instituteId,

            @Schema(description = "시작일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDateTime startedAt,

            @Schema(description = "종료일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime endedAt,

            @Schema(description = "다음 결제일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime nextPaymentAt
    ) {}

    @Schema(description = "구독(교육기관) 조회 요청 객체")
    public record FindSubscriptionInstituteRequest(
            @Schema(description = "구독(교육기관) ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> subscriptionInstituteIds,

            @Schema(description = "교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> instituteIds,

            @Schema(description = "구독 상품 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> subscriptionPlanIds,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "구독(교육기관) 수정 요청 객체")
    public record UpdateSubscriptionInstituteRequest(
            @Schema(description = "구독 상품 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long subscriptionPlanId,

            @Schema(description = "구독자(교육기관) ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long instituteId,

            @Schema(description = "시작일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime startedAt,

            @Schema(description = "종료일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime endedAt,

            @Schema(description = "다음 결제일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime nextPaymentAt
    ) {}

    @Schema(description = "구독(교육기관) 응답 객체")
    public record SubscriptionInstituteResponse(
            Long subscriptionInstituteId,
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            LocalDateTime nextPaymentAt,
            Long subscriptionPlanId,
            String subscriptionPlanName,
            String subscriptionPlanDetails,
            Integer price,
            Integer discountPercentage,
            Integer durationDays,
            SubscriptionPlanStatus subscriptionPlanStatus,
            Boolean autoRenew,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "구독(교육기관) 응답 페이징 객체")
    public record SubscriptionInstitutePageResponse(
            List<SubscriptionInstituteResponse> subscriptionInstitutes,
            Long totalElements,
            Integer totalPages
    ) {}
}