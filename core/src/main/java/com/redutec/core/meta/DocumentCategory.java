package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentCategory {
    READING_LIST("독서목록"),
    EDUCATIONAL_MATERIAL("교육자료"),
    PROMOTIONAL_MATERIAL("홍보자료"),
    COUNSELING_MATERIAL("상담자료"),
    ETC("기타");

    private final String displayName;
}