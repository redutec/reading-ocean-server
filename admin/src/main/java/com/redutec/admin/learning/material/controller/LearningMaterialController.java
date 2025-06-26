package com.redutec.admin.learning.material.controller;

import com.redutec.admin.learning.material.service.LearningMaterialService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.LearningMaterialDto;
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
@RequestMapping("/learning-materials")
@Tag(name = "학습 자료 게시물 관리 API", description = "학습 자료 게시물 관리 API 모음")
public class LearningMaterialController {
    private final ApiResponseManager apiResponseManager;
    private final LearningMaterialService learningMaterialService;

    @Operation(summary = "학습 자료 게시물 등록", description = "학습 자료 게시물을 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(
            @ModelAttribute @Valid LearningMaterialDto.CreateLearningMaterialRequest createLearningMaterialRequest
    ) {
        return apiResponseManager.create(learningMaterialService.create(createLearningMaterialRequest));
    }

    @Operation(summary = "조건에 맞는 학습 자료 게시물 목록 조회", description = "조건에 맞는 학습 자료 게시물 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid LearningMaterialDto.FindLearningMaterialRequest findLearningMaterialRequest
    ) {
        return apiResponseManager.ok(learningMaterialService.find(findLearningMaterialRequest));
    }

    @Operation(summary = "특정 학습 자료 게시물 조회", description = "특정 학습 자료 게시물을 조회하는 API")
    @GetMapping("/{learningMaterialId}")
    public ResponseEntity<ApiResponseBody> findById(
            @Parameter(description = "학습 자료 게시물 ID") @PathVariable Long learningMaterialId
    ) {
        return apiResponseManager.ok(learningMaterialService.findById(learningMaterialId));
    }

    @Operation(summary = "특정 학습 자료 게시물 수정", description = "특정 학습 자료 게시물을 수정하는 API")
    @PutMapping(path = "/{learningMaterialId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "학습 자료 게시물 ID") @PathVariable Long learningMaterialId,
            @ModelAttribute @Valid LearningMaterialDto.UpdateLearningMaterialRequest updateLearningMaterialRequest
    ) {
        learningMaterialService.update(learningMaterialId, updateLearningMaterialRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 학습 자료 게시물 삭제", description = "특정 학습 자료 게시물을 삭제하는 API")
    @DeleteMapping("/{learningMaterialId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "학습 자료 게시물 ID") @PathVariable Long learningMaterialId
    ) {
        learningMaterialService.delete(learningMaterialId);
        return apiResponseManager.noContent();
    }
}