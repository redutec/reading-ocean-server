package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LearningMaterialAuthor {
    BUSINESS_TEAM("사업팀"),
    CONTENT_RESEARCH_TEAM("컨텐츠연구팀"),
    OPERATION_TEAM("운영팀");

    private final String displayName;
}