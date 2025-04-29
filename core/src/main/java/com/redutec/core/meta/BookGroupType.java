package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookGroupType {
    RECOMMEND("추천 도서"),
    GRADE_LINK("학년 연계 도서");

    private final String displayName;
}