package com.redutec.teachingocean.mall.order.service;

import com.redutec.core.dto.OrderDto;

public interface OrderService {
    /**
     * 리딩오션몰 - 특정 상품 선택 - 상품 주문에 추가
     * @param addOrderItemsRequests 상품 주문에 추가할 요청 객체
     * @return 등록한 상품 주문 정보
     */
    OrderDto.OrderItemResponse addOrderItems(OrderDto.AddOrderItemsRequestWrapper addOrderItemsRequests);

    /**
     * 리딩오션몰 - 장바구니 - 현재 장바구니에 있는 상품들을 상품 주문에 추가
     * @return 등록한 상품 주문 정보
     */
    OrderDto.OrderItemResponse addOrderItemsFromCart();

    /**
     * 리딩오션몰 - 상품 주문 조회
     * @param getOrderItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 상품 주문 응답 객체
     */
    OrderDto.OrderItemPageResponse find(OrderDto.GetOrderItemRequest getOrderItemRequest);
}