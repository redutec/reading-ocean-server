package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDERED("주문 접수"),
    PAYMENT_COMPLETED("결제 완료"),
    PREPARING("상품 준비 중"),
    SHIPPING("배송 중"),
    DELIVERED("배송 완료"),
    CANCELLED("주문 취소"),
    RETURN_REQUESTED("반품 요청"),
    RETURNED("반품 완료"),
    REFUNDED("환불 완료");

    private final String displayName;
}