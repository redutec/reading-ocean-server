package com.redutec.admin.v1.siteinfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class SiteInfoDto {
    @Schema(description = "사이트 설정 등록 요청 객체")
    public record CreateSiteInfoRequest(
            @Schema(description = "설정 키", requiredMode = Schema.RequiredMode.REQUIRED, example = "DELIVERY_FEE")
            @NotBlank
            @Size(max = 50)
            String configurationKey,

            @Schema(description = "설정 카테고리 키", requiredMode = Schema.RequiredMode.REQUIRED, example = "DELIVERY")
            @NotBlank
            @Size(max = 50)
            String configurationCategoryKey,

            @Schema(description = "설정 카테고리 명", requiredMode = Schema.RequiredMode.REQUIRED, example = "배송정보")
            @NotBlank
            @Size(max = 100)
            String configurationCategoryName,

            @Schema(description = "설정 명", requiredMode = Schema.RequiredMode.REQUIRED, example = "배송비 적용정책")
            @NotBlank
            @Size(max = 200)
            String configurationName,

            @Schema(description = "설정 내용", requiredMode = Schema.RequiredMode.REQUIRED, example = "DFP003")
            @NotBlank
            String configurationContent,

            @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "Y")
            @NotBlank
            @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
            String useYn,

            @Schema(description = "설명", example = "조건부 무료 정책")
            @Size(max = 300)
            String description
    ) {}

    @Schema(description = "사이트 설정 조회 요청 객체")
    public record FindSiteInfoRequest(
            @Schema(description = "설정 키 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<String> configurationKeyList,

            @Schema(description = "설정 항목 카테고리 키", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String configurationCategoryKey,

            @Schema(description = "설정 항목 카테고리 명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String configurationCategoryName,

            @Schema(description = "설정 항목 명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String configurationName,

            @Schema(description = "설정 항목 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String configurationContent,

            @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
            String useYn,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 row의 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer size
    ) {}

    @Schema(description = "사이트 설정 수정 요청 객체")
    public record UpdateSiteInfoRequest(
            @Schema(description = "설정 항목 Key", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "REFUND_COST")
            @Size(max = 50)
            String configurationKey,

            @Schema(description = "설정 항목 카테고리 Key", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "REFUND")
            @Size(max = 50)
            String configurationCategoryKey,

            @Schema(description = "설정 항목 카테고리 명", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "반품/환불 정보")
            @Size(max = 30)
            String configurationCategoryName,

            @Schema(description = "설정 명", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "반품/교환 비용")
            @Size(max = 30)
            String configurationName,

            @Schema(description = "설정 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "고객의 단순변심 및 구매 착오로 인한 상품 반송비용은 고객 부담")
            String configurationContent,

            @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Y")
            @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
            String useYn,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 300)
            String description
    ) {}

    @Schema(description = "사이트 설정 응답 객체")
    public record SiteInfoResponse(
            String configurationKey,
            String configurationCategoryKey,
            String configurationCategoryName,
            String configurationName,
            String configurationContent,
            String useYn,
            LocalDateTime registerDatetime,
            LocalDateTime modifyDatetime,
            String description
    ) {}

    @Schema(description = "사이트 설정 목록 및 페이징 정보 응답 객체")
    public record SiteInfoPageResponse(
            List<SiteInfoResponse> siteInfoList,
            Long totalElements,
            Integer totalPages
    ) {}
}
