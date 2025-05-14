package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PolicyType {
    TERMS_OF_SERVICE("이용약관"),
    PRIVACY_POLICY("개인정보 처리방침"),
    COOKIE_POLICY("쿠키 정책");

    private final String displayName;
}