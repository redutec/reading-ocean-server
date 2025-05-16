package com.redutec.teachingocean.branch.service;

import com.redutec.core.entity.Branch;
import com.redutec.core.dto.BranchDto;

public interface BranchService {
    /**
     * 현재 로그인한 지사장(교사)의 지사 정보 조회
     * @return 특정 지사 응답 객체
     */
    BranchDto.BranchResponse findBranch();

    /**
     * 특정 지사 엔티티 조회
     * @param branchId 지사 고유번호
     * @return 특정 지사 엔티티 객체
     */
    Branch getBranch(Long branchId);
}