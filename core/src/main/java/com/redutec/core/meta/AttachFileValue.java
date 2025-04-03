package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttachFileValue {
    AFV001("MOBILE", "모바일"),
    AFV002("PC", "PC"),
    AFV003("ETC", "기타");

    private final String name;
    private final String description;
}