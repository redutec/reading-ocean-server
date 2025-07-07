package com.redutec.core.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class SampleData {
    // AdminApplication 서비스용
    @Getter
    @AllArgsConstructor
    public enum SampleAdminUser {
        // Master: 1개 (서울)
        MASTER_ADMIN(
                "redutec",
                "redutec@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.CEO,
                "레듀텍",
                AuthenticationStatus.ACTIVE,
                0
        ),
        DEVELOPMENT_TEAM_MANAGER(
                "devManager",
                "development.manager@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.DEVELOPMENT_TEAM_MANAGER,
                "개발팀장",
                AuthenticationStatus.ACTIVE,
                0
        ),
        DEVELOPMENT_TEAM_MEMBER(
                "devMember",
                "development.member@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.DEVELOPMENT_TEAM_MEMBER,
                "개발팀원",
                AuthenticationStatus.ACTIVE,
                0
        ),
        BUSINESS_TEAM_MANAGER(
                "businessManager",
                "business.manager@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.BUSINESS_TEAM_MANAGER,
                "사업팀장",
                AuthenticationStatus.ACTIVE,
                0
        ),
        BUSINESS_TEAM_MEMBER(
                "businessMember",
                "business.member@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.BUSINESS_TEAM_MEMBER,
                "사업팀원",
                AuthenticationStatus.ACTIVE,
                0
        ),
        RESEARCH_TEAM_MANAGER(
                "researchManager",
                "research.manager@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.RESEARCH_TEAM_MANAGER,
                "연구소장",
                AuthenticationStatus.ACTIVE,
                0
        ),
        RESEARCH_TEAM_MEMBER(
                "researchMember",
                "research.member@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.RESEARCH_TEAM_MEMBER,
                "연구원",
                AuthenticationStatus.ACTIVE,
                0
        ),
        MANAGEMENT_TEAM_MANAGER(
                "managementManager",
                "management.manager@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.MANAGEMENT_TEAM_MANAGER,
                "경영기획팀장",
                AuthenticationStatus.ACTIVE,
                0
        ),
        MANAGEMENT_TEAM_MEMBER(
                "managementMember",
                "management.member@redutec.co.kr",
                "Redutec123!",
                AdminUserRole.MANAGEMENT_TEAM_MEMBER,
                "경영기획팀원",
                AuthenticationStatus.ACTIVE,
                0
        );

        private final String accountId;
        private final String email;
        private final String password;
        private final AdminUserRole role;
        private final String nickname;
        private final AuthenticationStatus authenticationStatus;
        private final Integer failedLoginAttempts;
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

    // TeachingOceanApplication 서비스용
    @Getter
    @AllArgsConstructor
    public enum ParentTeachingOceanMenu {
        INSTITUTE(
                "교육기관 관리",
                "/institute",
                "교육기관의 교사, 학생 등을 관리",
                List.of(TeacherRole.CHIEF, TeacherRole.TEACHER)
        );

        private final String name;
        private final String url;
        private final String description;
        private final List<TeacherRole> accessibleRoles;
    }

    @Getter
    @AllArgsConstructor
    public enum ChildrenTeachingOceanMenu {
        TEACHER(
                "교사 관리",
                "/institute/teacher",
                "교육기관의 교사 관리",
                ParentTeachingOceanMenu.INSTITUTE,
                List.of(TeacherRole.CHIEF)
        ),
        STUDENT(
                "학생 관리",
                "/institute/student",
                "교육기관의 학생 관리",
                ParentTeachingOceanMenu.INSTITUTE,
                ParentTeachingOceanMenu.INSTITUTE.accessibleRoles
        );

        private final String name;
        private final String url;
        private final String description;
        private final ParentTeachingOceanMenu parentTeachingOceanMenu;
        private final List<TeacherRole> accessibleRoles;
    }

    @Getter
    @AllArgsConstructor
    public enum SampleBranch {
        SEONGNAM(
                "seongnam_chief",
                "경기",
                "성남지사",
                BranchStatus.ACTIVE,
                "성남시, 하남시, 광주시",
                "seongnam_contract_file.pdf",
                LocalDate.of(2025, 2, 3),
                LocalDate.of(2030, 2, 2),
                "샘플"
        ),
        SEOUL(
                "seoul_chief",
                "서울",
                "서울지사",
                BranchStatus.ACTIVE,
                "서울시",
                "seoul_contract_file.pdf",
                LocalDate.of(2025, 4, 1),
                null,
                "샘플"
        ),
        BUSAN(
                "busan_chief",
                "부산",
                "부산지사",
                BranchStatus.INACTIVE,
                "부산시, 김해시, 기장군",
                "busan_contract_file.pdf",
                LocalDate.of(2025, 3, 2),
                LocalDate.of(2026, 3, 1),
                "샘플"
        );

        private final String managerAccountId;
        private final String region;
        private final String name;
        private final BranchStatus status;
        private final String businessArea;
        private final String contractFileName;
        private final LocalDate contractDate;
        private final LocalDate renewalDate;
        private final String description;
    }

    @Getter
    @AllArgsConstructor
    public enum SampleInstitute {
        SEONGNAM(
                "리딩오션 성남지점",
                "리딩오션 성남지점",
                "주소",
                "12345",
                "77788889999",
                "www.redutec.co.kr",
                "www.redutec.co.kr",
                InstituteType.ACADEMY,
                InstituteManagementType.STANDARD,
                InstituteStatus.ACTIVE,
                InstituteOperationStatus.OPERATING,
                SampleBranch.SEONGNAM.getName()
        ),
        SEOUL(
                "리딩오션 서울지점",
                "리딩오션 서울지점",
                "주소ㅇㅇ",
                "54321",
                "66688889999",
                "www.redutec.co.kr",
                "www.redutec.co.kr",
                InstituteType.SCHOOL,
                InstituteManagementType.BASIC,
                InstituteStatus.WAIT,
                InstituteOperationStatus.PREPARING,
                SampleBranch.SEOUL.getName()
        ),
        BUSAN(
                "리딩오션 부산지점",
                "리딩오션 부산지점",
                "광안리",
                "71225",
                "55198764213",
                "www.redutec.co.kr",
                "www.redutec.co.kr",
                InstituteType.ACADEMY,
                InstituteManagementType.BASIC,
                InstituteStatus.ACTIVE,
                InstituteOperationStatus.OPERATING,
                SampleBranch.BUSAN.getName()
        );

        private final String name;
        private final String businessRegistrationName;
        private final String address;
        private final String postalCode;
        private final String phoneNumber;
        private final String url;
        private final String naverPlaceUrl;
        private final InstituteType type;
        private final InstituteManagementType managementType;
        private final InstituteStatus status;
        private final InstituteOperationStatus operationStatus;
        private final String branchName;
    }

    @Getter
    @AllArgsConstructor
    public enum SampleTeacher {
        // CHIEF: 3개
        SEONGNAM_CHIEF(
                "seongnam_chief",
                "Redutec123!",
                "성남지점 원장",
                "44455556666",
                "seongnam_chief@redutec.co.kr",
                TeacherStatus.ACTIVE,
                TeacherRole.CHIEF,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.SEONGNAM.getName()
        ),
        SEOUL_CHIEF(
                "seoul_chief",
                "Redutec123!",
                "서울지점 원장",
                "33355556666",
                "seoul_chief@redutec.co.kr",
                TeacherStatus.ACTIVE,
                TeacherRole.CHIEF,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.SEOUL.getName()
        ),
        BUSAN_CHIEF(
                "busan_chief",
                "Redutec123!",
                "부산지점 원장",
                "55155556666",
                "busan_chief@redutec.co.kr",
                TeacherStatus.ACTIVE,
                TeacherRole.CHIEF,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.BUSAN.getName()
        ),
        // TEACHER: 4개
        SEONGNAM_TEACHER_1(
                "seongnam_teacher_1",
                "Redutec123!",
                "성남지점 교사1",
                "44444446666",
                "seongnam_teacher_1@redutec.co.kr",
                TeacherStatus.ACTIVE,
                TeacherRole.TEACHER,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.SEONGNAM.getName()
        ),
        SEONGNAM_TEACHER_2(
                "seongnam_teacher_2",
                "Redutec123!",
                "성남지점 교사2",
                "44422226666",
                "seongnam_teacher_2@redutec.co.kr",
                TeacherStatus.INACTIVE,
                TeacherRole.TEACHER,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.SEONGNAM.getName()
        ),
        SEOUL_TEACHER_1(
                "seoul_teacher_1",
                "Redutec123!",
                "서울지점 교사1",
                "22233336666",
                "seoul_teacher_1@redutec.co.kr",
                TeacherStatus.ACTIVE,
                TeacherRole.TEACHER,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.SEOUL.getName()
        ),
        SEOUL_TEACHER_2(
                "seoul_teacher_2",
                "Redutec123!",
                "서울지점 교사2",
                "22211116666",
                "seoul_teacher_2@redutec.co.kr",
                TeacherStatus.INACTIVE,
                TeacherRole.TEACHER,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.SEOUL.getName()
        ),
        BUSAN_TEACHER_1(
                "busan_teacher_1",
                "Redutec123!",
                "부산지점 교사1",
                "55533336666",
                "busan_teacher_1@redutec.co.kr",
                TeacherStatus.ACTIVE,
                TeacherRole.TEACHER,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.BUSAN.getName()
        ),
        BUSAN_TEACHER_2(
                "busan_teacher_2",
                "Redutec123!",
                "부산지점 교사2",
                "55511116666",
                "busan_teacher_2@redutec.co.kr",
                TeacherStatus.INACTIVE,
                TeacherRole.TEACHER,
                AuthenticationStatus.ACTIVE,
                SampleInstitute.BUSAN.getName()
        );

        private final String accountId;
        private final String password;
        private final String name;
        private final String phoneNumber;
        private final String email;
        private final TeacherStatus status;
        private final TeacherRole role;
        private final AuthenticationStatus authenticationStatus;
        private final String instituteName;
    }

    @Getter
    @AllArgsConstructor
    public enum SampleStudent {
        // 성남지점: 2개
        SEONGNAM_STUDENT_1(
                "seongnam_student_1",
                "Redutec123!",
                "성남지점 학생1",
                "22244448888",
                "seongnam_student_1@redutec.co.kr",
                "20000101",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.A3,
                490,
                SchoolGrade.ELEMENTARY_3,
                "비고",
                SampleInstitute.SEONGNAM.getName(),
                Domain.READING_OCEAN_EDU
        ),
        SEONGNAM_STUDENT_2(
                "seongnam_student_2",
                "Redutec123!",
                "성남지점 학생2",
                "22255558888",
                "seongnam_student_2@redutec.co.kr",
                "20000101",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.B6,
                920,
                SchoolGrade.ELEMENTARY_6,
                "비고",
                SampleInstitute.SEONGNAM.getName(),
                Domain.READING_OCEAN_EDU
        ),
        // 서울지점: 2개
        SEOUL_STUDENT_1(
                "seoul_student_1",
                "Redutec123!",
                "서울지점 학생1",
                "33344445555",
                "seoul_student_1@redutec.co.kr",
                "20020202",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.A2,
                350,
                SchoolGrade.ELEMENTARY_2,
                "샘플임",
                SampleInstitute.SEOUL.getName(),
                Domain.READING_OCEAN_EDU
        ),
        SEOUL_STUDENT_2(
                "seoul_student_2",
                "Redutec123!",
                "서울지점 학생2",
                "33344445555",
                "seoul_student_2@redutec.co.kr",
                "20020202",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.B4,
                680,
                SchoolGrade.ELEMENTARY_4,
                "샘플임",
                SampleInstitute.SEOUL.getName(),
                Domain.READING_OCEAN_EDU
        ),
        // 부산지점: 2개
        BUSAN_STUDENT_1(
                "busan_student_1",
                "Redutec123!",
                "부산지점 학생1",
                "33344445555",
                "busan_student_1@redutec.co.kr",
                "20040404",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.A3,
                490,
                SchoolGrade.ELEMENTARY_3,
                "샘플임!!!",
                SampleInstitute.BUSAN.getName(),
                Domain.READING_OCEAN_EDU
        ),
        BUSAN_STUDENT_2(
                "busan_student_2",
                "Redutec123!",
                "부산지점 학생2",
                "33344447777",
                "busan_student_2@redutec.co.kr",
                "20010303",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.B7,
                1030,
                SchoolGrade.MIDDLE_1,
                "샘플임!!!!!",
                SampleInstitute.BUSAN.getName(),
                Domain.READING_OCEAN_EDU
        ),
        // 리딩오션 홈: 3개
        HOME_STUDENT_1(
                "home_student_1",
                "Redutec123!",
                "홈 학생1",
                "99922224444",
                "home_student_1@redutec.co.kr",
                "20050505",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.A5,
                730,
                SchoolGrade.ELEMENTARY_5,
                "홈 샘플임22",
                null,
                Domain.READING_OCEAN_HOME
        ),
        HOME_STUDENT_2(
                "home_student_2",
                "Redutec123!",
                "홈 학생2",
                "99933334444",
                "home_student_2@redutec.co.kr",
                "20020202",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.B5,
                920,
                SchoolGrade.ELEMENTARY_6,
                "샘플임",
                null,
                Domain.READING_OCEAN_HOME
        ),
        HOME_STUDENT_3(
                "home_student_3",
                "Redutec123!",
                "홈 학생3",
                "99933338888",
                "home_student_3@redutec.co.kr",
                "20020202",
                StudentStatus.ACTIVE,
                AuthenticationStatus.ACTIVE,
                ReadingLevel.B3,
                560,
                SchoolGrade.ELEMENTARY_3,
                "샘플임@@!",
                null,
                Domain.READING_OCEAN_HOME
        );

        private final String accountId;
        private final String password;
        private final String name;
        private final String phoneNumber;
        private final String email;
        private final String birthday;
        private final StudentStatus status;
        private final AuthenticationStatus authenticationStatus;
        private final ReadingLevel readingLevel;
        private final Integer raq;
        private final SchoolGrade schoolGrade;
        private final String description;
        private final String instituteName;
        private final Domain domain;
    }
}