package com.redutec.admin.book.controller;

import com.redutec.core.dto.BookDto;
import com.redutec.admin.book.service.BookService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "도서 관리 API", description = "도서 관리 API 모음")
public class BookController {
    private final ApiResponseManager apiResponseManager;
    private final BookService bookService;

    @Operation(summary = "도서 등록", description = "도서 정보를 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(@ModelAttribute @Valid BookDto.CreateBookRequest createBookRequest) {
        return apiResponseManager.create(bookService.create(createBookRequest));
    }

    @Operation(summary = "조건에 맞는 도서 목록 조회", description = "조건에 맞는 도서 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid BookDto.FindBookRequest findBookRequest) {
        return apiResponseManager.ok(bookService.find(findBookRequest));
    }

    @Operation(summary = "특정 도서 조회", description = "특정 도서를 조회하는 API")
    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponseBody> get(@PathVariable Long bookId) {
        return apiResponseManager.ok(bookService.get(bookId));
    }

    @Operation(summary = "특정 도서 수정", description = "특정 도서를 수정하는 API")
    @PutMapping(path = "/{bookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "도서 ID") @PathVariable Long bookId,
            @ModelAttribute @Valid BookDto.UpdateBookRequest updateBookRequest
    ) {
        bookService.update(bookId, updateBookRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 도서 삭제", description = "특정 도서를 삭제하는 API")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "도서 ID") @PathVariable Long bookId) {
        bookService.delete(bookId);
        return apiResponseManager.noContent();
    }
}