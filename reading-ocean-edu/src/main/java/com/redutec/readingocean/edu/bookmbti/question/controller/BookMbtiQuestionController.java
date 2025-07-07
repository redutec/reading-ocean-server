package com.redutec.readingocean.edu.bookmbti.question.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.readingocean.edu.bookmbti.question.service.BookMbtiQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book-mbti/questions")
@Tag(name = "북BTI 설문 문항 API", description = "북BTI 설문 문항 API 모음")
public class BookMbtiQuestionController {
    private final ApiResponseManager apiResponseManager;
    private final BookMbtiQuestionService bookMbtiQuestionService;

    @Operation(summary = "모든 북BTI 설문 문항 목록 조회", description = "모든 북BTI 설문 문항 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> findAll() {
        return apiResponseManager.ok(bookMbtiQuestionService.findAll());
    }
}