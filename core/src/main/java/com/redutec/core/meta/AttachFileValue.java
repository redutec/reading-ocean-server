package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttachFileValue {
    MOBILE("AFV001", "모바일"),
    PC("AFV002", "PC"),
    ETC("AFV003", "기타");

    private final String code;
    private final String description;
}