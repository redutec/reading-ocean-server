package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public class CartInstituteDto {
    @Schema(description = "현재 로그인한 교사가 속한 교육기관의 장바구니에 상품들을 추가하는 요청 객체")
    public record AddItemToCartInstituteRequest(
            @Schema(description = "장바구니에 담을 상품 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long productId,

            @Schema(description = "장바구니에 담을 상품의 개수", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Integer quantity
    ) {}

    @Schema(description = "장바구니(교육기관) 조회 요청 객체")
    public record FindCartInstituteRequest(
            @Schema(description = "장바구니를 소유한 교육기관 고유번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> instituteIds,

            @Schema(description = "장바구니(교육기관)에 담긴 상품 고유번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> productIds,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "장바구니(교육기관) 응답 객체")
    public record CartInstituteResponse(
            Long instituteId,
            List<CartItemDto> cartItems
    ) {}

    @Schema(description = "장바구니(교육기관) 응답 페이징 객체")
    public record CartInstitutePageResponse(
            List<CartInstituteResponse> cartInstitutes,
            Long totalElements,
            Integer totalPages
    ) {}
}