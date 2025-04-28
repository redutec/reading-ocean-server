package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookGenre {
    LITERATURE("문학"),
    SCIENCE("과학"),
    SOCIAL_SCIENCES("사회"),
    HISTORY("역사"),
    ARTS_AND_SPORTS("예체능"),
    BIOGRAPHY("인물"),
    GENERAL_KNOWLEDGE("교양");

    private final String displayName;
}