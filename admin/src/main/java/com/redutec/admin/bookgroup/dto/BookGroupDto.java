package com.redutec.admin.bookgroup.dto;

import com.redutec.core.meta.BookGroupType;
import com.redutec.core.meta.SchoolGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public class BookGroupDto {
    @Schema(description = "도서 그룹 등록 요청 객체")
    public record CreateBookGroupRequest(
            @Schema(description = "기준연월", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            YearMonth yearMonth,

            @Schema(description = "그룹 유형", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BookGroupType type,

            @Schema(description = "대상 학년(그룹 유형이 GRADE_LINK일 때만 존재)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            SchoolGrade schoolGrade,

            @Schema(description = "그룹에 포함된 도서", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> bookIds
    ) {}

    @Schema(description = "도서 그룹 조회 요청 객체")
    public record FindBookGroupRequest(
            @Schema(description = "도서 그룹 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> bookGroupIds,

            @Schema(description = "기준연월", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            YearMonth yearMonth,

            @Schema(description = "그룹 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookGroupType.class)
            @Enumerated(EnumType.STRING)
            List<BookGroupType> types,

            @Schema(description = "학년", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = SchoolGrade.class)
            @Enumerated(EnumType.STRING)
            List<SchoolGrade> schoolGrades,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "도서 그룹 수정 요청 객체")
    public record UpdateBookGroupRequest(
            @Schema(description = "기준연월", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            YearMonth yearMonth,

            @Schema(description = "그룹 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BookGroupType type,

            @Schema(description = "대상 학년(그룹 유형이 GRADE_LINK일 때만 존재)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            SchoolGrade schoolGrade,

            @Schema(description = "그룹에 포함된 도서", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> bookIds
    ) {}

    @Schema(description = "도서 그룹 응답 객체")
    public record BookGroupResponse(
            Long bookGroupId,
            YearMonth yearMonth,
            BookGroupType type,
            String schoolGrade,
            List<Long> bookIds,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "도서 그룹 응답 페이징 객체")
    public record BookGroupPageResponse(
            List<BookGroupResponse> bookGroups,
            Long totalElements,
            Integer totalPages
    ) {}
}