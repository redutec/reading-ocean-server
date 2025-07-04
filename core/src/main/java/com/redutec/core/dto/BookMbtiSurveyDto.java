package com.redutec.core.dto;

import com.redutec.core.meta.BookMbtiResult;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class BookMbtiSurveyDto {
    @Schema(description = "북BTI 설문 응답 등록 요청 객체")
    public record CreateBookMbtiSurveyRequest(
            @Schema(description = "설문에 응답한 학생 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long studentId,

            @Schema(description = "문항별 응답 목록", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotEmpty
            @Size(max = 255)
            List<CreateBookMbtiSurveyAnswerRequest> answers
    ) {}

    @Schema(description = "북BTI 설문 응답 상세내역 등록 요청 객체")
    public record CreateBookMbtiSurveyAnswerRequest(
            @Schema(description = "응답한 문항 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long bookMbtiQuestionId,

            @Schema(description = "선택한 선택지 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long answerChoiceId
    ) {}

    @Schema(description = "북BTI 설문 답변 조회 요청 객체")
    public record FindBookMbtiSurveyRequest(
            @Schema(description = "북BTI 설문 답변 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> bookMbtiSurveyIds,

            @Schema(description = "응답자 학생", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 255)
            List<@Positive Long> studentIds,

            @Schema(description = "북BTI 결과", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            @ElementCollection(targetClass = BookMbtiResult.class)
            List<BookMbtiResult> bookMbtiResults,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "북BTI 설문 답변 응답 객체")
    public record BookMbtiSurveyResponse(
            Long bookMbtiSurveyId,
            Long studentId,
            String studentAccountId,
            BookMbtiResult result,
            List<BookMbtiSurveyAnswerResponse> answers,
            LocalDateTime createdAt
    ) {}

    @Schema(description = "북BTI 설문 답변 상세내역 응답 객체 ")
    public record BookMbtiSurveyAnswerResponse(
            @Schema(description = "응답한 문항 고유번호")
            Long bookMbtiQuestionId,

            @Schema(description = "문항 내용")
            String question,

            @Schema(description = "선택한 선택지 고유번호")
            Long answerChoiceId,

            @Schema(description = "선택지 레이블(예: 1,2,3,4)")
            Integer label,

            @Schema(description = "선택지 내용")
            String content,

            @Schema(description = "선택지 점수")
            Integer score
    ) {}

    @Schema(description = "북BTI 설문 답변 응답 페이징 객체")
    public record BookMbtiSurveyPageResponse(
            List<BookMbtiSurveyResponse> bookMbtiSurveys,
            Long totalElements,
            Integer totalPages
    ) {}
}