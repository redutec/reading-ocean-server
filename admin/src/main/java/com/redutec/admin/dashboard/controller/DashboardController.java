package com.redutec.admin.dashboard.controller;

import com.redutec.admin.dashboard.service.DashboardService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Tag(name = "대시보드 API", description = "대시보드 API 모음")
public class DashboardController {
    private final ApiResponseManager apiResponseManager;
    private final DashboardService dashboardService;

    @Operation(summary = "대시보드", description = "지사, 교육기관, 교사, 학생 상태별 조회 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> getDashboard() {
        return apiResponseManager.ok(dashboardService.getDashboard());
    }
}