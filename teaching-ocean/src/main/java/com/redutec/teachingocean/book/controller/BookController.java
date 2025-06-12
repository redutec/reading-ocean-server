package com.redutec.teachingocean.book.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.BookDto;
import com.redutec.teachingocean.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "도서 API", description = "도서 API 모음")
public class BookController {
    private final ApiResponseManager apiResponseManager;
    private final BookService bookService;

    @Operation(summary = "조건에 맞는 도서 목록 조회", description = "조건에 맞는 도서 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid BookDto.FindBookRequest findBookRequest) {
        return apiResponseManager.ok(bookService.find(findBookRequest));
    }

    @Operation(summary = "특정 도서 조회", description = "특정 도서를 조회하는 API")
    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long bookId) {
        return apiResponseManager.ok(bookService.findById(bookId));
    }
}