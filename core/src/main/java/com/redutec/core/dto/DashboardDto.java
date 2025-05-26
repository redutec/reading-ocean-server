package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class DashboardDto {
    @Schema(description = "어드민 대시보드 응답 객체")
    public record AdminDashboardResponse(
            // 지사
            int totalBranches,
            int activeBranches,
            int inactiveBranches,
            // 교육기관
            int totalInstitutes,
            int activeInstitutes,
            int waitInstitutes,
            int nonPaymentInstitutes,
            int terminationInstitutes,
            // 교사
            int totalTeachers,
            int activeTeachers,
            int inactiveTeachers,
            int waitTeachers,
            // 학생
            int totalStudents,
            int activeStudents,
            int inactiveStudents,
            int waitStudents
    ) {}

    @Schema(description = "티칭오션 대시보드 응답 객체")
    public record TeachingOceanDashboardResponse(
    ) {}
}