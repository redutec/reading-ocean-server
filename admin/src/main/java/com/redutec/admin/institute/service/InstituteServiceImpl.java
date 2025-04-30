package com.redutec.admin.institute.service;

import com.redutec.admin.branch.service.BranchService;
import com.redutec.admin.institute.dto.InstituteDto;
import com.redutec.admin.institute.mapper.InstituteMapper;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.meta.TeacherRole;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.InstituteSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class InstituteServiceImpl implements InstituteService {
    private final InstituteMapper instituteMapper;
    private final InstituteRepository instituteRepository;
    private final BranchService branchService;
    private final TeacherRepository teacherRepository;

    /**
     * 교육기관 등록
     * @param createInstituteRequest 교육기관 등록 정보를 담은 DTO
     * @return 등록된 교육기관 정보
     */
    @Override
    @Transactional
    public InstituteDto.InstituteResponse create(InstituteDto.CreateInstituteRequest createInstituteRequest) {
        // 교육기관 등록
        Institute institute = instituteRepository.save(
                instituteMapper.toEntity(
                        createInstituteRequest,
                        Optional.ofNullable(createInstituteRequest.branchId())
                                .map(branchService::getBranch)
                                .orElse(null)
                )
        );
        // 지사가 존재하면 조회, 없으면 null
        Branch branch = Optional.ofNullable(institute.getBranch())
                .map(Branch::getId)
                .map(branchService::getBranch)
                .orElse(null);
        // 응답 객체에 담아 리턴
        return instituteMapper.toResponseDto(institute, null, branch);
    }

    /**
     * 조건에 맞는 교육기관 목록 조회
     * @param findInstituteRequest 조회 조건을 담은 DTO
     * @return 조회된 교육기관 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteDto.InstitutePageResponse find(InstituteDto.FindInstituteRequest findInstituteRequest) {
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
                    // Branch가 있을 때만 조회, 없으면 null
                    Branch branch = Optional.ofNullable(institute.getBranch())
                            .map(Branch::getId)
                            .map(branchService::getBranch)
                            .orElse(null);
                    return instituteMapper.toResponseDto(institute, chiefTeacher, branch);
                })
                .collect(Collectors.toList());
        return new InstituteDto.InstitutePageResponse(instituteResponses, page.getTotalElements(), page.getTotalPages());
    }

    /**
     * 특정 교육기관 조회
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteDto.InstituteResponse findById(Long instituteId) {
        // 교육기관과 원장 교사, 지사 조회
        Institute institute = getInstitute(instituteId);
        Teacher chiefTeacher = teacherRepository.findByInstituteAndRole(institute, TeacherRole.CHIEF)
                .orElse(null);
        Branch branch = Optional.ofNullable(institute.getBranch())
                .map(b -> branchService.getBranch(b.getId()))
                .orElse(null);
        return instituteMapper.toResponseDto(
                institute,
                chiefTeacher,
                branch);
    }

    /**
     * 특정 교육기관 엔티티 조회
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Institute getInstitute(Long instituteId) {
        return instituteRepository.findById(instituteId)
                .orElseThrow(() -> new EntityNotFoundException("교육기관이 존재하지 않습니다. id = " + instituteId));
    }

    /**
     * 특정 교육기관 수정
     * @param instituteId 수정할 교육기관의 ID
     * @param updateInstituteRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long instituteId, InstituteDto.UpdateInstituteRequest updateInstituteRequest) {
        // 수정할 교육기관 엔티티 조회
        Institute institute = getInstitute(instituteId);
        // UPDATE 도메인 메서드로 변환
        institute.updateInstitute(
                updateInstituteRequest.name(),
                updateInstituteRequest.businessRegistrationName(),
                updateInstituteRequest.address(),
                updateInstituteRequest.zipCode(),
                updateInstituteRequest.phoneNumber(),
                updateInstituteRequest.url(),
                updateInstituteRequest.naverPlaceUrl(),
                updateInstituteRequest.type(),
                updateInstituteRequest.managementType(),
                updateInstituteRequest.status(),
                updateInstituteRequest.operationStatus(),
                Optional.ofNullable(updateInstituteRequest.branchId())
                        .map(branchService::getBranch)
                        .orElse(null)
        );
        // 교육기관 엔티티 UPDATE
        instituteRepository.save(institute);
    }

    /**
     * 특정 교육기관 삭제
     * @param instituteId 삭제할 교육기관의 ID
     */
    @Override
    @Transactional
    public void delete(Long instituteId) {
        instituteRepository.delete(getInstitute(instituteId));
    }
}