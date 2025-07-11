package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.redutec.core.meta.Domain.*;

@Getter
@RequiredArgsConstructor
public enum InquiryCategory {
    EDUCATION( "교육자료", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, TEACHING_OCEAN, READING_OCEAN_SCHOOL, TEACHING_OCEAN_SCHOOL)),
    CONSULTING( "상담자료", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, TEACHING_OCEAN, READING_OCEAN_SCHOOL, TEACHING_OCEAN_SCHOOL)),
    PROMOTION( "홍보자료", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, TEACHING_OCEAN, READING_OCEAN_SCHOOL, TEACHING_OCEAN_SCHOOL)),
    READING_NOTE( "독서기록장", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, TEACHING_OCEAN, READING_OCEAN_SCHOOL, TEACHING_OCEAN_SCHOOL)),
    READING_LIST( "독서목록", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, TEACHING_OCEAN, READING_OCEAN_SCHOOL, TEACHING_OCEAN_SCHOOL)),
    USER_INFO( "회원정보", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, TEACHING_OCEAN, READING_OCEAN_SCHOOL, TEACHING_OCEAN_SCHOOL)),
    DIAGNOSIS( "독서진단", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, READING_OCEAN_SCHOOL)),
    RECOMMENDED_BOOK( "추천도서", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, READING_OCEAN_SCHOOL)),
    AFTER_READING( "독후활동", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, READING_OCEAN_SCHOOL)),
    LIBRARY( "나의 서재", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, READING_OCEAN_SCHOOL)),
    COMMUNITY( "커뮤니티", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, READING_OCEAN_SCHOOL)),
    ETC( "기타", List.of(READING_OCEAN_EDU, READING_OCEAN_HOME, TEACHING_OCEAN, READING_OCEAN_SCHOOL, TEACHING_OCEAN_SCHOOL)),
    ACADEMIC_MANAGEMENT( "학사관리", List.of(TEACHING_OCEAN, TEACHING_OCEAN_SCHOOL)),
    CLASS_MANAGEMENT( "학급관리", List.of(TEACHING_OCEAN, TEACHING_OCEAN_SCHOOL)),
    READING_OCEAN_MALL( "리딩오션몰", List.of(TEACHING_OCEAN, TEACHING_OCEAN_SCHOOL)),
    PAYMENT( "이용료 결제", List.of(TEACHING_OCEAN, TEACHING_OCEAN_SCHOOL));

    private final String displayName;
    private final List<Domain> allowedDomainList;
}