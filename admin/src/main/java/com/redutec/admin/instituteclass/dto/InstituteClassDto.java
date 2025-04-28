package com.redutec.admin.instituteclass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class InstituteClassDto {
    @Schema(description = "학급 등록 요청 객체")
    public record CreateInstituteClassRequest(
            @Schema(description = "학급명", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 20)
            String name,

            @Schema(description = "교육기관 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long instituteId,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description
    ) {}

    @Schema(description = "학급 조회 요청 객체")
    public record FindInstituteClassRequest(
            @Schema(description = "학급 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> instituteClassIds,

            @Schema(description = "학급명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String name,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> instituteIds,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "학급 수정 요청 객체")
    public record UpdateInstituteClassRequest(
            @Schema(description = "학급명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String name,

            @Schema(description = "교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long instituteId,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description
    ) {}

    @Schema(description = "학급 응답 객체")
    public record InstituteClassResponse(
            Long instituteClassId,
            String name,
            String description,
            Long instituteId,
            String instituteName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "학급 응답 페이징 객체")
    public record InstituteClassPageResponse(
            List<InstituteClassResponse> instituteClasses,
            Long totalElements,
            Integer totalPages
    ) {}
}