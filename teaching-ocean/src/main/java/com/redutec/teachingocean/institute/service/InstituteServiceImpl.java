package com.redutec.teachingocean.institute.service;

import com.redutec.core.dto.InstituteDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.mapper.InstituteMapper;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class InstituteServiceImpl implements InstituteService {
    private final InstituteRepository instituteRepository;
    private final InstituteMapper instituteMapper;
    private final AuthenticationService authenticationService;
    private final TeacherRepository teacherRepository;

    /**
     * 현재 로그인한 교사가 속한 교육기관 조회
     *
     * @return 특정 교육기관 응답 객체
     */
    @Override
    public InstituteDto.InstituteResponse findInstitute() {
        var authenticatedTeacher = authenticationService.getAuthenticatedTeacher();
        return instituteMapper.toResponseDto(
                instituteRepository.findById(authenticatedTeacher.instituteId()).orElse(null),
                teacherRepository.findById(authenticatedTeacher.chiefTeacherId()).orElse(null)
        );
    }

    /**
     * 특정 교육기관 엔티티 조회
     *
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 엔티티 객체
     */
    @Override
    public Institute getInstitute(Long instituteId) {
        return instituteRepository.findById(instituteId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교육기관입니다. instituteId = " + instituteId));
    }

    /**
     * 현재 로그인한 교사가 속한 교육기관 수정
     *
     * @param updateInstituteRequest 교육기관 수정 요청 객체
     */
    @Override
    public void update(InstituteDto.UpdateInstituteRequest updateInstituteRequest) {

    }
}