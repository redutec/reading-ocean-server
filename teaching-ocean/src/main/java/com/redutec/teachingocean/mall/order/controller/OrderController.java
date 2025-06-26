package com.redutec.teachingocean.mall.order.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.OrderDto;
import com.redutec.teachingocean.mall.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "상품 주문 API", description = "상품 주문 API 모음")
public class OrderController {
    private final ApiResponseManager apiResponseManager;
    private final OrderService orderService;

    @Operation(summary = "리딩오션몰 - 특정 상품 선택 - 상품 주문에 추가", description = "현재 로그인한 교육기관이 선택한 상품들을 주문하기")
    @PostMapping
    public ResponseEntity<ApiResponseBody> addOrderItems(
            @RequestBody @Valid OrderDto.AddOrderItemsRequestWrapper addOrderItemsRequests
    ) {
        return apiResponseManager.ok(orderService.addOrderItems(addOrderItemsRequests));
    }

    @Operation(summary = "리딩오션몰 - 장바구니 - 현재 장바구니에 있는 상품들을 상품 주문에 추가", description = "현재 로그인한 교육기관의 장바구니에 담긴 상품들을 상품 주문에 추가")
    @PostMapping("/from-cart")
    public ResponseEntity<ApiResponseBody> addOrderItemsFromCart() {
        return apiResponseManager.ok(orderService.addOrderItemsFromCart());
    }

    @Operation(summary = "리딩오션몰 - 상품 주문 조회", description = "현재 로그인한 교육기관의 상품 주문 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid OrderDto.GetOrderItemRequest getOrderItemRequest
    ) {
        return apiResponseManager.ok(orderService.find(getOrderItemRequest));
    }
}