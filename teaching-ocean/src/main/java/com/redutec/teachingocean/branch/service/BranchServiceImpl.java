package com.redutec.teachingocean.branch.service;

import com.redutec.core.entity.Branch;
import com.redutec.core.repository.BranchRepository;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import com.redutec.core.dto.BranchDto;
import com.redutec.core.mapper.BranchMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;
    private final AuthenticationService authenticationService;

    /**
     * 현재 로그인한 지사장(교사)의 지사 정보 조회
     *
     * @return 특정 지사 응답 객체
     */
    @Override
    public BranchDto.BranchResponse findBranch() {
        // 현재 로그인한 지사장(교사) 정보 조회
        Long managerTeacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        // 해당 지사장(교사)가 소속된 지사의 엔티티 조회
        Branch branch = branchRepository.findByManagerTeacherId(managerTeacherId)
                .orElseThrow(() -> new EntityNotFoundException("지사 정보를 찾을 수 없습니다. managerTeacherId = " + managerTeacherId));
        // 응답 객체로 변환하여 리턴
        return branchMapper.toResponseDto(branch);
    }

    /**
     * 특정 지사 엔티티 조회
     *
     * @param branchId 지사 고유번호
     * @return 특정 지사 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Branch getBranch(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("지사를 찾을 수 없습니다. branchId = " + branchId));
    }
}