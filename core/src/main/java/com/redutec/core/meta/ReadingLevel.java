package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ReadingLevel {
    A0(0, 20, 90, List.of(SchoolGrade.CHILD)),
    B0(0, 100, 170, List.of(SchoolGrade.CHILD)),
    A1(1, 180, 260, List.of(SchoolGrade.CHILD, SchoolGrade.ELEMENTARY_1, SchoolGrade.ELEMENTARY_2)),
    B1(1, 270, 340, List.of(SchoolGrade.CHILD, SchoolGrade.ELEMENTARY_1, SchoolGrade.ELEMENTARY_2)),
    A2(2, 350, 410, List.of(SchoolGrade.ELEMENTARY_1, SchoolGrade.ELEMENTARY_2, SchoolGrade.ELEMENTARY_3)),
    B2(2, 420, 480, List.of(SchoolGrade.ELEMENTARY_1, SchoolGrade.ELEMENTARY_2, SchoolGrade.ELEMENTARY_3)),
    A3(3, 490, 550, List.of(SchoolGrade.ELEMENTARY_2, SchoolGrade.ELEMENTARY_3, SchoolGrade.ELEMENTARY_4)),
    B3(3, 560, 610, List.of(SchoolGrade.ELEMENTARY_2, SchoolGrade.ELEMENTARY_3, SchoolGrade.ELEMENTARY_4)),
    A4(4, 620, 670, List.of(SchoolGrade.ELEMENTARY_3, SchoolGrade.ELEMENTARY_4, SchoolGrade.ELEMENTARY_5)),
    B4(4, 680, 720, List.of(SchoolGrade.ELEMENTARY_3, SchoolGrade.ELEMENTARY_4, SchoolGrade.ELEMENTARY_5)),
    A5(5, 730, 790, List.of(SchoolGrade.ELEMENTARY_3, SchoolGrade.ELEMENTARY_4, SchoolGrade.ELEMENTARY_5, SchoolGrade.ELEMENTARY_6)),
    B5(5, 800, 850, List.of(SchoolGrade.ELEMENTARY_3, SchoolGrade.ELEMENTARY_4, SchoolGrade.ELEMENTARY_5, SchoolGrade.ELEMENTARY_6, SchoolGrade.MIDDLE_1, SchoolGrade.MIDDLE_2)),
    A6(6, 860, 910, List.of(SchoolGrade.ELEMENTARY_4, SchoolGrade.ELEMENTARY_5, SchoolGrade.ELEMENTARY_6, SchoolGrade.MIDDLE_1, SchoolGrade.MIDDLE_2)),
    B6(6, 920, 960, List.of(SchoolGrade.ELEMENTARY_4, SchoolGrade.ELEMENTARY_5, SchoolGrade.ELEMENTARY_6, SchoolGrade.MIDDLE_1, SchoolGrade.MIDDLE_2)),
    A7(7, 970, 1020, List.of(SchoolGrade.ELEMENTARY_5, SchoolGrade.ELEMENTARY_6, SchoolGrade.MIDDLE_1, SchoolGrade.MIDDLE_2, SchoolGrade.MIDDLE_3)),
    B7(7, 1030, 1080, List.of(SchoolGrade.ELEMENTARY_5, SchoolGrade.ELEMENTARY_6, SchoolGrade.MIDDLE_1, SchoolGrade.MIDDLE_2, SchoolGrade.MIDDLE_3)),
    A8(8, 1090, 1140, List.of(SchoolGrade.ELEMENTARY_6, SchoolGrade.MIDDLE_1, SchoolGrade.MIDDLE_2, SchoolGrade.MIDDLE_3, SchoolGrade.HIGH_1)),
    B8(8, 1150, 1200, List.of(SchoolGrade.MIDDLE_1, SchoolGrade.MIDDLE_2, SchoolGrade.MIDDLE_3, SchoolGrade.HIGH_1));

    private final Integer level;
    private final Integer minimumRaq;
    private final Integer maximumRaq;
    private final List<SchoolGrade> schoolGrades;
}