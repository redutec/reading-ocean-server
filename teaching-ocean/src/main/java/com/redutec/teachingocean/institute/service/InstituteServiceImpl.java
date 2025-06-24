package com.redutec.teachingocean.institute.service;

import com.redutec.core.dto.InstituteDto;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.InstituteMapper;
import com.redutec.core.repository.BranchRepository;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class InstituteServiceImpl implements InstituteService {
    private final InstituteRepository instituteRepository;
    private final InstituteMapper instituteMapper;
    private final AuthenticationService authenticationService;
    private final TeacherRepository teacherRepository;
    private final BranchRepository branchRepository;

    /**
     * 마이페이지 - 교육기관 조회
     * @return 현재 로그인한 교사가 속한 교육기관 응답 객체
     */
    @Override
    @Transactional
    public InstituteDto.InstituteResponse findInstitute() {
        // 현재 로그인한 교사 정보 조회
        var authenticatedTeacher = authenticationService.getAuthenticatedTeacher();
        // 현재 로그인한 교사가 속한 교육기관 엔티티를 조회
        Institute institute = instituteRepository.findById(authenticatedTeacher.instituteId())
                .orElseThrow(() -> new EntityNotFoundException("교육기관이 존재하지 않습니다. instituteId: " + authenticatedTeacher.instituteId()));
        // 현재 로그인한 교사가 속한 교육기관의 원장 교사 엔티티를 조회
        Teacher chiefTeacher = teacherRepository.findById(authenticatedTeacher.chiefTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("학원장이 존재하지 않습니다. chiefTeacherId: " + authenticatedTeacher.chiefTeacherId()));
        // 조회한 교육기관 응답 객체 리턴
        return instituteMapper.toResponseDto(institute, chiefTeacher);
    }

    /**
     * 마이페이지 - 교육기관 수정
     * @param updateInstituteRequest 교육기관 수정 요청 객체
     */
    @Override
    @Transactional
    public void update(InstituteDto.UpdateInstituteRequest updateInstituteRequest) {
        // 현재 로그인한 교사가 속한 교육기관 엔티티 조회(수정할 엔티티 조회)
        var authenticatedTeacher = authenticationService.getAuthenticatedTeacher();
        // 현재 로그인한 교사가 속한 교육기관 엔티티를 조회
        Institute institute = instituteRepository.findById(authenticatedTeacher.instituteId())
                .orElseThrow(() -> new EntityNotFoundException("교육기관이 존재하지 않습니다. instituteId: " + authenticatedTeacher.instituteId()));
        // 수정 요청 객체에 지사 고유번호가 존재하면 지사 엔티티를 조회
        Branch branch = Optional.ofNullable(updateInstituteRequest.branchId())
                .map(branchId -> branchRepository.findById(branchId)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지사입니다. branchId: " + branchId)))
                .orElseGet(institute::getBranch);
        // 교육기관 수정
        instituteMapper.updateEntity(institute, updateInstituteRequest, branch);
    }
}