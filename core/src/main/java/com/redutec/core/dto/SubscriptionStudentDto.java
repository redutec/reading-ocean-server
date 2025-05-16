package com.redutec.core.dto;

import com.redutec.core.meta.SubscriptionPlanStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.util.List;

public class SubscriptionStudentDto {
    @Schema(description = "구독(학생) 등록 요청 객체")
    public record CreateSubscriptionStudentRequest(
            @Schema(description = "구독 상품 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long subscriptionPlanId,

            @Schema(description = "구독자(학생) ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long studentId,

            @Schema(description = "시작일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDateTime startedAt,

            @Schema(description = "종료일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime endedAt,

            @Schema(description = "다음 결제일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime nextPaymentAt
    ) {}

    @Schema(description = "구독(학생) 조회 요청 객체")
    public record FindSubscriptionStudentRequest(
            @Schema(description = "구독(학생) ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> subscriptionStudentIds,

            @Schema(description = "학생 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> studentIds,

            @Schema(description = "구독 상품 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> subscriptionPlanIds,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "구독(학생) 수정 요청 객체")
    public record UpdateSubscriptionStudentRequest(
            @Schema(description = "구독 상품 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long subscriptionPlanId,

            @Schema(description = "구독자(학생) ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long studentId,

            @Schema(description = "시작일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime startedAt,

            @Schema(description = "종료일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime endedAt,

            @Schema(description = "다음 결제일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime nextPaymentAt
    ) {}

    @Schema(description = "구독(학생) 응답 객체")
    public record SubscriptionStudentResponse(
            Long subscriptionStudentId,
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

    @Schema(description = "구독(학생) 응답 페이징 객체")
    public record SubscriptionStudentPageResponse(
            List<SubscriptionStudentResponse> subscriptionStudents,
            Long totalElements,
            Integer totalPages
    ) {}
}