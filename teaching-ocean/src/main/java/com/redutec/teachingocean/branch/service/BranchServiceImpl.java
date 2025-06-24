package com.redutec.teachingocean.branch.service;

import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.dto.BranchDto;
import com.redutec.core.dto.InstituteDto;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.BranchMapper;
import com.redutec.core.mapper.InstituteMapper;
import com.redutec.core.meta.TeacherRole;
import com.redutec.core.repository.BranchRepository;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.InstituteSpecification;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchMapper branchMapper;
    private final BranchRepository branchRepository;
    private final AuthenticationService authenticationService;
    private final InstituteRepository instituteRepository;
    private final TeacherRepository teacherRepository;
    private final InstituteMapper instituteMapper;
    private final FileUtil fileUtil;

    /**
     * 현재 로그인한 지사장(교사)의 지사 정보 조회
     *
     * @return 특정 지사 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BranchDto.BranchResponse findBranch() {
        return branchMapper.toResponseDto(getAuthenticatedBranch());
    }

    /**
     * 현재 로그인한 지사장(교사)의 지사에 속한 특정 교육기관 조회
     *
     * @param instituteId 조회할 교육기관 고유번호
     * @return 현재 로그인한 지사장(교사)의 지사에 속한 특정 교육기관 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteDto.InstituteResponse findInstitute(Long instituteId) {
        Branch branch = getAuthenticatedBranch();
        Institute institute = instituteRepository.findByIdAndBranch(instituteId, branch)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교육기관입니다. instituteId = " + instituteId));
        return instituteMapper.toResponseDto(institute, branch.getManagerTeacher());
    }

    /**
     * 현재 로그인한 지사장(교사)의 지사에 속한 교육기관 조회
     *
     * @return 현재 로그인한 지사장(교사)의 지사에 속한 교육기관 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteDto.InstitutePageResponse findInstitutes() {
        InstituteDto.FindInstituteRequest findInstituteRequest = new InstituteDto.FindInstituteRequest(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(getAuthenticatedBranch().getId()),
                0,
                30
        );
        Page<Institute> page = instituteRepository.findAll(
                InstituteSpecification.findWith(instituteMapper.toCriteria(findInstituteRequest)),
                (findInstituteRequest.page() != null && findInstituteRequest.size() != null)
                        ? PageRequest.of(findInstituteRequest.page(), findInstituteRequest.size())
                        : Pageable.unpaged()
        );
        List<InstituteDto.InstituteResponse> instituteResponses = page.getContent().stream()
                .map(institute -> {
                    // Chief 교사 조회
                    Teacher chiefTeacher = teacherRepository.findByInstituteAndRole(institute, TeacherRole.CHIEF)
                            .orElse(null);
                    return instituteMapper.toResponseDto(institute, chiefTeacher);
                })
                .collect(Collectors.toList());
        return new InstituteDto.InstitutePageResponse(instituteResponses, page.getTotalElements(), page.getTotalPages());
    }

    /**
     * 현재 로그인한 지사장(교사)의 지사 정보 수정
     *
     * @param updateBranchRequest 지사 수정 요청 객체
     */
    @Override
    @Transactional
    public void update(BranchDto.UpdateBranchRequest updateBranchRequest) {
        // 현재 로그인한 지사장(교사)의 지사 엔티티 조회(수정할 엔티티 조회)
        Branch branch = getAuthenticatedBranch();
        // 업로드할 계약서 파일이 있는 경우 업로드하고 파일명을 생성
        String contractFileName = Optional.ofNullable(updateBranchRequest.contractFile())
                .filter(contractFile -> !contractFile.isEmpty())
                .map(contractFile -> {
                    FileUploadResult result = fileUtil.uploadFile(contractFile, "/branch");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElseGet(branch::getContractFileName);
        // 지사 정보 UPDATE
        branchMapper.updateEntity(
                branch,
                updateBranchRequest,
                branch.getManagerTeacher(),
                contractFileName
        );
    }

    /**
     * 현재 로그인한 지사장(교사)가 속한 지사 엔티티 조회
     * @return 현재 로그인한 지사장(교사)가 속한 지사 엔티티
     */
    @Transactional(readOnly = true)
    protected Branch getAuthenticatedBranch() {
        // 현재 로그인한 지사장(교사) 정보 조회
        Long managerTeacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        // 해당 지사장(교사)가 소속된 지사의 엔티티 조회
        return branchRepository.findByManagerTeacherId(managerTeacherId)
                .orElseThrow(() -> new EntityNotFoundException("지사 정보를 찾을 수 없습니다. managerTeacherId = " + managerTeacherId));
    }
}