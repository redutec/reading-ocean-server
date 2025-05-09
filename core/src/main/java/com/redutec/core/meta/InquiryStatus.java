package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquiryStatus {
    PENDING("응답대기"),
    IN_PROGRESS("처리중"),
    RESPONDED("응답완료"),
    CLOSED("종료");

    private final String displayName;
}