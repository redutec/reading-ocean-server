package com.redutec.core.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class SampleData {
    // AdminApplication용
    @Getter
    @AllArgsConstructor
    public enum Administrator {
        // Master: 1개 (서울)
        MASTER_ADMIN(
            "redutec@redutec.co.kr",
                "Redutec123!",
                AdministratorRole.CEO,
                "redutec",
                AuthenticationStatus.ACTIVE,
                0
        );

        private final String email;
        private final String password;
        private final AdministratorRole role;
        private final String nickname;
        private final AuthenticationStatus authenticationStatus;
        private final int failedLoginAttempts;
    }

    @Getter
    @AllArgsConstructor
    public enum ParentAdminMenu {
        ADMIN(
                "시스템 관리",
                "/admin",
                "어드민 사용자, 메뉴 등을 관리",
                List.of(AdministratorRole.CEO, AdministratorRole.DEVELOPMENT_TEAM_MANAGER, AdministratorRole.BUSINESS_TEAM_MANAGER, AdministratorRole.RESEARCH_TEAM_MANAGER, AdministratorRole.MANAGEMENT_TEAM_MANAGER)
        );

        private final String name;
        private final String url;
        private final String description;
        private final List<AdministratorRole> accessibleRoles;
    }

    @Getter
    @AllArgsConstructor
    public enum ChildrenAdminMenu {
        ADMINISTRATOR(
                "어드민 사용자",
                "/admin/administrator",
                "어드민 사용자 목록",
                ParentAdminMenu.ADMIN,
                ParentAdminMenu.ADMIN.accessibleRoles
        );

        private final String name;
        private final String url;
        private final String description;
        private final ParentAdminMenu parentAdminMenu;
        private final List<AdministratorRole> accessibleRoles;
    }
}