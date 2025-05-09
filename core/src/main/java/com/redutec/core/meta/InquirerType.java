package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InquirerType {
    TEACHER("교사"),
    STUDENT("학생"),
    GUEST("비회원");

    private final String displayName;
}