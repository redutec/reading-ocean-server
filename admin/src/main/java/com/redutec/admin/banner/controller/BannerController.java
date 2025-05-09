package com.redutec.admin.banner.controller;

import com.redutec.admin.banner.dto.BannerDto;
import com.redutec.admin.banner.service.BannerService;
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
@RequestMapping("/banner")
@Tag(name = "배너 관리 API", description = "배너 관리 API 모음")
public class BannerController {
    private final ApiResponseManager apiResponseManager;
    private final BannerService bannerService;

    @Operation(summary = "배너 등록", description = "배너 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@ParameterObject @Valid BannerDto.CreateBannerRequest createBannerRequest) {
        return apiResponseManager.success(bannerService.create(createBannerRequest));
    }

    @Operation(summary = "조건에 맞는 배너 목록 조회", description = "조건에 맞는 배너 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid BannerDto.FindBannerRequest findBannerRequest) {
        return apiResponseManager.success(bannerService.find(findBannerRequest));
    }

    @Operation(summary = "특정 배너 조회", description = "특정 배너를 조회하는 API")
    @GetMapping("/{bannerId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long bannerId) {
        return apiResponseManager.success(bannerService.findById(bannerId));
    }

    @Operation(summary = "특정 배너 수정", description = "특정 배너를 수정하는 API")
    @PatchMapping("/{bannerId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "배너 ID") @PathVariable Long bannerId,
            @ParameterObject @Valid BannerDto.UpdateBannerRequest updateBannerRequest
    ) {
        bannerService.update(bannerId, updateBannerRequest);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "특정 배너 삭제", description = "특정 배너를 삭제하는 API")
    @DeleteMapping("/{bannerId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "배너 ID") @PathVariable Long bannerId) {
        bannerService.delete(bannerId);
        return apiResponseManager.success(null);
    }
}