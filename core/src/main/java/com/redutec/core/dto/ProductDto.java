package com.redutec.core.dto;

import com.redutec.core.meta.ProductCategory;
import com.redutec.core.meta.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDto {
    @Schema(description = "판매상품 등록 요청 객체")
    public record CreateProductRequest(
            @Schema(description = "상품명", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String name,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 1000)
            String details,

            @Schema(description = "가격", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @PositiveOrZero
            Integer price,

            @Schema(description = "할인율", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            @Max(100)
            Integer discountPercentage,

            @Schema(description = "판매상품에 대한 정보가 담긴 파일명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            MultipartFile attachmentFile,

            @Schema(description = "분류", requiredMode = Schema.RequiredMode.REQUIRED, example = "POWER_BOOK")
            @NotNull
            @Enumerated(EnumType.STRING)
            ProductCategory category,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.REQUIRED, example = "ACTIVE")
            @NotNull
            @Enumerated(EnumType.STRING)
            ProductStatus status
    ) {}

    @Schema(description = "판매상품 조회 요청 객체")
    public record FindProductRequest(
            @Schema(description = "판매상품 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> productIds,

            @Schema(description = "상품명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String name,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String details,

            @Schema(description = "최소 금액", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer minimumPrice,

            @Schema(description = "최대 금액", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer maximumPrice,

            @Schema(description = "최소 할인율", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer minimumDiscountPercentage,

            @Schema(description = "최대 할인율", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer maximumDiscountPercentage,

            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = ProductCategory.class)
            @Enumerated(EnumType.STRING)
            List<ProductCategory> categories,

            @Schema(description = "노출 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = ProductStatus.class)
            @Enumerated(EnumType.STRING)
            List<ProductStatus> statuses,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "판매상품 수정 요청 객체")
    public record UpdateProductRequest(
            @Schema(description = "상품명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String name,

            @Schema(description = "설명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 1000)
            String details,

            @Schema(description = "가격", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer price,

            @Schema(description = "할인율", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            @Max(100)
            Integer discountPercentage,

            @Schema(description = "판매상품에 대한 정보가 담긴 파일명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            MultipartFile attachmentFile,

            @Schema(description = "분류", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "POWER_BOOK")
            @Enumerated(EnumType.STRING)
            ProductCategory category,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "ACTIVE")
            @Enumerated(EnumType.STRING)
            ProductStatus status
    ) {}

    @Schema(description = "판매상품 응답 객체")
    public record ProductResponse(
            Long productId,
            String name,
            String details,
            Integer price,
            Integer discountPercentage,
            Integer priceAfterDiscount,
            String attachmentFileName,
            ProductCategory category,
            ProductStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "판매상품 응답 페이징 객체")
    public record ProductPageResponse(
            List<ProductResponse> products,
            Long totalElements,
            Integer totalPages
    ) {}
}