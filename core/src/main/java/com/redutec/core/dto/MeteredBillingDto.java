package com.redutec.core.dto;

import com.redutec.core.meta.BillingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public class MeteredBillingDto {
    @Schema(description = "월별 사용료 청구서 조회 요청 객체")
    public record FindMeteredBillingRequest(
            @Schema(description = "월별 사용료 청구서 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> meteredBillingIds,

            @Schema(description = "교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> instituteIds,

            @Schema(description = "사용료 청구월", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<YearMonth> billingPeriodMonths,

            @Schema(description = "청구 상태: 결제 대기/PENDING, 결제 완료/PAID, 미납/UNPAID, 환불/REFUNDED", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BillingStatus.class)
            @Enumerated(EnumType.STRING)
            List<BillingStatus> billingStatuses,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "월별 사용료 청구서 응답 객체")
    public record MeteredBillingResponse(
            Long meteredBillingId,
            LocalDate billingPeriodStart,
            LocalDate billingPeriodEnd,
            Long invoiceAmount,
            BillingStatus billingStatus,
            LocalDate paymentDueDate,
            LocalDateTime paymentAt,
            LocalDateTime refundAt,
            List<MeteredBillingRecordDto.MeteredBillingRecordResponse> meteredBillingRecordResponses,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "월별 사용료 청구서 응답 페이징 객체")
    public record MeteredBillingPageResponse(
            List<MeteredBillingResponse> meteredBillings,
            Long totalElements,
            Integer totalPages
    ) {}
}