package com.redutec.admin.v1.article.dto;

import com.redutec.core.meta.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class BannerDto {
    @Schema(description = "배너 등록 요청 객체")
    public record CreateBannerRequest(
        // bdt_article
        @Schema(description = "배너 제목", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 배너 제목")
        @NotBlank
        @Size(max = 100)
        String bannerTitle,

        @Schema(description = "배너 내용", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 배너 헤드라인")
        @NotBlank
        String bannerContent,

        @Schema(description = "배너 상세 내용", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 배너 내용")
        @NotBlank
        String bannerContentDetail,

        @Schema(description = "노출여부(Y: 노출 | N: 미노출)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Y")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        String displayYn,

        @Schema(description = "표시할 도메인", requiredMode = Schema.RequiredMode.REQUIRED, example = "TEACHING_OCEAN")
        @NotBlank
        @Enumerated(EnumType.STRING)
        Domain domain,

        // bdt_article_display
        @Schema(description = "문자열 색상", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(regexp = "^(Black|White)$", message = "textColor는 'Black' 또는 'White'만 가능합니다.")
        String textColor,

        @Schema(description = "배경 색상", requiredMode = Schema.RequiredMode.REQUIRED, example = "rgba(0, 0, 0, 1)")
        @NotBlank
        @Pattern(
                regexp = "^rgba\\(\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:0(?:\\.\\d+)?|1(?:\\.0+)?)\\s*\\)$",
                message = "backgroundColor는 rgba 형식(예: rgba(0, 0, 0, 1))만 허용됩니다."
        )
        String backgroundColor,

        @Schema(description = "노출 순서", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
        @NotBlank
        @Min(value = 0, message = "displayOrder는 0 이상이어야 합니다.")
        @Max(value = 255, message = "displayOrder는 255 이하이어야 합니다.")
        Byte displayOrder,

        @Schema(description = "배너 유형(BNT001: 캐러셀)", requiredMode = Schema.RequiredMode.REQUIRED, example = "BNT001")
        @NotBlank
        @Enumerated(EnumType.STRING)
        BannerType bannerType,

        @Schema(description = "링크 URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "www.teachingschool.co.kr")
        @NotBlank
        String linkUrl,

        @Schema(description = "새 창으로 열기 여부(Y: 새 창으로 열기 | N: 현재 창에서 이동)", requiredMode = Schema.RequiredMode.REQUIRED, example = "Y")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "새 창으로 열기 여부는 'Y' 또는 'N'이어야 합니다.")
        String displayNewWindowYn,

        @Schema(description = "노출 시작 시각", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        LocalDateTime displayBeginDatetime,

        @Schema(description = "노출 종료 시각", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        LocalDateTime displayEndDatetime
    ) {}

    @Schema(description = "배너 조회 요청 객체")
    public record FindBannerRequest(
        @Schema(description = "배너 고유번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        List<@Positive Integer> bannerNoList,

        @Schema(description = "배너 제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 100, message = "제목은 100글자까지 검색 가능합니다")
        String bannerTitle,

        @Schema(description = "배너 내용(배너 헤드라인)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String bannerContent,

        @Schema(description = "배너 상세 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String bannerContentDetail,

        @Schema(description = "노출여부(Y: 노출 | N: 미노출)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        String displayYn,

        @Schema(description = "표시할 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @ElementCollection(fetch = FetchType.LAZY)
        @Enumerated(EnumType.STRING)
        List<Domain> domainList,

        @Schema(description = "페이지 번호(0 이상의 정수, 예: 0)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @PositiveOrZero
        Integer page,

        @Schema(description = "페이지당 row의 개수(1 이상의 자연수, 예: 30)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Positive
        Integer size
    ) {}

    @Schema(description = "배너 수정 요청 객체")
    public record UpdateBannerRequest(
        // bdt_article
        @Schema(description = "게시물 제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "테스트 게시물 제목")
        @Size(max = 100)
        String bannerTitle,

        @Schema(description = "게시물 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "테스트 내용")
        String bannerContent,

        @Schema(description = "게시물 상세 내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "테스트 상세 내용")
        String bannerContentDetail,

        @Schema(description = "노출여부(Y: 노출 | N: 미노출)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Y")
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        String displayYn,

        @Schema(description = "표시할 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "TEACHING_OCEAN")
        @Enumerated(EnumType.STRING)
        Domain domain,

        // bdt_article_display
        @Schema(description = "팝업 위치", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "PPT000")
        PopupPositionType popupPositionType,

        @Schema(description = "문자열 색상", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Pattern(regexp = "^(Black|White)$", message = "textColor는 'Black' 또는 'White'만 가능합니다.")
        String textColor,

        @Schema(description = "배경 색상", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "rgba(0, 0, 0, 1)")
        @Pattern(
                regexp = "^rgba\\(\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:[0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\s*,\\s*(?:0(?:\\.\\d+)?|1(?:\\.0+)?)\\s*\\)$",
                message = "backgroundColor는 rgba 형식(예: rgba(0, 0, 0, 1))만 허용됩니다."
        )
        String backgroundColor,

        @Schema(description = "노출 순서", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10")
        @Min(value = 0, message = "displayOrder는 0 이상이어야 합니다.")
        @Max(value = 255, message = "displayOrder는 255 이하이어야 합니다.")
        Byte displayOrder,

        @Schema(description = "배너 유형(BNT001: 캐러셀)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "BNT001")
        @Enumerated(EnumType.STRING)
        BannerType bannerType,

        @Schema(description = "링크 URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "www.teachingschool.co.kr")
        @NotBlank
        String linkUrl,

        @Schema(description = "새 창으로 열기 여부(Y: 새 창으로 열기 | N: 현재 창에서 이동)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Y")
        @Pattern(regexp = "^[YN]$", message = "새 창으로 열기 여부는 'Y' 또는 'N'이어야 합니다.")
        String displayNewWindowYn,

        @Schema(description = "노출 시작 시각", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDateTime displayBeginDatetime,

        @Schema(description = "노출 종료 시각", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDateTime displayEndDatetime
    ) {}

    @Schema(description = "배너의 첨부 파일 응답 객체")
    public record BannerAttachFileResponse(
            Integer bannerAttachFileNo,
            Integer bannerNo,
            AttachFileValue attachFileValue,
            String attachFileName,
            String attachmentFilePath,
            LocalDateTime registerDatetime,
            LocalDateTime modifyDatetime
    ) {}

    @Schema(description = "배너 응답 객체")
    public record BannerResponse(
        Integer bannerNo,
        String bannerTitle,
        String bannerContent,
        String bannerContentDetail,
        Domain domain,
        String displayYn,
        Byte displayOrder,
        BannerType bannerType,
        String backgroundColor,
        String textColor,
        LocalDateTime displayBeginDatetime,
        LocalDateTime displayEndDatetime,
        String linkUrl,
        String displayNewWindowYn,
        BannerAttachFileResponse pcImageFile,
        BannerAttachFileResponse mobileImageFile,
        LocalDateTime registerDatetime,
        LocalDateTime modifyDatetime
    ) {}

    @Schema(description = "배너 목록 및 페이징 정보 응답 객체")
    public record BannerPageResponse(
        List<BannerResponse> bannerList,
        Long totalElements,
        Integer totalPages
    ) {}
}