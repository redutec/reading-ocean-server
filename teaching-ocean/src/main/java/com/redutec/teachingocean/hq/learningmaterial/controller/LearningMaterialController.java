package com.redutec.teachingocean.hq.learningmaterial.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.LearningMaterialDto;
import com.redutec.teachingocean.hq.learningmaterial.service.LearningMaterialService;
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
@RequestMapping("/learning-materials")
@Tag(name = "학습 자료 API", description = "학습 자료 API 모음")
public class LearningMaterialController {
    private final ApiResponseManager apiResponseManager;
    private final LearningMaterialService learningMaterialService;

    @Operation(summary = "조건에 맞는 학습 자료 게시물 목록 조회", description = "조건에 맞는 학습 자료 게시물 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid LearningMaterialDto.FindLearningMaterialRequest findLearningMaterialRequest
    ) {
        return apiResponseManager.ok(learningMaterialService.find(findLearningMaterialRequest));
    }

    @Operation(summary = "특정 학습 자료 게시물 조회", description = "특정 학습 자료 게시물을 조회하는 API")
    @GetMapping("/{learningMaterialId}")
    public ResponseEntity<ApiResponseBody> get(
            @Parameter(description = "학습 자료 게시물 ID") @PathVariable Long learningMaterialId
    ) {
        return apiResponseManager.ok(learningMaterialService.get(learningMaterialId));
    }
}