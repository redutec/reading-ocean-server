package com.redutec.admin.dashboard.service;

import com.redutec.core.dto.DashboardDto;

public interface DashboardService {
    /**
     * 어드민 대시보드 조회
     * @return 어드민 대시보드 정보 조회(지사, 교육기관, 교사, 학생 상태별 조회)
     */
    DashboardDto.AdminDashboardResponse getDashboard();
}