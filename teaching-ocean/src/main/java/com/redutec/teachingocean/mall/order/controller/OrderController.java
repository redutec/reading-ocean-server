package com.redutec.teachingocean.mall.order.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.InstituteOrderDto;
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
@RequestMapping("/mall/order")
@Tag(name = "주문내역(교육기관) API", description = "주문내역(교육기관) API 모음")
public class OrderController {
    private final ApiResponseManager apiResponseManager;
    private final OrderService orderService;

    @Operation(summary = "리딩오션몰 - 특정 상품 선택 - 주문내역에 추가", description = "현재 로그인한 교육기관의 주문내역에 선택한 상품들을 추가")
    @PostMapping
    public ResponseEntity<ApiResponseBody> addOrderItems(
            @RequestBody @Valid InstituteOrderDto.AddOrderItemsRequestWrapper addOrderItemsRequests
    ) {
        return apiResponseManager.ok(orderService.addOrderItems(addOrderItemsRequests));
    }

    @Operation(summary = "리딩오션몰 - 장바구니 - 현재 장바구니에 있는 상품들을 주문내역에 추가", description = "현재 로그인한 교육기관의 장바구니에 담긴 상품들을 주문내역에 추가")
    @PostMapping("/from-cart")
    public ResponseEntity<ApiResponseBody> addOrderItemsFromCart() {
        return apiResponseManager.ok(orderService.addOrderItemsFromCart());
    }

    @Operation(summary = "리딩오션몰 - 주문내역 조회", description = "현재 로그인한 교육기관의 주문내역를 비우기")
    @GetMapping
    public ResponseEntity<ApiResponseBody> getOrderItems(
            @ParameterObject @Valid InstituteOrderDto.GetOrderItemRequest getOrderItemRequest
    ) {
        return apiResponseManager.ok(orderService.getOrderItems(getOrderItemRequest));
    }
}