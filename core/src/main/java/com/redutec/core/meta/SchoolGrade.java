package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SchoolGrade {
    ALL(1, "전체학년"),
    CHILD(2, "누리과정"),
    ELEMENTARY_1(3, "초등1"),
    ELEMENTARY_2(4, "초등2"),
    ELEMENTARY_3(5, "초등3"),
    ELEMENTARY_4(6, "초등4"),
    ELEMENTARY_5(7, "초등5"),
    ELEMENTARY_6(8, "초등6"),
    MIDDLE_1(9, "중등1"),
    MIDDLE_2(10, "중등2"),
    MIDDLE_3(11, "중등3"),
    HIGH_1(12, "고등1"),
    HIGH_2(13, "고등2"),
    HIGH_3(14, "고등3");

    private final Integer order;
    private final String description;
}