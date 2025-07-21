package com.redutec.core.dto;

import com.redutec.core.meta.ReadingStatus;
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

public class CurriculumBookDto {
    @Schema(description = "커리큘럼에 소속할 도서 등록 요청 객체")
    public record CreateCurriculumBookRequest(
            @Schema(description = "소속할 커리큘럼 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long curriculumBookId,

            @Schema(description = "읽어야 할 도서 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long bookId,

            @Schema(description = "독서 순서", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Integer readingOrder,

            @Schema(description = "독서 예정일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDate plannedReadingDate,

            @Schema(description = "독서 상태", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            ReadingStatus readingStatus
    ) {}

    @Schema(description = "커리큘럼에 소속된 도서 조회 요청 객체")
    public record FindCurriculumBookRequest(
            @Schema(description = "커리큘럼에 소속된 도서 배정 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> curriculumBookIds,

            @Schema(description = "소속한 커리큘럼 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> curriculumIds,

            @Schema(description = "도서 제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String title,

            @Schema(description = "독서 예정일(시작)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate plannedReadingStartDate,

            @Schema(description = "독서 예정일(종료)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate plannedReadingEndDate,

            @Schema(description = "독서 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = ReadingStatus.class)
            @Enumerated(EnumType.STRING)
            List<ReadingStatus> readingStatuses,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "커리큘럼에 소속된 도서 수정 요청 객체")
    public record UpdateCurriculumBookRequest(
            @Schema(description = "소속할 커리큘럼 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long curriculumBookId,

            @Schema(description = "읽어야 할 도서 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long bookId,

            @Schema(description = "독서 순서", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Integer readingOrder,

            @Schema(description = "독서 예정일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDate plannedReadingDate,

            @Schema(description = "독서 상태", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            ReadingStatus readingStatus
    ) {}

    @Schema(description = "커리큘럼에 소속된 도서 응답 객체")
    public record CurriculumBookResponse(
            Long curriculumBookId,
            Long curriculumId,
            BookDto.BookResponse book,
            Integer readingOrder,
            LocalDate plannedReadingDate,
            ReadingStatus readingStatus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "커리큘럼에 소속된 도서 응답 페이징 객체")
    public record CurriculumPageResponse(
            List<CurriculumBookResponse> curriculums,
            Long totalElements,
            Integer totalPages
    ) {}
}