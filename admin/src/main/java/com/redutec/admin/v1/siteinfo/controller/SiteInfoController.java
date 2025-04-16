package com.redutec.admin.v1.siteinfo.controller;

import com.redutec.admin.v1.siteinfo.dto.SiteInfoDto;
import com.redutec.admin.v1.siteinfo.service.SiteInfoService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사이트 설정 API 컨트롤러.
 * 이 클래스는 사이트 설정 관련 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/site-info")
@Tag(name = "사이트 설정 관리", description = "사이트 설정 관련 API 모음")
public class SiteInfoController {
    private final ApiResponseManager apiResponseManager;
    private final SiteInfoService siteInfoService;

    @Operation(summary = "사이트 설정 등록", description = "새로운 사이트 설정 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject SiteInfoDto.CreateSiteInfoRequest createSiteInfoRequest
    ) {
        return apiResponseManager.success(siteInfoService.create(createSiteInfoRequest));
    }

    @Operation(summary = "사이트 설정 목록 조회", description = "조건에 맞는 사이트 설정 정보를 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject SiteInfoDto.FindSiteInfoRequest findSiteInfoRequest
    ) {
        return apiResponseManager.success(siteInfoService.find(findSiteInfoRequest));
    }

    @Operation(summary = "사이트 설정 상세 조회", description = "특정 사이트 설정 정보를 상세 조회하는 API")
    @GetMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseBody> findByConfigurationKey(
            @Parameter(description = "조회할 사이트 설정 ID") @PathVariable String configurationKey
    ) {
        return apiResponseManager.success(siteInfoService.findByConfigurationKey(configurationKey));
    }

    @Operation(summary = "사이트 설정 수정", description = "특정 사이트 설정 정보를 수정하는 API")
    @PutMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "수정할 사이트 설정 ID") @PathVariable String configurationKey,
            @ParameterObject SiteInfoDto.UpdateSiteInfoRequest updateSiteInfoRequest
    ) {
        siteInfoService.update(configurationKey, updateSiteInfoRequest);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "사이트 설정 삭제", description = "특정 사이트 설정 정보를 삭제하는 API")
    @DeleteMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "삭제할 사이트 설정 ID") @PathVariable String configurationKey
    ) {
        siteInfoService.delete(configurationKey);
        return apiResponseManager.success(null);
    }
}