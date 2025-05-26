package com.redutec.teachingocean.dashboard.service;

import com.redutec.core.dto.DashboardDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {
    /**
     * 현재 로그인한 교육기관의 대시보드 조회
     */
    @Override
    public DashboardDto.TeachingOceanDashboardResponse getDashboard() {
        return null;
    }
}