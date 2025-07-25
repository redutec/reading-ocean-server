package com.redutec.teachingocean.branch.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.teachingocean.branch.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branches")
@Tag(name = "지사 API", description = "지사 API 모음")
public class BranchController {
    private final ApiResponseManager apiResponseManager;
    private final BranchService branchService;

    @Operation(summary = "현재 로그인한 지사장(교사)의 지사 정보 조회", description = "현재 로그인한 지사장(교사)의 지사 정보 조회 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> findBranch() {
        return apiResponseManager.ok(branchService.findBranch());
    }

    @Operation(summary = "현재 로그인한 지사장(교사)의 지사에 속한 특정 교육기관 조회", description = "현재 로그인한 지사장(교사)의 지사에 속한 특정 교육기관 조회 API")
    @GetMapping("/institute/{instituteId}")
    public ResponseEntity<ApiResponseBody> findInstitute(@PathVariable Long instituteId) {
        return apiResponseManager.ok(branchService.findInstitute(instituteId));
    }

    @Operation(summary = "현재 로그인한 지사장(교사)의 지사에 속한 교육기관 조회", description = "현재 로그인한 지사장(교사)의 지사에 속한 교육기관 조회 API")
    @GetMapping("/institute")
    public ResponseEntity<ApiResponseBody> findInstitutes() {
        return apiResponseManager.ok(branchService.findInstitutes());
    }
}