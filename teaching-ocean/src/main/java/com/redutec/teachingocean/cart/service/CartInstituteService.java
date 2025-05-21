package com.redutec.teachingocean.cart.service;

import com.redutec.core.dto.CartInstituteDto;

public interface CartInstituteService {
    /**
     * 장바구니(교육기관) 등록
     * @param createCartInstituteRequest 장바구니(교육기관) 등록 정보를 담은 DTO
     * @return 등록된 장바구니(교육기관) 정보
     */
    CartInstituteDto.CartInstituteResponse create(CartInstituteDto.AddItemToCartInstituteRequest createCartInstituteRequest);

    /**
     * 조건에 맞는 장바구니(교육기관) 목록 조회
     * @param findCartInstituteRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 장바구니(교육기관) 목록 및 페이징 정보
     */
    CartInstituteDto.CartInstitutePageResponse find(CartInstituteDto.FindCartInstituteRequest findCartInstituteRequest);

    /**
     * 특정 장바구니(교육기관) 조회
     * @param cartInstituteId 장바구니(교육기관) 고유번호
     * @return 특정 장바구니(교육기관) 응답 객체
     */
    CartInstituteDto.CartInstituteResponse findById(Long cartInstituteId);

    /**
     * 특정 장바구니(교육기관) 수정
     * @param cartInstituteId 수정할 장바구니(교육기관)의 ID
     * @param updateCartInstituteRequest 장바구니(교육기관) 수정 요청 객체
     */
    void update(Long cartInstituteId, CartInstituteDto.UpdateCartInstituteRequest updateCartInstituteRequest);

    /**
     * 특정 장바구니(교육기관) 삭제
     * @param cartInstituteId 삭제할 장바구니(교육기관)의 ID
     */
    void delete(Long cartInstituteId);
}