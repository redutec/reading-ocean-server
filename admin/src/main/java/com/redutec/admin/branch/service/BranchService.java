package com.redutec.admin.branch.service;

import com.redutec.core.dto.BranchDto;

public interface BranchService {
    /**
     * 지사 등록
     * @param createBranchRequest 지사 등록 정보를 담은 DTO
     * @return 등록된 지사 정보
     */
    BranchDto.BranchResponse create(BranchDto.CreateBranchRequest createBranchRequest);

    /**
     * 조건에 맞는 지사 목록 조회
     * @param findBranchRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 지사 목록 및 페이징 정보
     */
    BranchDto.BranchPageResponse find(BranchDto.FindBranchRequest findBranchRequest);

    /**
     * 특정 지사 조회
     * @param branchId 지사 고유번호
     * @return 특정 지사 응답 객체
     */
    BranchDto.BranchResponse findById(Long branchId);

    /**
     * 특정 지사 수정
     * @param branchId 수정할 지사의 ID
     * @param updateBranchRequest 지사 수정 요청 객체
     */
    void update(Long branchId, BranchDto.UpdateBranchRequest updateBranchRequest);

    /**
     * 특정 지사 삭제
     * @param branchId 삭제할 지사의 ID
     */
    void delete(Long branchId);
}