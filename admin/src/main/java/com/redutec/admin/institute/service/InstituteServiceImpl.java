package com.redutec.admin.institute.service;

import com.redutec.core.dto.InstituteDto;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.InstituteMapper;
import com.redutec.core.meta.TeacherRole;
import com.redutec.core.repository.BranchRepository;
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
    private final BranchRepository branchRepository;
    private final TeacherRepository teacherRepository;

    /**
     * 교육기관 등록
     * @param createInstituteRequest 교육기관 등록 정보를 담은 DTO
     * @return 등록한 교육기관 정보
     */
    @Override
    @Transactional
    public InstituteDto.InstituteResponse create(InstituteDto.CreateInstituteRequest createInstituteRequest) {
        // 등록 요청 객체에 지사 고유번호가 존재한다면 지사 엔티티를 조회
        Branch branch = Optional.ofNullable(createInstituteRequest.branchId())
                .map(branchId -> branchRepository.findById(branchId)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지사입니다. branchId = " + branchId)))
                .orElse(null);
        // 교육기관 등록
        return instituteMapper.toResponseDto(
                instituteRepository.save(instituteMapper.toCreateEntity(createInstituteRequest, branch)),
                null
        );
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
                    return instituteMapper.toResponseDto(institute, chiefTeacher);
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
        // 교육기관 엔티티 조회
        Institute institute = getInstitute(instituteId);
        // 교육기관의 원장 교사 엔티티 조회
        Teacher chiefTeacher = teacherRepository.findByInstituteAndRole(institute, TeacherRole.CHIEF).orElse(null);
        // 응답객체에 담아 리턴
        return instituteMapper.toResponseDto(institute, chiefTeacher);
    }

    /**
     * 특정 교육기관 수정
     * @param instituteId 수정할 교육기관의 ID
     * @param updateInstituteRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long instituteId, InstituteDto.UpdateInstituteRequest updateInstituteRequest) {
        // 수정 요청 객체에 지사 고유번호가 존재하면 지사 엔티티를 조회
        Branch branch = Optional.ofNullable(updateInstituteRequest.branchId())
                .map(branchId -> branchRepository.findById(branchId)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지사입니다. branchId = " + branchId)))
                .orElse(null);
        // 교육기관 수정
        instituteRepository.save(instituteMapper.toUpdateEntity(
                getInstitute(instituteId),
                updateInstituteRequest,
                branch
        ));
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

    /**
     * 특정 교육기관 엔티티 조회
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Institute getInstitute(Long instituteId) {
        return instituteRepository.findById(instituteId)
                .orElseThrow(() -> new EntityNotFoundException("교육기관이 존재하지 않습니다. instituteId = " + instituteId));
    }
}