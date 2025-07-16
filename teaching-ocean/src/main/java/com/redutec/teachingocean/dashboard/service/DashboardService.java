package com.redutec.teachingocean.dashboard.service;

import com.redutec.core.dto.DashboardDto;

public interface DashboardService {
    /**
     * 현재 로그인한 교사가 속한 교육기관의 대시보드 조회
     */
    DashboardDto.TeachingOceanDashboardResponse getDashboard();
}