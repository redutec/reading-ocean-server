package com.redutec.admin.bookmbti.survey.controller;

import com.redutec.admin.bookmbti.survey.service.BookMbtiSurveyService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.BookMbtiSurveyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-mbti/surveys")
@Tag(name = "북BTI 설문 응답 관리 API", description = "북BTI 설문 응답 관리 API 모음")
public class BookMbtiSurveyController {
    private final ApiResponseManager apiResponseManager;
    private final BookMbtiSurveyService bookMbtiSurveyService;

    @Operation(summary = "조건에 맞는 북BTI 설문 응답 목록 조회", description = "조건에 맞는 북BTI 설문 응답 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid BookMbtiSurveyDto.FindBookMbtiSurveyRequest findBookMbtiSurveyRequest
    ) {
        return apiResponseManager.ok(bookMbtiSurveyService.find(findBookMbtiSurveyRequest));
    }

    @Operation(summary = "특정 북BTI 설문 응답 조회", description = "특정 북BTI 설문 응답을 조회하는 API")
    @GetMapping("/{bookMbtiSurveyId}")
    public ResponseEntity<ApiResponseBody> findById(
            @Parameter(description = "북BTI 설문 응답 ID") @PathVariable Long bookMbtiSurveyId
    ) {
        return apiResponseManager.ok(bookMbtiSurveyService.findById(bookMbtiSurveyId));
    }

    @Operation(summary = "특정 북BTI 설문 응답 삭제", description = "특정 북BTI 설문 응답을 삭제하는 API")
    @DeleteMapping("/{bookMbtiSurveyId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "북BTI 설문 응답 ID") @PathVariable Long bookMbtiSurveyId
    ) {
        bookMbtiSurveyService.delete(bookMbtiSurveyId);
        return apiResponseManager.noContent();
    }
}