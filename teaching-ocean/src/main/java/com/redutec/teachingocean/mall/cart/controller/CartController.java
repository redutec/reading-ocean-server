package com.redutec.teachingocean.mall.cart.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.CartDto;
import com.redutec.teachingocean.mall.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mall/cart")
@Tag(name = "장바구니 API", description = "장바구니 API 모음")
public class CartController {
    private final ApiResponseManager apiResponseManager;
    private final CartService cartService;

    @Operation(summary = "리딩오션몰 - 특정 상품 선택 - 장바구니에 추가", description = "현재 로그인한 교육기관의 장바구니에 선택한 상품들을 추가")
    @PostMapping
    public ResponseEntity<ApiResponseBody> addCartItems(
            @RequestBody @Valid CartDto.AddCartItemsRequestWrapper addCartItemsRequests
    ) {
        return apiResponseManager.ok(cartService.addCartItems(addCartItemsRequests));
    }

    @Operation(summary = "리딩오션몰 - 장바구니", description = "현재 로그인한 교육기관의 장바구니에 담긴 상품 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> getCartItems(
            @ParameterObject @Valid CartDto.GetCartItemRequest getCartItemRequest
    ) {
        return apiResponseManager.ok(cartService.getCartItems(getCartItemRequest));
    }

    @Operation(summary = "리딩오션몰 - 장바구니 비우기", description = "현재 로그인한 교육기관의 장바구니를 비우기")
    @PutMapping
    public ResponseEntity<ApiResponseBody> clearCart() {
        cartService.clearCart();
        return apiResponseManager.noContent();
    }
}