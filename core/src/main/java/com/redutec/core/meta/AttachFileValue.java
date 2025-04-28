package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttachFileValue {
    MOBILE("모바일"),
    PC("PC"),
    ETC("기타");

    private final String displayName;
}