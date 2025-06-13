package com.redutec.teachingocean.institute.mall.cart.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.InstituteCartDto;
import com.redutec.teachingocean.institute.mall.cart.service.InstituteCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "장바구니(교육기관) API", description = "장바구니(교육기관) API 모음")
public class InstituteCartController {
    private final ApiResponseManager apiResponseManager;
    private final InstituteCartService instituteCartService;

    @Operation(summary = "리딩오션몰 - 특정 상품 선택 - 장바구니에 추가", description = "현재 로그인한 교육기관의 장바구니에 선택한 상품들을 추가")
    @PostMapping
    public ResponseEntity<ApiResponseBody> addCartItems(
            @ParameterObject @Valid List<InstituteCartDto.AddCartItemsRequest> addCartItemsRequests
    ) {
        return apiResponseManager.ok(instituteCartService.addCartItems(addCartItemsRequests));
    }

    @Operation(summary = "리딩오션몰 - 장바구니", description = "현재 로그인한 교육기관의 장바구니에 담긴 상품 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> getCartItems(
            @ParameterObject @Valid InstituteCartDto.GetCartItemRequest getCartItemRequest
    ) {
        return apiResponseManager.ok(instituteCartService.getCartItems(getCartItemRequest));
    }

    @Operation(summary = "리딩오션몰 - 장바구니 비우기", description = "현재 로그인한 교육기관의 장바구니를 비우기")
    @PatchMapping
    public ResponseEntity<ApiResponseBody> clearCart() {
        instituteCartService.clearCart();
        return apiResponseManager.noContent();
    }
}