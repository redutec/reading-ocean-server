package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryValue {
    EDUCATION("LBV001", "교육자료"),
    CONSULTING("LBV002", "상담자료"),
    PROMOTION("LBV003", "홍보자료"),
    ETC("LBV004", "기타"),
    READING_NOTE("LBV005", "독서기록장"),
    READING_LIST("LBV006", "독서목록");

    private final String code;
    private final String description;
}