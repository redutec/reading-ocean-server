package com.redutec.teachingocean.mall.cart.service;

import com.redutec.core.dto.CartDto;

public interface CartService {
    /**
     * 리딩오션몰 - 특정 상품 선택 - 장바구니에 추가
     * @param addCartItemsRequests 장바구니(교육기관) 등록 정보를 담은 DTO List
     * @return 등록된 장바구니(교육기관) 정보
     */
    CartDto.CartItemResponse addCartItems(CartDto.AddCartItemsRequestWrapper addCartItemsRequests);

    /**
     * 리딩오션몰 - 장바구니 조회
     * @param getCartItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 장바구니에 담긴 상품 응답 객체
     */
    CartDto.CartItemPageResponse getCartItems(CartDto.GetCartItemRequest getCartItemRequest);

    /**
     * 리딩오션몰 - 장바구니 비우기
     */
    void clearCart();
}