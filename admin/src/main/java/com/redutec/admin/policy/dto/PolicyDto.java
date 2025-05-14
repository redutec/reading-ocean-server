package com.redutec.admin.policy.dto;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.PolicyType;
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

public class PolicyDto {
    @Schema(description = "정책 등록 요청 객체")
    public record CreatePolicyRequest(
            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "유형", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            PolicyType type,

            @Schema(description = "버전(e.g. v1.0, 2025-05)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 50)
            String version,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            String content,

            @Schema(description = "적용 시작일시", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDateTime effectiveAt,

            @Schema(description = "적용 종료일시", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime expiresAt,

            @Schema(description = "활성화 여부", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            Boolean available
    ) {}

    @Schema(description = "정책 조회 요청 객체")
    public record FindPolicyRequest(
            @Schema(description = "정책 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> policyIds,

            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = Domain.class)
            @Enumerated(EnumType.STRING)
            List<Domain> domains,

            @Schema(description = "유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = PolicyType.class)
            @Enumerated(EnumType.STRING)
            List<PolicyType> types,

            @Schema(description = "활성화 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean available,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "정책 수정 요청 객체")
    public record UpdatePolicyRequest(
            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            PolicyType type,

            @Schema(description = "버전(e.g. v1.0, 2025-05)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 50)
            String version,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "적용 시작일시", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime effectiveAt,

            @Schema(description = "적용 종료일시", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime expiresAt,

            @Schema(description = "활성화 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean available
    ) {}

    @Schema(description = "정책 응답 객체")
    public record PolicyResponse(
            Long policyId,
            Domain domain,
            PolicyType type,
            String version,
            String content,
            LocalDateTime effectiveAt,
            LocalDateTime expiresAt,
            Boolean available,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "정책 응답 페이징 객체")
    public record PolicyPageResponse(
            List<PolicyResponse> policies,
            Long totalElements,
            Integer totalPages
    ) {}
}