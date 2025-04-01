package com.redutec.admin.article.controller;

import com.redutec.admin.article.service.BannerService;
import com.redutec.admin.siteinfo.dto.SiteInfoDto;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 배너 API 컨트롤러.
 * 이 클래스는 배너 관련 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/article/banner")
@Tag(name = "배너 관리", description = "배너 관련 API 모음")
public class BannerController {
    private final ApiResponseManager apiResponseManager;
    private final BannerService bannerService;

    @Operation(summary = "배너 등록", description = "새로운 배너 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@Valid @RequestBody SiteInfoDto.CreateSiteInfoRequest createSiteInfoRequest) {
        return apiResponseManager.success(siteInfoService.create(createSiteInfoRequest));
    }

    @Operation(summary = "배너 목록 조회", description = "조건에 맞는 배너 정보를 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @Parameter(description = "설정 키 목록") @RequestParam(required = false) List<String> configurationKeyList,
            @Parameter(description = "설정 카테고리 키") @RequestParam(required = false) String configurationCategoryKey,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(required = false) Integer page,
            @Parameter(description = "페이지 당 데이터 개수", example = "30") @RequestParam(required = false) Integer size
    ) {
        SiteInfoDto.FindSiteInfoRequest findSiteInfoRequest = SiteInfoDto.FindSiteInfoRequest.builder()
                .configurationKeyList(configurationKeyList)
                .configurationCategoryKey(configurationCategoryKey)
                .page(page)
                .size(size)
                .build();
        return apiResponseManager.success(siteInfoService.find(findSiteInfoRequest));
    }

    @Operation(summary = "배너 상세 조회", description = "특정 배너 정보를 상세 조회하는 API")
    @GetMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseBody> findByConfigurationKey(@PathVariable String configurationKey) {
        return apiResponseManager.success(siteInfoService.findByConfigurationKey(configurationKey));
    }

    @Operation(summary = "배너 수정", description = "특정 배너 정보를 수정하는 API")
    @PutMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseBody> update(
            @PathVariable String configurationKey,
            @Valid @RequestBody SiteInfoDto.UpdateSiteInfoRequest updateSiteInfoRequest) {
        siteInfoService.update(configurationKey, updateSiteInfoRequest);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "배너 삭제", description = "특정 배너 정보를 삭제하는 API")
    @DeleteMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseBody> delete(@PathVariable String configurationKey) {
        siteInfoService.delete(configurationKey);
        return apiResponseManager.success(null);
    }
}