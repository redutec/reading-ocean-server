package com.redutec.teachingocean.mall.order.service;

import com.redutec.core.dto.InstituteOrderDto;

public interface OrderService {
    /**
     * 리딩오션몰 - 특정 상품 선택 - 주문내역에 추가
     * @param addOrderItemsRequests 주문내역(교육기관) 등록 정보를 담은 DTO List
     * @return 등록된 주문내역(교육기관) 정보
     */
    InstituteOrderDto.OrderItemResponse addOrderItems(InstituteOrderDto.AddOrderItemsRequestWrapper addOrderItemsRequests);

    /**
     * 리딩오션몰 - 장바구니 - 현재 로그인한 교육기관의 장바구니에 있는 상품들을 주문내역에 추가
     * @return 등록된 주문내역(교육기관) 정보
     */
    InstituteOrderDto.OrderItemResponse addOrderItemsFromCart();

    /**
     * 리딩오션몰 - 주문내역 조회
     * @param getOrderItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 주문내역에 담긴 상품 응답 객체
     */
    InstituteOrderDto.OrderItemPageResponse getOrderItems(InstituteOrderDto.GetOrderItemRequest getOrderItemRequest);
}