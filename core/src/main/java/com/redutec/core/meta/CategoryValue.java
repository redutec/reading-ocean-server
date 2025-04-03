package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryValue {
    LBV001("EDUCATION", "교육자료"),
    LBV002("CONSULTING", "상담자료"),
    LBV003("PROMOTION", "홍보자료"),
    LBV004("ETC", "기타"),
    LBV005("READING_NOTE", "독서기록장"),
    LBV006("READING_LIST", "독서목록");

    private final String name;
    private final String description;
}