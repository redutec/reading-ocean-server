package com.redutec.admin.inquiry.dto;

import com.redutec.core.meta.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryDto {
    @Schema(description = "고객문의 등록 요청 객체")
    public record CreateInquiryRequest(
            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            String content,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @NotNull
            Boolean visible,

            @Schema(description = "노출 시작일시", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDateTime visibleStartAt,

            @Schema(description = "노출 종료일시", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDateTime visibleEndAt
    ) {}

    @Schema(description = "고객문의 조회 요청 객체")
    public record FindInquiryRequest(
            @Schema(description = "고객문의 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> inquiryIds,

            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = Domain.class)
            @Enumerated(EnumType.STRING)
            List<Domain> domains,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean visible,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "고객문의 수정 요청 객체")
    public record UpdateInquiryRequest(
            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "true")
            Boolean visible
    ) {}

    @Schema(description = "고객문의 응답 객체")
    public record InquiryResponse(
            Long inquiryId,
            Domain domain,
            String title,
            String content,
            Boolean visible,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "고객문의 응답 페이징 객체")
    public record InquiryPageResponse(
            List<InquiryResponse> inquiries,
            Long totalElements,
            Integer totalPages
    ) {}
}