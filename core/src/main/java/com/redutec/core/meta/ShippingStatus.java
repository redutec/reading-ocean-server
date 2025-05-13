package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShippingStatus {
    READY("배송 준비 중"),
    DISPATCHED("집하 완료"),
    IN_TRANSIT("배송 중"),
    OUT_FOR_DELIVERY("배송 출발"),
    DELIVERED("배송 완료"),
    RETURN_REQUESTED("반품 요청"),
    RETURNED("반품 완료"),
    CANCELLED("배송 취소");

    private final String displayName;
}