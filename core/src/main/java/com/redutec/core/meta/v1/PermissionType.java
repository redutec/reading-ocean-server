package com.redutec.core.meta.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionType {
    PMT001("All", "모든권한"),
    PMT002("Read only", "조회가능"),
    PMT003("No permission", "권한없음");

    private final String name;
    private final String description;
}