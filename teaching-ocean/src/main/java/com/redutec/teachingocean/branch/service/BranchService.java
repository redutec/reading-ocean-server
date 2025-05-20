package com.redutec.teachingocean.branch.service;

import com.redutec.core.dto.BranchDto;
import com.redutec.core.dto.InstituteDto;

public interface BranchService {
    /**
     * 현재 로그인한 지사장(교사)의 지사 정보 조회
     * @return 현재 로그인한 지사장(교사)의 지사 응답 객체
     */
    BranchDto.BranchResponse findBranch();

    /**
     * 현재 로그인한 지사장(교사)의 지사에 속한 특정 교육기관 조회
     * @param instituteId 조회할 교육기관 고유번호
     * @return 현재 로그인한 지사장(교사)의 지사에 속한 특정 교육기관 응답 객체
     */
    InstituteDto.InstituteResponse findInstitute(Long instituteId);

    /**
     * 현재 로그인한 지사장(교사)의 지사에 속한 교육기관 조회
     * @return 현재 로그인한 지사장(교사)의 지사에 속한 교육기관 응답 객체
     */
    InstituteDto.InstitutePageResponse findInstitutes();
}