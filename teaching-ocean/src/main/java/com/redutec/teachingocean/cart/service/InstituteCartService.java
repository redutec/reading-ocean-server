package com.redutec.teachingocean.cart.service;

import com.redutec.core.dto.InstituteCartDto;

import java.util.List;

public interface InstituteCartService {
    /**
     * 티칭오션몰 - 특정 상품 선택 - 장바구니에 추가
     * @param addCartItemsRequests 장바구니(교육기관) 등록 정보를 담은 DTO List
     * @return 등록된 장바구니(교육기관) 정보
     */
    InstituteCartDto.CartItemResponse addCartItems(List<InstituteCartDto.AddCartItemsRequest> addCartItemsRequests);

    /**
     * 티칭오션몰 - 장바구니
     * @param getCartItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 장바구니에 담긴 상품 응답 객체
     */
    InstituteCartDto.CartItemPageResponse getCartItems(InstituteCartDto.GetCartItemRequest getCartItemRequest);

    /**
     * 티칭오션몰 - 장바구니 비우기
     */
    void clearCart();
}