package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeacherRole {
    CHIEF("원장"),
    TEACHER("교사");

    private final String displayName;
}
