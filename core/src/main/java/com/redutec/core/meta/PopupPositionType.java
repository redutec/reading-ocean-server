package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PopupPositionType {
    PPT001("CENTER", "중앙");

    private final String name;
    private final String description;
}