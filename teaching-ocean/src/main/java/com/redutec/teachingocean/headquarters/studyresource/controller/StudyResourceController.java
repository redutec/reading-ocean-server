package com.redutec.teachingocean.headquarters.studyresource.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.HeadquartersDocumentDto;
import com.redutec.teachingocean.headquarters.studyresource.service.HeadquartersDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/")
@Tag(name = "학습 자료 API", description = "학습 자료 API 모음")
public class StudyResourceController {
    private final ApiResponseManager apiResponseManager;
    private final HeadquartersDocumentService headquartersDocumentService;

    @Operation(summary = "조건에 맞는 학습 자료 게시물 목록 조회", description = "조건에 맞는 학습 자료 게시물 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid HeadquartersDocumentDto.FindHeadquartersDocumentRequest findHeadquartersDocumentRequest
    ) {
        return apiResponseManager.ok(headquartersDocumentService.find(findHeadquartersDocumentRequest));
    }

    @Operation(summary = "특정 학습 자료 게시물 조회", description = "특정 학습 자료 게시물을 조회하는 API")
    @GetMapping("/{headquartersDocumentId}")
    public ResponseEntity<ApiResponseBody> findById(
            @Parameter(description = "학습 자료 게시물 ID") @PathVariable Long headquartersDocumentId
    ) {
        return apiResponseManager.ok(headquartersDocumentService.findById(headquartersDocumentId));
    }
}