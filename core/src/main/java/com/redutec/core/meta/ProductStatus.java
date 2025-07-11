package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    ACTIVE("활성화"),
    INACTIVE("비활성화");

    private final String displayName;
}