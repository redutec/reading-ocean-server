package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OrderItemDto(
    @Schema(description = "주문내역의 상품 정보", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    ProductDto.ProductResponse product,

    @Schema(description = "주문내역의 상품의 개수", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    Integer quantity
) {}