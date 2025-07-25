package com.redutec.admin.institute.controller;

import com.redutec.core.dto.InstituteDto;
import com.redutec.admin.institute.service.InstituteService;
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
@RequestMapping("/institutes")
@Tag(name = "교육기관 관리 API", description = "교육기관 관리 API 모음")
public class InstituteController {
    private final ApiResponseManager apiResponseManager;
    private final InstituteService instituteService;

    @Operation(summary = "교육기관 등록", description = "교육기관 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid InstituteDto.CreateInstituteRequest createInstituteRequest
    ) {
        return apiResponseManager.create(instituteService.create(createInstituteRequest));
    }

    @Operation(summary = "조건에 맞는 교육기관 목록 조회", description = "조건에 맞는 교육기관 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid InstituteDto.FindInstituteRequest findInstituteRequest
    ) {
        return apiResponseManager.ok(instituteService.find(findInstituteRequest));
    }

    @Operation(summary = "특정 교육기관 조회", description = "특정 교육기관을 조회하는 API")
    @GetMapping("/{instituteId}")
    public ResponseEntity<ApiResponseBody> get(
            @Parameter(description = "교육기관 ID") @PathVariable Long instituteId
    ) {
        return apiResponseManager.ok(instituteService.get(instituteId));
    }

    @Operation(summary = "특정 교육기관 수정", description = "특정 교육기관을 수정하는 API")
    @PutMapping("/{instituteId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "교육기관 ID") @PathVariable Long instituteId,
            @ParameterObject @Valid InstituteDto.UpdateInstituteRequest updateInstituteRequest
    ) {
        instituteService.update(instituteId, updateInstituteRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 교육기관 삭제", description = "특정 교육기관을 삭제하는 API")
    @DeleteMapping("/{instituteId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "교육기관 ID") @PathVariable Long instituteId
    ) {
        instituteService.delete(instituteId);
        return apiResponseManager.noContent();
    }
}