package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurriculumStatus {
    PENDING("시작 전"),
    IN_PROGRESS("진행 중"),
    COMPLETED("완료"),
    CANCELLED("취소/보류");

    private final String displayName;
}