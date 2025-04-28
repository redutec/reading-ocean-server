package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PopupPositionType {
    CENTER("중앙");

    private final String displayName;
}