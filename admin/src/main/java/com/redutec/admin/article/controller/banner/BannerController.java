package com.redutec.admin.article.controller.banner;

import com.redutec.admin.article.dto.ArticleDto;
import com.redutec.admin.article.service.banner.BannerService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.meta.Domain;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(
            @Valid @RequestPart ArticleDto.CreateBannerRequest createBannerRequest,
            @RequestPart(value = "pcBannerImageFile") MultipartFile pcBannerImageFile,
            @RequestPart(value = "mobileBannerImageFile") MultipartFile mobileBannerImageFile
    ) {
        bannerService.create(createBannerRequest, pcBannerImageFile, mobileBannerImageFile);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "배너 목록 조회", description = "조건에 맞는 배너 정보를 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @Parameter(description = "배너 고유번호") @RequestParam(required = false) List<Integer> bannerNoList,
            @Parameter(description = "배너 제목") @RequestParam(required = false) String bannerTitle,
            @Parameter(description = "배너 내용") @RequestParam(required = false) String bannerContent,
            @Parameter(description = "배너 상세 내용") @RequestParam(required = false) String bannerContentDetail,
            @Parameter(description = "노출 여부") @RequestParam(required = false) String displayYn,
            @Parameter(description = "노출 구분") @RequestParam(required = false) List<Domain> domainList,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(required = false) Integer page,
            @Parameter(description = "페이지 당 데이터 개수", example = "30") @RequestParam(required = false) Integer size
    ) {
        ArticleDto.FindArticleRequest findBannerRequest = ArticleDto.FindArticleRequest.builder()
                .articleNoList(bannerNoList)
                .articleTitle(bannerTitle)
                .articleContent(bannerContent)
                .articleContentDetail(bannerContentDetail)
                .displayYn(displayYn)
                .domainList(domainList)
                .page(page)
                .size(size)
                .build();
        return apiResponseManager.success(bannerService.find(findBannerRequest));
    }

    @Operation(summary = "배너 상세 조회", description = "특정 배너 정보를 상세 조회하는 API")
    @GetMapping("/{bannerNo}")
    public ResponseEntity<ApiResponseBody> findByBannerNo(@PathVariable Integer bannerNo) {
        return apiResponseManager.success(bannerService.findByBannerNo(bannerNo));
    }

    @Operation(summary = "배너 수정", description = "특정 배너 정보를 수정하는 API")
    @PutMapping(value = "/{bannerNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @PathVariable Integer bannerNo,
            @Valid @RequestPart("updateBannerRequest") ArticleDto.UpdateBannerRequest updateBannerRequest,
            @RequestPart(value = "pcBannerImageFile") MultipartFile pcBannerImageFile,
            @RequestPart(value = "mobileBannerImageFile") MultipartFile mobileBannerImageFile
    ) {
        bannerService.update(
                bannerNo,
                updateBannerRequest,
                pcBannerImageFile,
                mobileBannerImageFile
        );
        return apiResponseManager.success(null);
    }

    @Operation(summary = "배너 삭제", description = "특정 배너 정보를 삭제하는 API")
    @DeleteMapping("/{bannerNo}")
    public ResponseEntity<ApiResponseBody> delete(@PathVariable Integer bannerNo) {
        bannerService.delete(bannerNo);
        return apiResponseManager.success(null);
    }
}