package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AcademyOperationStatus {
    OPERATING("운영"),
    CLOSED("휴원"),
    PREPARING("준비"),
    RESERVED("선점");

    private final String description;
}