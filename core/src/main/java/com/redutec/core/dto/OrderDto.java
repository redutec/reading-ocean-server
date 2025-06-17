package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class OrderDto {
    @Schema(description = "현재 로그인한 교사가 속한 교육기관의 주문내역에 상품들을 추가하는 요청 객체")
    public record AddOrderItemsRequest(
            @Schema(description = "주문내역에 담을 상품 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long productId,

            @Schema(description = "주문내역에 담을 상품의 개수", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Integer quantity
    ) {}

    public record AddOrderItemsRequestWrapper(
            @Schema(description = "추가할 상품 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(min = 1)
            List<@Valid AddOrderItemsRequest> addOrderItemsRequests,

            @Schema(description = "수취인 이름", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank
            @Size(max = 20)
            String recipientName,

            @Schema(description = "수취인 연락처", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank
            @Pattern(regexp = "^[0-9]{10,11}$", message = "전화번호는 10~11자리 숫자여야 합니다.")
            String recipientPhoneNumber,

            @Schema(description = "배송지 우편번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank
            @Pattern(regexp = "^[0-9]{5}$", message = "우편번호는 5자리 숫자이어야 합니다.")
            String postalCode,

            @Schema(description = "배송지", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotBlank
            @Size(max = 200)
            String deliveryAddress,

            @Schema(description = "배송 업체명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String deliveryCompanyName
    ) {}

    @Schema(description = "현재 로그인한 교사가 속한 교육기관의 주문내역(교육기관)의 특정 상품 조회 요청 객체")
    public record GetOrderItemRequest(
            @Schema(description = "주문내역(교육기관)에 담긴 상품명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String productName,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "주문내역(교육기관) 응답 객체")
    public record OrderItemResponse(
            List<OrderItemDto> orderItems
    ) {}

    @Schema(description = "주문내역(교육기관) 응답 페이징 객체")
    public record OrderItemPageResponse(
            List<OrderItemResponse> orderItems,
            Long totalElements,
            Integer totalPages
    ) {}
}