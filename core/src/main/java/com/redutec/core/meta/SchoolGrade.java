package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SchoolGrade {
    ALL(-1, "전체학년"),
    CHILD(0, "누리과정"),
    ELEMENTARY_1(1, "초등1"),
    ELEMENTARY_2(2, "초등2"),
    ELEMENTARY_3(3, "초등3"),
    ELEMENTARY_4(4, "초등4"),
    ELEMENTARY_5(5, "초등5"),
    ELEMENTARY_6(6, "초등6"),
    MIDDLE_1(7, "중등1"),
    MIDDLE_2(8, "중등2"),
    MIDDLE_3(9, "중등3"),
    HIGH_1(10, "고등1"),
    HIGH_2(11, "고등2"),
    HIGH_3(12, "고등3");

    private final Integer order;
    private final String displayName;
}