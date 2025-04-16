package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdministratorRole {
    CEO("대표"),
    DEVELOPMENT_TEAM_MANAGER("개발팀 책임"),
    DEVELOPMENT_TEAM_MEMBER("개발팀원"),
    BUSINESS_TEAM_MANAGER("사업팀 책임"),
    BUSINESS_TEAM_MEMBER("사업팀원"),
    RESEARCH_TEAM_MANAGER("연구팀 책임"),
    RESEARCH_TEAM_MEMBER("연구팀원"),
    MANAGEMENT_TEAM_MANAGER("경영기획팀 책임"),
    MANAGEMENT_TEAM_MEMBER("경영기획팀원");

    private final String group;
}