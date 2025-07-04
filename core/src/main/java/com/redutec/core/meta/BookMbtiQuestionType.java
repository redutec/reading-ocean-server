package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookMbtiQuestionType {
    E_I("E/I"),
    S_N("S/N"),
    F_T("F/T"),
    J_P("J/P");

    private final String displayName;
}