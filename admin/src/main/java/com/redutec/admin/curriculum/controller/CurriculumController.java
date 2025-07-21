package com.redutec.admin.curriculum.controller;

import com.redutec.admin.curriculum.service.CurriculumService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.CurriculumDto;
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
@RequestMapping("/curriculums")
@Tag(name = "커리큘럼 관리 API", description = "커리큘럼 관리 API 모음")
public class CurriculumController {
    private final ApiResponseManager apiResponseManager;
    private final CurriculumService curriculumService;

    @Operation(summary = "조건에 맞는 커리큘럼 목록 조회", description = "조건에 맞는 커리큘럼 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid CurriculumDto.FindCurriculumRequest findCurriculumRequest
    ) {
        return apiResponseManager.ok(curriculumService.find(findCurriculumRequest));
    }

    @Operation(summary = "특정 커리큘럼 조회", description = "특정 커리큘럼을 조회하는 API")
    @GetMapping("/{curriculumId}")
    public ResponseEntity<ApiResponseBody> get(@PathVariable Long curriculumId) {
        return apiResponseManager.ok(curriculumService.get(curriculumId));
    }
}