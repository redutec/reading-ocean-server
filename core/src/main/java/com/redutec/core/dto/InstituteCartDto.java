package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public class InstituteCartDto {
    @Schema(description = "현재 로그인한 교사가 속한 교육기관의 장바구니에 상품들을 추가하는 요청 객체")
    public record AddCartItemsRequest(
            @Schema(description = "장바구니에 담을 상품 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long productId,

            @Schema(description = "장바구니에 담을 상품의 개수", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Integer quantity
    ) {}

    @Schema(description = "현재 로그인한 교사가 속한 교육기관의 장바구니(교육기관)의 특정 상품 조회 요청 객체")
    public record GetCartItemRequest(
            @Schema(description = "장바구니(교육기관)에 담긴 상품명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String productName,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "장바구니(교육기관) 응답 객체")
    public record CartItemResponse(
            List<CartItemDto> cartItems
    ) {}

    @Schema(description = "장바구니(교육기관) 응답 페이징 객체")
    public record CartItemPageResponse(
            List<CartItemResponse> cartItems,
            Long totalElements,
            Integer totalPages
    ) {}
}