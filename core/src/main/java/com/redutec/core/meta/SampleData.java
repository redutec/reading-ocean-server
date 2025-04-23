package com.redutec.core.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class SampleData {
    // AdminApplication용
    @Getter
    @AllArgsConstructor
    public enum AdminUser {
        // Master: 1개 (서울)
        MASTER_ADMIN(
                "redutec@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.CEO,
                "redutec",
                AuthenticationStatus.ACTIVE,
                0
        ),
        DEVELOPMENT_TEAM_MANAGER(
                "development.manager@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.DEVELOPMENT_TEAM_MANAGER,
                "devManager",
                AuthenticationStatus.ACTIVE,
                0
        ),
        DEVELOPMENT_TEAM_MEMBER(
                "development.member@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.DEVELOPMENT_TEAM_MEMBER,
                "devMember",
                AuthenticationStatus.ACTIVE,
                0
        ),
        BUSINESS_TEAM_MANAGER(
                "business.manager@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.BUSINESS_TEAM_MANAGER,
                "businessManager",
                AuthenticationStatus.ACTIVE,
                0
        ),
        BUSINESS_TEAM_MEMBER(
                "business.member@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.BUSINESS_TEAM_MEMBER,
                "businessMember",
                AuthenticationStatus.ACTIVE,
                0
        ),
        RESEARCH_TEAM_MANAGER(
                "research.manager@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.RESEARCH_TEAM_MANAGER,
                "researchManager",
                AuthenticationStatus.ACTIVE,
                0
        ),
        RESEARCH_TEAM_MEMBER(
                "research.member@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.RESEARCH_TEAM_MEMBER,
                "researchMember",
                AuthenticationStatus.ACTIVE,
                0
        ),
        MANAGEMENT_TEAM_MANAGER(
                "management.manager@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.MANAGEMENT_TEAM_MANAGER,
                "managementManager",
                AuthenticationStatus.ACTIVE,
                0
        ),
        MANAGEMENT_TEAM_MEMBER(
                "management.member@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.MANAGEMENT_TEAM_MEMBER,
                "managementMember",
                AuthenticationStatus.ACTIVE,
                0
        );

        private final String email;
        private final String password;
        private final AdminUserRole role;
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
                List.of(AdminUserRole.CEO, AdminUserRole.DEVELOPMENT_TEAM_MANAGER, AdminUserRole.BUSINESS_TEAM_MANAGER, AdminUserRole.RESEARCH_TEAM_MANAGER, AdminUserRole.MANAGEMENT_TEAM_MANAGER)
        );

        private final String name;
        private final String url;
        private final String description;
        private final List<AdminUserRole> accessibleRoles;
    }

    @Getter
    @AllArgsConstructor
    public enum ChildrenAdminMenu {
        ADMIN_USER(
                "어드민 사용자",
                "/admin/user",
                "어드민 사용자 목록",
                ParentAdminMenu.ADMIN,
                ParentAdminMenu.ADMIN.accessibleRoles
        );

        private final String name;
        private final String url;
        private final String description;
        private final ParentAdminMenu parentAdminMenu;
        private final List<AdminUserRole> accessibleRoles;
    }
}