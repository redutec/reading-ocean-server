package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BillingStatus {
    PENDING("결제대기"),
    PAID("결제완료"),
    UNPAID("미납"),
    REFUNDED("환불");

    private final String displayName;
}