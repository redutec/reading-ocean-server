package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AcademyStatus {
    ACTIVE("활성화"),
    NON_PAYMENT("미납"),
    TERMINATION("계약종료"),
    WAIT("승인대기");

    private final String description;
}