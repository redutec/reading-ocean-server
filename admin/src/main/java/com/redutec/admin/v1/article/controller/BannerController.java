package com.redutec.admin.v1.article.controller;

import com.redutec.admin.v1.article.dto.BannerDto;
import com.redutec.admin.v1.article.service.ArticleService;
import com.redutec.admin.v1.article.service.BannerService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 배너 API 컨트롤러.
 * 이 클래스는 배너 관련 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/article/banner")
@Tag(name = "배너 관리", description = "배너 관련 API 모음")
public class BannerController {
    private final ApiResponseManager apiResponseManager;
    private final ArticleService articleService;
    private final BannerService bannerService;

    @Operation(summary = "배너 등록", description = "새로운 배너 정보를 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject BannerDto.CreateBannerRequest createBannerRequest,
            @RequestPart(value = "pcBannerImageFile") MultipartFile pcBannerImageFile,
            @RequestPart(value = "mobileBannerImageFile") MultipartFile mobileBannerImageFile
    ) {
        bannerService.create(createBannerRequest, pcBannerImageFile, mobileBannerImageFile);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "배너 목록 조회", description = "조건에 맞는 배너 정보를 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
        @ParameterObject BannerDto.FindBannerRequest findBannerRequest
    ) {
        return apiResponseManager.success(bannerService.find(findBannerRequest));
    }

    @Operation(summary = "배너 상세 조회", description = "특정 배너 정보를 상세 조회하는 API")
    @GetMapping("/{bannerNo}")
    public ResponseEntity<ApiResponseBody> findByBannerNo(
            @Parameter(description = "조회할 배너 고유번호") @PathVariable Integer bannerNo
    ) {
        return apiResponseManager.success(bannerService.findByBannerNo(bannerNo));
    }

    @Operation(summary = "배너 수정", description = "특정 배너 정보를 수정하는 API")
    @PutMapping(value = "/{bannerNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "수정할 배너 고유번호") @PathVariable Integer bannerNo,
            @ParameterObject BannerDto.UpdateBannerRequest updateBannerRequest,
            @RequestPart(value = "pcBannerImageFile") MultipartFile pcBannerImageFile,
            @RequestPart(value = "mobileBannerImageFile") MultipartFile mobileBannerImageFile
    ) {
        bannerService.update(bannerNo, updateBannerRequest, pcBannerImageFile, mobileBannerImageFile);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "배너 삭제", description = "특정 배너 정보를 삭제하는 API")
    @DeleteMapping("/{bannerNo}")
    public ResponseEntity<ApiResponseBody> delete(
            @PathVariable Integer bannerNo
    ) {
        articleService.delete(bannerNo);
        return apiResponseManager.success(null);
    }
}