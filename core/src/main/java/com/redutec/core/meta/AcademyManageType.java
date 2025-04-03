package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AcademyManageType {
    AMT001("BASIC", "베이직"),
    AMT002("STANDARD", "스탠다드");

    private final String name;
    private final String description;
}
