package com.redutec.admin.bookmbti.question.controller;

import com.redutec.admin.bookmbti.question.service.BookMbtiQuestionService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.BookMbtiQuestionDto;
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
@RequestMapping("/book-mbti/questions")
@Tag(name = "북BTI 설문 문항 관리 API", description = "북BTI 설문 문항 관리 API 모음")
public class BookMbtiQuestionController {
    private final ApiResponseManager apiResponseManager;
    private final BookMbtiQuestionService bookMbtiQuestionService;

    @Operation(summary = "북BTI 설문 문항 등록", description = "북BTI 설문 문항 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "등록할 문항 정보 (type, question, choices 포함)",
                    required = true
            )
            @RequestBody @Valid BookMbtiQuestionDto.CreateBookMbtiQuestionRequest createBookMbtiQuestionRequest
    ) {
        return apiResponseManager.create(bookMbtiQuestionService.create(createBookMbtiQuestionRequest));
    }

    @Operation(summary = "조건에 맞는 북BTI 설문 문항 목록 조회", description = "조건에 맞는 북BTI 설문 문항 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid BookMbtiQuestionDto.FindBookMbtiQuestionRequest findBookMbtiQuestionRequest
    ) {
        return apiResponseManager.ok(bookMbtiQuestionService.find(findBookMbtiQuestionRequest));
    }

    @Operation(summary = "특정 북BTI 설문 문항 조회", description = "특정 북BTI 설문 문항을 조회하는 API")
    @GetMapping("/{bookMbtiQuestionId}")
    public ResponseEntity<ApiResponseBody> findById(
            @Parameter(description = "북BTI 설문 문항 ID") @PathVariable Long bookMbtiQuestionId
    ) {
        return apiResponseManager.ok(bookMbtiQuestionService.findById(bookMbtiQuestionId));
    }

    @Operation(summary = "특정 북BTI 설문 문항 수정", description = "특정 북BTI 설문 문항을 수정하는 API")
    @PutMapping("/{bookMbtiQuestionId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "북BTI 설문 문항 ID") @PathVariable Long bookMbtiQuestionId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "등록할 문항 정보 (type, question, choices 포함)",
                    required = true
            )
            @RequestBody @Valid BookMbtiQuestionDto.UpdateBookMbtiQuestionRequest updateBookMbtiQuestionRequest
    ) {
        bookMbtiQuestionService.update(bookMbtiQuestionId, updateBookMbtiQuestionRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 북BTI 설문 문항 삭제", description = "특정 북BTI 설문 문항을 삭제하는 API")
    @DeleteMapping("/{bookMbtiQuestionId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "북BTI 설문 문항 ID") @PathVariable Long bookMbtiQuestionId
    ) {
        bookMbtiQuestionService.delete(bookMbtiQuestionId);
        return apiResponseManager.noContent();
    }
}