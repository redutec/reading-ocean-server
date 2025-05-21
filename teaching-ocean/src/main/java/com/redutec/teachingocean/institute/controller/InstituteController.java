package com.redutec.teachingocean.institute.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.InstituteDto;
import com.redutec.teachingocean.institute.service.InstituteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institute")
@Tag(name = "교육기관 API", description = "교육기관 API 모음")
public class InstituteController {
    private final ApiResponseManager apiResponseManager;
    private final InstituteService instituteService;

    @Operation(summary = "마이페이지 - 교육기관 조회", description = "현재 로그인한 교사가 속한 교육기관 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> findOne() {
        return apiResponseManager.ok(instituteService.findOne());
    }

    @Operation(summary = "마이페이지 - 교육기관 수정", description = "현재 로그인한 교사가 속한 교육기관 수정")
    @PatchMapping
    public ResponseEntity<ApiResponseBody> update(
            @ParameterObject @Valid InstituteDto.UpdateInstituteRequest updateInstituteRequest
    ) {
        instituteService.update(updateInstituteRequest);
        return apiResponseManager.noContent();
    }
}