package com.redutec.teachingocean.curriculum.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.CurriculumDto;
import com.redutec.teachingocean.curriculum.service.CurriculumService;
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
@RequestMapping("/curriculums")
@Tag(name = "커리큘럼 관리 API", description = "커리큘럼 관리 API 모음")
public class CurriculumController {
    private final ApiResponseManager apiResponseManager;
    private final CurriculumService curriculumService;

    @Operation(summary = "커리큘럼 등록", description = "커리큘럼을 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(
            @ModelAttribute @Valid CurriculumDto.CreateCurriculumRequest createCurriculumRequest
    ) {
        return apiResponseManager.create(curriculumService.create(createCurriculumRequest));
    }
    
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

    @Operation(summary = "특정 커리큘럼 수정", description = "특정 커리큘럼을 수정하는 API")
    @PutMapping(path = "/{curriculumId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "커리큘럼 ID") @PathVariable Long curriculumId,
            @ModelAttribute @Valid CurriculumDto.UpdateCurriculumRequest updateCurriculumRequest
    ) {
        curriculumService.update(curriculumId, updateCurriculumRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 커리큘럼 삭제", description = "특정 커리큘럼을 삭제하는 API")
    @DeleteMapping("/{curriculumId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "커리큘럼 ID") @PathVariable Long curriculumId
    ) {
        curriculumService.delete(curriculumId);
        return apiResponseManager.noContent();
    }
}