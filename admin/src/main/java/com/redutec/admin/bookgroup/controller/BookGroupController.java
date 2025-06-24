package com.redutec.admin.bookgroup.controller;

import com.redutec.core.dto.BookGroupDto;
import com.redutec.admin.bookgroup.service.BookGroupService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
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
@RequestMapping("/book/group")
@Tag(name = "도서 그룹 관리 API", description = "도서 그룹 관리 API 모음")
public class BookGroupController {
    private final ApiResponseManager apiResponseManager;
    private final BookGroupService bookGroupService;

    @Operation(summary = "도서 그룹 등록", description = "도서 그룹 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid BookGroupDto.CreateBookGroupRequest createBookGroupRequest
    ) {
        return apiResponseManager.create(bookGroupService.create(createBookGroupRequest));
    }

    @Operation(summary = "조건에 맞는 도서 그룹 목록 조회", description = "조건에 맞는 도서 그룹 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid BookGroupDto.FindBookGroupRequest findBookGroupRequest
    ) {
        return apiResponseManager.ok(bookGroupService.find(findBookGroupRequest));
    }

    @Operation(summary = "특정 도서 그룹 조회", description = "특정 도서 그룹를 조회하는 API")
    @GetMapping("/{bookGroupId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long bookGroupId) {
        return apiResponseManager.ok(bookGroupService.findById(bookGroupId));
    }

    @Operation(summary = "특정 도서 그룹 수정", description = "특정 도서 그룹를 수정하는 API")
    @PutMapping("/{bookGroupId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "도서 그룹 ID") @PathVariable Long bookGroupId,
            @ParameterObject @Valid BookGroupDto.UpdateBookGroupRequest updateBookGroupRequest
    ) {
        bookGroupService.update(bookGroupId, updateBookGroupRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 도서 그룹 삭제", description = "특정 도서 그룹를 삭제하는 API")
    @DeleteMapping("/{bookGroupId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "도서 그룹 ID") @PathVariable Long bookGroupId
    ) {
        bookGroupService.delete(bookGroupId);
        return apiResponseManager.noContent();
    }
}