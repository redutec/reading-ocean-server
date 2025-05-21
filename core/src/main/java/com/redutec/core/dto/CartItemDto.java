package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CartItemDto (
    @Schema(description = "장바구니에 담기는 상품 정보", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    ProductDto.ProductResponse product,

    @Schema(description = "장바구니에 담기는 상품의 개수", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    Integer quantity
) {}