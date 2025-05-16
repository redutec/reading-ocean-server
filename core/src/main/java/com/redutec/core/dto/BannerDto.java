package com.redutec.core.dto;

import com.redutec.core.meta.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class BannerDto {
    @Schema(description = "배너 등록 요청 객체")
    public record CreateBannerRequest(
            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            String content,

            @Schema(description = "링크 URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 300)
            String linkUrl,

            @Schema(description = "첨부 파일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            MultipartFile attachedFile,

            @Schema(description = "우선순위(0이 최상위)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Min(0)
            @PositiveOrZero
            Integer priority,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @NotNull
            Boolean visible,

            @Schema(description = "노출 시작일시", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDateTime visibleStartAt,

            @Schema(description = "노출 종료일시", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            LocalDateTime visibleEndAt
    ) {}

    @Schema(description = "배너 조회 요청 객체")
    public record FindBannerRequest(
            @Schema(description = "배너 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> bannerIds,

            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = Domain.class)
            @Enumerated(EnumType.STRING)
            List<Domain> domains,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean visible,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "배너 수정 요청 객체")
    public record UpdateBannerRequest(
            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "링크 URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 300)
            String linkUrl,

            @Schema(description = "첨부 파일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            MultipartFile attachedFile,

            @Schema(description = "우선순위(0이 최상위)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Min(0)
            @PositiveOrZero
            Integer priority,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "true")
            Boolean visible,

            @Schema(description = "노출 시작일시", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime visibleStartAt,

            @Schema(description = "노출 종료일시", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime visibleEndAt
    ) {}

    @Schema(description = "배너 응답 객체")
    public record BannerResponse(
            Long bannerId,
            Domain domain,
            String title,
            String content,
            String linkUrl,
            String attachedFile,
            Integer priority,
            Boolean visible,
            LocalDateTime visibleStartAt,
            LocalDateTime visibleEndAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "배너 응답 페이징 객체")
    public record BannerPageResponse(
            List<BannerResponse> banners,
            Long totalElements,
            Integer totalPages
    ) {}
}