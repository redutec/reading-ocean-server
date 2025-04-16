package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Domain {
    READING_OCEAN_EDU("리딩오션 에듀"),
    READING_OCEAN_SCHOOL("리딩오션 스쿨"),
    READING_OCEAN_HOME("리딩오션 홈"),
    TEACHING_OCEAN("티칭오션 에듀"),
    TEACHING_OCEAN_SCHOOL("티칭오션 스쿨"),
    ADMIN("어드민");

    private final String name;
}