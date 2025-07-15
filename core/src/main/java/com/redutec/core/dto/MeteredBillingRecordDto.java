package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class MeteredBillingRecordDto {
    @Schema(description = "일별 사용료 내역 응답 객체")
    public record MeteredBillingRecordResponse(
            Long meteredBillingRecordId,
            LocalDate billingDate,
            Integer activeStudents,
            Long dailyAmount
    ) {}
}