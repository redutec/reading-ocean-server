package com.redutec.core.dto;

import com.redutec.core.meta.BookMbtiQuestionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class BookMbtiQuestionDto {
    @Schema(description = "북BTI 설문 문항 등록 요청 객체")
    public record CreateBookMbtiQuestionRequest(
            @Schema(description = "설문 문항 유형", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BookMbtiQuestionType type,

            @Schema(description = "설문 문항 내용", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank
            @Size(max = 255)
            String question,

            @Schema(description = "문항별 선택지 목록(Response: CreateChoiceRequest)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotEmpty
            List<CreateBookMbtiQuestionChoiceRequest> choices
    ) {}

    @Schema(description = "북BTI 문항 선택지 생성 요청 객체")
    public record CreateBookMbtiQuestionChoiceRequest(
            @Schema(description = "선택지 레이블(예: 1,2,3,4)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Integer label,

            @Schema(description = "선택지 내용", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank
            @Size(max = 255)
            String content,

            @Schema(description = "선택지 점수", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @PositiveOrZero
            Integer score
    ) {}

    @Schema(description = "북BTI 설문 문항 조회 요청 객체")
    public record FindBookMbtiQuestionRequest(
            @Schema(description = "북BTI 설문 문항 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> bookMbtiQuestionIds,

            @Schema(description = "설문 문항 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookMbtiQuestionType.class)
            @Enumerated(EnumType.STRING)
            List<BookMbtiQuestionType> types,

            @Schema(description = "설문 문항 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 255)
            String question,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}


    @Schema(description = "북BTI 설문 문항 수정 요청 객체")
    public record UpdateBookMbtiQuestionRequest(
            @Schema(description = "문항 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BookMbtiQuestionType type,

            @Schema(description = "문항 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 255)
            String question,

            @Schema(description = "문항별 선택지 수정 요청 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<UpdateBookMbtiQuestionChoiceRequest> choices
    ) {}

    @Schema(description = "북BTI 문항 선택지 수정 요청 객체")
    public record UpdateBookMbtiQuestionChoiceRequest(
            @Schema(description = "선택지 고유번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Long choiceId,

            @Schema(description = "선택지 레이블(예: 1,2,3,4)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            Integer label,

            @Schema(description = "선택지 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotBlank
            @Size(max = 255)
            String content,

            @Schema(description = "선택지 점수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @NotNull
            Integer score
    ) {}

    @Schema(description = "북BTI 설문 문항 응답 객체")
    public record BookMbtiQuestionResponse(
            @Schema(description = "문항 고유번호")
            Long bookMbtiQuestionId,

            @Schema(description = "문항 유형")
            BookMbtiQuestionType type,

            @Schema(description = "문항 내용")
            String question,

            @Schema(description = "문항별 선택지 목록")
            List<BookMbtiQuestionChoiceResponse> choices,

            @Schema(description = "생성 일시")
            LocalDateTime createdAt,

            @Schema(description = "수정 일시")
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "북BTI 설문 문항 선택지 응답 객체")
    public record BookMbtiQuestionChoiceResponse(
            @Schema(description = "선택지 고유번호")
            Long choiceId,

            @Schema(description = "선택지 레이블(예: 1,2,3,4)")
            Integer label,

            @Schema(description = "선택지 내용")
            String content,

            @Schema(description = "선택지 점수")
            Integer score
    ) {}

    @Schema(description = "북BTI 설문 문항 응답 페이징 객체")
    public record BookMbtiQuestionPageResponse(
            List<BookMbtiQuestionResponse> bookMbtiQuestions,
            Long totalElements,
            Integer totalPages
    ) {}
}