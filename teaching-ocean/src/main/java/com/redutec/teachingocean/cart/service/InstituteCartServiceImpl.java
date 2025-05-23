package com.redutec.teachingocean.cart.service;

import com.redutec.core.dto.InstituteCartDto;
import com.redutec.core.mapper.InstituteCartMapper;
import com.redutec.core.repository.InstituteCartRepository;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class InstituteCartServiceImpl implements InstituteCartService {
    private final InstituteCartMapper instituteCartMapper;
    private final InstituteCartRepository instituteCartRepository;
    private final AuthenticationService authenticationService;

    /**
     * 티칭오션몰 - 특정 상품 선택 - 장바구니에 추가
     *
     * @param addCartItemsRequests 장바구니(교육기관) 등록 정보를 담은 DTO List
     * @return 등록된 장바구니(교육기관) 정보
     */
    @Override
    @Transactional
    public InstituteCartDto.CartItemResponse addCartItems(List<InstituteCartDto.AddCartItemsRequest> addCartItemsRequests) {
        return null;
    }

    /**
     * 티칭오션몰 - 장바구니
     *
     * @param getCartItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 장바구니에 담긴 상품 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteCartDto.CartItemPageResponse getCartItems(InstituteCartDto.GetCartItemRequest getCartItemRequest) {
        return null;
    }

    /**
     * 티칭오션몰 - 장바구니 비우기
     */
    @Override
    @Transactional
    public void clearCart() {

    }
}