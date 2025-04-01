package com.redutec.admin.article.dto;

import com.redutec.core.criteria.CmtConfigurationGeneralCriteria;
import com.redutec.core.entity.CmtConfigurationGeneral;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class BannerDto {
    @Schema(description = "배너 등록 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class CreateBannerRequest {
        @Schema(description = "배너 제목", example = "테스트 배너 제목")
        @NotBlank
        @Size(max = 100)
        private String articleTitle;

    }

    @Schema(description = "배너 조회 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class FindBannerRequest {
        @Schema(description = "설정 키 목록")
        private List<String> configurationKeyList;

        @Schema(description = "설정 항목 카테고리 키")
        private String configurationCategoryKey;

        @Schema(description = "설정 항목 카테고리 명")
        private String configurationCategoryName;

        @Schema(description = "설정 항목 명")
        private String configurationName;

        @Schema(description = "설정 항목 내용")
        private String configurationContent;

        @Schema(description = "사용 여부")
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String useYn;

        @Schema(description = "설명")
        private String description;

        @Schema(description = "페이지 번호 (0 이상의 정수)")
        @PositiveOrZero
        private Integer page;

        @Schema(description = "페이지 당 row의 개수 (0 이상의 정수)")
        @PositiveOrZero
        private Integer size;

        public CmtConfigurationGeneralCriteria toCriteria() {
            return CmtConfigurationGeneralCriteria.builder()
                    .configurationKeyList(configurationKeyList)
                    .configurationCategoryKey(configurationCategoryKey)
                    .configurationCategoryName(configurationCategoryName)
                    .configurationName(configurationName)
                    .configurationContent(configurationContent)
                    .useYn(useYn)
                    .description(description)
                    .build();
        }
    }

    @Schema(description = "배너 수정 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class UpdateBannerRequest {
        @Schema(description = "설정 항목 Key", example = "REFUND_COST")
        @Size(max = 50)
        private String configurationKey;

        @Schema(description = "설정 항목 카테고리 Key", example = "REFUND")
        @Size(max = 50)
        private String configurationCategoryKey;

        @Schema(description = "설정 항목 카테고리 명", example = "반품/환불 정보")
        @Size(max = 30)
        private String configurationCategoryName;

        @Schema(description = "설정 명", example = "반품/교환 비용")
        @Size(max = 30)
        private String configurationName;

        @Schema(description = "설정 내용", example = "고객의 단순변심 및 구매 착오로 인한 상품 반송비용은 고객 부담")
        private String configurationContent;

        @Schema(description = "사용 여부", example = "Y")
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String useYn;

        @Schema(description = "설명")
        @Size(max = 300)
        private String description;
    }

    @Schema(description = "배너 응답 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BannerResponse {
        @Schema(description = "설정 키")
        private String configurationKey;

        @Schema(description = "설정 카테고리 키")
        private String configurationCategoryKey;

        @Schema(description = "설정 카테고리 명")
        private String configurationCategoryName;

        @Schema(description = "설정 명")
        private String configurationName;

        @Schema(description = "설정 내용")
        private String configurationContent;

        @Schema(description = "사용 여부")
        private String useYn;

        @Schema(description = "등록 일시")
        private LocalDateTime registerDatetime;

        @Schema(description = "수정 일시")
        private LocalDateTime modifyDatetime;

        @Schema(description = "설명")
        private String description;

        public static BannerResponse fromEntity(CmtConfigurationGeneral cmtConfigurationGeneral) {
            return BannerResponse.builder()
                    .configurationKey(cmtConfigurationGeneral.getConfigurationKey())
                    .configurationCategoryKey(cmtConfigurationGeneral.getConfigurationCategoryKey())
                    .configurationCategoryName(cmtConfigurationGeneral.getConfigurationCategoryName())
                    .configurationName(cmtConfigurationGeneral.getConfigurationName())
                    .configurationContent(cmtConfigurationGeneral.getConfigurationContent())
                    .useYn(cmtConfigurationGeneral.getUseYn())
                    .registerDatetime(cmtConfigurationGeneral.getRegisterDatetime())
                    .modifyDatetime(cmtConfigurationGeneral.getModifyDatetime())
                    .description(cmtConfigurationGeneral.getDescription())
                    .build();
        }
    }

    @Schema(description = "배너 목록 및 페이징 정보 응답 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BannerPageResponse {
        @Schema(description = "배너 목록")
        private List<BannerResponse> siteInfoList;

        @Schema(description = "전체 요소 수")
        private long totalElements;

        @Schema(description = "전체 페이지 수")
        private int totalPages;
    }
}