package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InstituteManagementType {
    BASIC("베이직"),
    STANDARD("스탠다드");

    private final String displayName;
}
