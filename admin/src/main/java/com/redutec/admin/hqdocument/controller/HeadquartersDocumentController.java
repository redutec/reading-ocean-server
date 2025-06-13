package com.redutec.admin.hqdocument.controller;

import com.redutec.admin.hqdocument.service.HeadquartersDocumentService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.HeadquartersDocumentDto;
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
@RequestMapping("/hq-document")
@Tag(name = "본사 자료실 게시물 관리 API", description = "본사 자료실 게시물 관리 API 모음")
public class HeadquartersDocumentController {
    private final ApiResponseManager apiResponseManager;
    private final HeadquartersDocumentService headquartersDocumentService;

    @Operation(summary = "본사 자료실 게시물 등록", description = "본사 자료실 게시물을 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(
            @ModelAttribute @Valid HeadquartersDocumentDto.CreateHeadquartersDocumentRequest createHeadquartersDocumentRequest
    ) {
        return apiResponseManager.create(headquartersDocumentService.create(createHeadquartersDocumentRequest));
    }

    @Operation(summary = "조건에 맞는 본사 자료실 게시물 목록 조회", description = "조건에 맞는 본사 자료실 게시물 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid HeadquartersDocumentDto.FindHeadquartersDocumentRequest findHeadquartersDocumentRequest
    ) {
        return apiResponseManager.ok(headquartersDocumentService.find(findHeadquartersDocumentRequest));
    }

    @Operation(summary = "특정 본사 자료실 게시물 조회", description = "특정 본사 자료실 게시물을 조회하는 API")
    @GetMapping("/{headquartersDocumentId}")
    public ResponseEntity<ApiResponseBody> findById(
            @Parameter(description = "본사 자료실 게시물 ID") @PathVariable Long headquartersDocumentId
    ) {
        return apiResponseManager.ok(headquartersDocumentService.findById(headquartersDocumentId));
    }

    @Operation(summary = "특정 본사 자료실 게시물 수정", description = "특정 본사 자료실 게시물을 수정하는 API")
    @PatchMapping(path = "/{headquartersDocumentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "본사 자료실 게시물 ID") @PathVariable Long headquartersDocumentId,
            @ModelAttribute @Valid HeadquartersDocumentDto.UpdateHeadquartersDocumentRequest updateHeadquartersDocumentRequest
    ) {
        headquartersDocumentService.update(headquartersDocumentId, updateHeadquartersDocumentRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 본사 자료실 게시물 삭제", description = "특정 본사 자료실 게시물을 삭제하는 API")
    @DeleteMapping("/{headquartersDocumentId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "본사 자료실 게시물 ID") @PathVariable Long headquartersDocumentId
    ) {
        headquartersDocumentService.delete(headquartersDocumentId);
        return apiResponseManager.noContent();
    }
}