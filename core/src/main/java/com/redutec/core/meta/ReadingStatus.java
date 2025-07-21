package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReadingStatus {
    PENDING("시작 전"),
    IN_PROGRESS("읽는 중"),
    COMPLETED("완료");

    private final String displayName;
}