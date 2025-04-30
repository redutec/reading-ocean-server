package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategory {
    POWER_BOOK("파워북"),
    READING_LEVEL_TEST_PAPER("독서능력진단검사"),
    PROMOTIONAL_MATERIALS("홍보물품"),
    SIGNBOARD("현판"),
    REGISTRATION_FEE("가입비 결제"),
    POWER_BOOK_PDF("파워북(PDF)");

    private final String displayName;
}