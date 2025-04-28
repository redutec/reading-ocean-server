package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InstituteType {
    ACADEMY("학원"),
    SCHOOL("학교");

    private final String displayName;
}
