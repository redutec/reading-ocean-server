package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AcademyManageType {
    /*#academyManageType#*/
    BASIC("AMT001", "베이직"),
    STANDARD("AMT002", "스탠다드");

    private final String oldCodeValue;
    private final String description;

    public static AcademyManageType findBySystemCodeValue(String systemCodeValue) {
        for (AcademyManageType academyManageType : AcademyManageType.values()) {
            if (academyManageType.getOldCodeValue().equalsIgnoreCase(systemCodeValue)) {
                return academyManageType;
            }
        }
        return BASIC;
    }
}
