package com.redutec.admin.article.dto;

import com.redutec.core.criteria.BdtArticleCriteria;
import com.redutec.core.entity.BdtArticle;
import com.redutec.core.entity.BdtArticleDisplay;
import com.redutec.core.meta.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDto {
    @Schema(description = "게시물(배너, 팝업, FAQ 등) 조회 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class FindArticleRequest {
        @Schema(description = "게시물 고유번호")
        private List<@Positive Integer> articleNoList;

        @Schema(description = "게시물 제목")
        @Size(max = 100, message = "제목은 100글자까지 검색 가능합니다")
        private String articleTitle;

        @Schema(description = "배너 내용(배너 헤드라인)")
        private String articleContent;

        @Schema(description = "배너 상세 내용")
        private String articleContentDetail;

        @Schema(description = "노출여부(Y: 노출 | N: 미노출)", example = "Y")
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String displayYn;

        @Schema(description = "표시할 도메인", example = "TEACHING_OCEAN")
        @ElementCollection(fetch = FetchType.LAZY)
        @Enumerated(EnumType.STRING)
        private List<Domain> domainList;

        @Schema(description = "페이지 번호 (0 이상의 정수, 예: 0)")
        @PositiveOrZero
        private Integer page;

        @Schema(description = "페이지당 row의 개수 (1 이상의 자연수, 예: 30)")
        @Positive
        private Integer size;

        public BdtArticleCriteria toCriteria() {
            return BdtArticleCriteria.builder()
                    .articleNoList(this.articleNoList)
                    .articleTitle(this.articleTitle)
                    .articleContent(this.articleContent)
                    .articleContentDetail(this.articleContentDetail)
                    .displayYn(this.displayYn)
                    .domainList(this.domainList)
                    .build();
        }
    }

    @Schema(description = "게시물의 이미지 파일 응답 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ArticleAttachFileResponse {
        private Integer articleAttachFileNo;
        private Integer articleNo;
        private AttachFileValue attachFileValue;
        private String attachFileName;
        private String attachmentFilePath;
        private LocalDateTime registerDatetime;
        private LocalDateTime modifyDatetime;
    }

    // Banner
    @Schema(description = "배너 등록 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class CreateBannerRequest {
        // bdt_article
        @Schema(description = "카테고리")
        @Enumerated(EnumType.STRING)
        private CategoryValue categoryValue;

        @Schema(description = "게시물 제목", example = "테스트 게시물 제목")
        @NotBlank
        @Size(max = 100)
        private String bannerTitle;

        @Schema(description = "게시물 내용", example = "테스트 게시물 헤드라인")
        private String bannerContent;

        @Schema(description = "게시물 상세 내용", example = "테스트 게시물 내용")
        private String bannerContentDetail;

        @Schema(description = "노출여부(Y: 노출 | N: 미노출)", example = "Y")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String displayYn;

        @Schema(description = "표시할 도메인", example = "TEACHING_OCEAN")
        @Enumerated(EnumType.STRING)
        private Domain domain;

        // bdt_article_display
        @Schema(description = "팝업 위치", example = "PPT000")
        private PopupPositionType popupPositionType;

        @Schema(description = "문자열 색상")
        @Pattern(regexp = "^(Black|White)$", message = "textColor는 'Black' 또는 'White'만 가능합니다.")
        private String textColor;

        @Schema(description = "배경 색상", example = "rgba(0, 0, 0, 1)")
        @Pattern(
                regexp = "^rgba\\(\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:0(?:\\.\\d+)?|1(?:\\.0+)?)\\s*\\)$",
                message = "backgroundColor는 rgba 형식(예: rgba(0, 0, 0, 1))만 허용됩니다."
        )
        private String backgroundColor;

        @Schema(description = "노출 순서", example = "10")
        @Min(value = 0, message = "displayOrder는 0 이상이어야 합니다.")
        @Max(value = 255, message = "displayOrder는 255 이하이어야 합니다.")
        private Byte displayOrder;

        @Schema(description = "배너 유형(BNT001: 캐러셀)", example = "BNT001")
        @Enumerated(EnumType.STRING)
        private BannerType bannerType;

        @Schema(description = "링크 URL", example = "www.teachingschool.co.kr")
        @NotBlank
        private String linkUrl;

        @Schema(description = "새 창으로 열기 여부(Y: 새 창으로 열기 | N: 현재 창에서 이동)", example = "Y")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "새 창으로 열기 여부는 'Y' 또는 'N'이어야 합니다.")
        private String displayNewWindowYn;

        @Schema(description = "노출 시작 시각")
        @NotNull
        private LocalDateTime displayBeginDatetime;

        @Schema(description = "노출 종료 시각")
        @NotNull
        private LocalDateTime displayEndDatetime;

        public BdtArticle toBdtArticleEntity() {
            return BdtArticle.builder()
                    .articleBoardNo(5)
                    .articleTitle(this.bannerTitle)
                    .articleContent(this.bannerContent)
                    .articleContentDetail(this.bannerContentDetail)
                    .displayYn(this.displayYn)
                    .domain(this.domain)
                    .build();
        }

        public BdtArticleDisplay toBdtArticleDisplayEntity(BdtArticle article) {
            return BdtArticleDisplay.builder()
                    .article(article)
                    .popupPositionType(this.popupPositionType)
                    .textColor(this.textColor)
                    .backgroundColor(this.backgroundColor)
                    .displayOrder(this.displayOrder)
                    .bannerType(this.bannerType)
                    .linkURL(this.linkUrl)
                    .displayNewWindowYn(this.displayNewWindowYn)
                    .displayBeginDatetime(this.displayBeginDatetime)
                    .displayEndDatetime(this.displayEndDatetime)
                    .build();
        }
    }

    @Schema(description = "배너 수정 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class UpdateBannerRequest {
        // bdt_article
        @Schema(description = "카테고리", example = "LBV004")
        @Enumerated(EnumType.STRING)
        private CategoryValue categoryValue;

        @Schema(description = "게시물 제목", example = "테스트 게시물 제목")
        @NotBlank
        @Size(max = 100)
        private String articleTitle;

        @Schema(description = "게시물 내용", example = "테스트 내용")
        private String articleContent;

        @Schema(description = "게시물 상세 내용", example = "테스트 상세 내용")
        private String articleContentDetail;

        @Schema(description = "노출여부(Y: 노출 | N: 미노출)", example = "Y")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String displayYn;

        @Schema(description = "표시할 도메인", example = "TEACHING_OCEAN")
        @Enumerated(EnumType.STRING)
        private Domain domain;

        // bdt_article_display
        @Schema(description = "팝업 위치", example = "PPT000")
        private PopupPositionType popupPositionType;

        @Schema(description = "문자열 색상")
        @Pattern(regexp = "^(Black|White)$", message = "textColor는 'Black' 또는 'White'만 가능합니다.")
        private String textColor;

        @Schema(description = "배경 색상", example = "rgba(0, 0, 0, 1)")
        @Pattern(
                regexp = "^rgba\\(\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:0(?:\\.\\d+)?|1(?:\\.0+)?)\\s*\\)$",
                message = "backgroundColor는 rgba 형식(예: rgba(0, 0, 0, 1))만 허용됩니다."
        )
        private String backgroundColor;

        @Schema(description = "노출 순서", example = "10")
        @Min(value = 0, message = "displayOrder는 0 이상이어야 합니다.")
        @Max(value = 255, message = "displayOrder는 255 이하이어야 합니다.")
        private Integer displayOrder;

        @Schema(description = "배너 유형(BNT001: 캐러셀)", example = "BNT001")
        @Enumerated(EnumType.STRING)
        private BannerType bannerType;

        @Schema(description = "링크 URL", example = "www.teachingschool.co.kr")
        @NotBlank
        private String linkUrl;

        @Schema(description = "새 창으로 열기 여부(Y: 새 창으로 열기 | N: 현재 창에서 이동)", example = "Y")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "새 창으로 열기 여부는 'Y' 또는 'N'이어야 합니다.")
        private String displayNewWindowYn;

        @Schema(description = "노출 시작 시각")
        @NotNull
        private LocalDateTime displayBeginDatetime;

        @Schema(description = "노출 종료 시각")
        @NotNull
        private LocalDateTime displayEndDatetime;
    }

    @Schema(description = "배너 응답 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BannerResponse {
        private Integer bannerNo;
        private String bannerTitle;
        private String bannerContent;
        private String bannerContentDetail;
        private Domain domain;
        private String displayYn;
        private Integer displayOrder;
        private BannerType bannerType;
        private String backgroundColor;
        private String textColor;
        private LocalDateTime displayBeginDatetime;
        private LocalDateTime displayEndDatetime;
        private String linkUrl;
        private String displayNewWindowYn;
        private ArticleAttachFileResponse pcImageFile;
        private ArticleAttachFileResponse mobileImageFile;
        private LocalDateTime registerDatetime;
        private LocalDateTime modifyDatetime;

        public static BannerResponse fromEntity(
                BdtArticle article,
                BdtArticleDisplay articleDisplay,
                ArticleAttachFileResponse pcImageFile,
                ArticleAttachFileResponse mobileImageFile
        ) {
            return BannerResponse.builder()
                    .bannerNo(article.getArticleNo())
                    .bannerTitle(article.getArticleTitle())
                    .bannerContent(article.getArticleContent())
                    .bannerContentDetail(article.getArticleContentDetail())
                    .displayYn(article.getDisplayYn())
                    .domain(article.getDomain())
                    // articleDisplay가 null이면 관련 값은 null로 처리
                    .displayOrder(articleDisplay != null ? Integer.valueOf(articleDisplay.getDisplayOrder()) : null)
                    .bannerType(articleDisplay != null ? articleDisplay.getBannerType() : null)
                    .backgroundColor(articleDisplay != null ? articleDisplay.getBackgroundColor() : null)
                    .textColor(articleDisplay != null ? articleDisplay.getTextColor() : null)
                    .displayBeginDatetime(articleDisplay != null ? articleDisplay.getDisplayBeginDatetime() : null)
                    .displayEndDatetime(articleDisplay != null ? articleDisplay.getDisplayEndDatetime() : null)
                    .linkUrl(articleDisplay != null ? articleDisplay.getLinkURL() : null)
                    .displayNewWindowYn(articleDisplay != null ? articleDisplay.getDisplayNewWindowYn() : null)
                    .pcImageFile(pcImageFile)
                    .mobileImageFile(mobileImageFile)
                    .registerDatetime(article.getRegisterDatetime())
                    .modifyDatetime(article.getModifyDatetime())
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
        private List<BannerResponse> bannerList;

        @Schema(description = "전체 요소 수")
        private long totalElements;

        @Schema(description = "전체 페이지 수")
        private int totalPages;
    }
}