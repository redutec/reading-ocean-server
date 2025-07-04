package com.redutec.readingoceanedu.bookmbti.survey.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.readingoceanedu.bookmbti.survey.service.BookMbtiSurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-mbti/surveys")
@Tag(name = "북BTI 설문 응답 API", description = "북BTI 설문 응답 관리 API 모음")
public class BookMbtiSurveyController {
    private final ApiResponseManager apiResponseManager;
    private final BookMbtiSurveyService bookMbtiSurveyService;

    @Operation(summary = "현재 로그인한 학생의 설문 응답 조회", description = "현재 로그인한 학생의 북BTI 설문 응답을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find() {
        return apiResponseManager.ok(bookMbtiSurveyService.find());
    }
}