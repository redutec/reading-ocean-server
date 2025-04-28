package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookSubGenre {
    // 문학
    DOMESTIC_PICTURE_BOOK("국내그림책",         BookGenre.LITERATURE),
    FOREIGN_PICTURE_BOOK("외국그림책",          BookGenre.LITERATURE),
    DOMESTIC_CREATIVE_FAIRYTALE("국내창작동화", BookGenre.LITERATURE),
    FOREIGN_CREATIVE_FAIRYTALE("외국창작동화",   BookGenre.LITERATURE),
    DOMESTIC_JUVENILE_LITERATURE("국내청소년문학", BookGenre.LITERATURE),
    FOREIGN_JUVENILE_LITERATURE("외국청소년문학",  BookGenre.LITERATURE),
    CLASSIC_MASTERPIECES("고전/명작",           BookGenre.LITERATURE),
    POEM("시",                                  BookGenre.LITERATURE),
    ESSAY("에세이",                              BookGenre.LITERATURE),
    DRAMA_AND_SCREENPLAY("희곡/시나리오",      BookGenre.LITERATURE),
    GRAPHIC_NOVEL("그래픽노블",                 BookGenre.LITERATURE),
    // 과학
    ENGINEERING("공학",                        BookGenre.SCIENCE),
    PHYSICS("물리",                            BookGenre.SCIENCE),
    BIOLOGY("생물",                            BookGenre.SCIENCE),
    MATH("수학",                              BookGenre.SCIENCE),
    MEDICINE("의학",                          BookGenre.SCIENCE),
    GENERAL_SCIENCE("일반과학",                BookGenre.SCIENCE),
    ASTRONOMY_EARTH_SCIENCE("천문/지구과학",    BookGenre.SCIENCE),
    CHEMISTRY("화학",                         BookGenre.SCIENCE),
    ENVIRONMENT("환경",                       BookGenre.SCIENCE),
    // 사회과학
    ECONOMICS_AND_MANAGEMENT("경제/경영",       BookGenre.SOCIAL_SCIENCES),
    LAW("법",                                 BookGenre.SOCIAL_SCIENCES),
    POLITICS("정치",                          BookGenre.SOCIAL_SCIENCES),
    GEOGRAPHY("지리",                         BookGenre.SOCIAL_SCIENCES),
    SOCIAL_ISSUES("사회문제",                 BookGenre.SOCIAL_SCIENCES),
    SOCIAL_CULTURE("사회문화",                BookGenre.SOCIAL_SCIENCES),
    GENERAL_SOCIAL_SCIENCES("일반사회과학",    BookGenre.SOCIAL_SCIENCES),
    FOLK_CUSTOMS("풍속/민속",                  BookGenre.SOCIAL_SCIENCES),
    // 역사
    KOREAN_HISTORY("국사",                     BookGenre.HISTORY),
    WORLD_HISTORY("세계사",                   BookGenre.HISTORY),
    CULTURAL_TRAVEL("문화/기행",              BookGenre.HISTORY),
    // 예체능
    ART("미술",                               BookGenre.ARTS_AND_SPORTS),
    PERFORMANCE("연극/공연",                 BookGenre.ARTS_AND_SPORTS),
    MUSIC("음악",                             BookGenre.ARTS_AND_SPORTS),
    SPORTS("체육",                            BookGenre.ARTS_AND_SPORTS),
    // 인물
    DOMESTIC_CHARACTER("국내인물",            BookGenre.BIOGRAPHY),
    FOREIGN_CHARACTER("해외인물",             BookGenre.BIOGRAPHY),
    // 교양
    GENERAL_KNOWLEDGE_AND_SELF_DEVELOPMENT("상식/자기계발", BookGenre.GENERAL_KNOWLEDGE),
    LANGUAGE_LEARNING("언어/학습",             BookGenre.GENERAL_KNOWLEDGE),
    PHILOSOPHY_AND_RELIGION("철학/종교",      BookGenre.GENERAL_KNOWLEDGE);

    private final String displayName;
    private final BookGenre genre;
}