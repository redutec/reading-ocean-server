package com.redutec.core.dto;

import com.redutec.core.meta.CurriculumStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CurriculumDto {
    @Schema(description = "커리큘럼 등록 요청 객체")
    public record CreateCurriculumRequest(
            @Schema(description = "커리큘럼을 진행할 학생 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long studentId,

            @Schema(description = "커리큘럼명", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String name,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "시작일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDate startDate,

            @Schema(description = "종료일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDate endDate,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            CurriculumStatus status
    ) {}

    @Schema(description = "커리큘럼 조회 요청 객체")
    public record FindCurriculumRequest(
            @Schema(description = "커리큘럼 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> curriculumIds,

            @Schema(description = "커리큘럼을 진행하는 학생 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> studentIds,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String name,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = CurriculumStatus.class)
            @Enumerated(EnumType.STRING)
            List<CurriculumStatus> statuses,

            @Schema(description = "커리큘럼에 배정된 도서명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String title,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "커리큘럼 수정 요청 객체")
    public record UpdateCurriculumRequest(
            @Schema(description = "커리큘럼명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String name,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "시작일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate startDate,

            @Schema(description = "종료일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate endDate,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            CurriculumStatus status
    ) {}

    @Schema(description = "커리큘럼 응답 객체")
    public record CurriculumResponse(
            Long curriculumId,
            StudentDto.StudentResponse student,
            String name,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            CurriculumStatus status,
            List<CurriculumBookDto.CurriculumBookResponse> books,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "커리큘럼 응답 페이징 객체")
    public record CurriculumPageResponse(
            List<CurriculumResponse> curriculums,
            Long totalElements,
            Integer totalPages
    ) {}
}