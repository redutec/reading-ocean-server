package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PopupPositionType {
    CENTER("PPT001", "중앙");

    private final String code;
    private final String description;
}