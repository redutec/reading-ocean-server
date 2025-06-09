package com.redutec.teachingocean.institute.service;

import com.redutec.core.dto.InstituteDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
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
     * 마이페이지 - 교육기관 조회
     * @return 현재 로그인한 교사가 속한 교육기관 응답 객체
     */
    @Override
    public InstituteDto.InstituteResponse findOne() {
        var authenticatedTeacher = authenticationService.getAuthenticatedTeacher();
        Institute institute = instituteRepository.findById(authenticatedTeacher.instituteId())
                .orElseThrow(EntityNotFoundException::new);
        Teacher chiefTeacher = teacherRepository.findById(authenticatedTeacher.teacherId())
                .orElseThrow(EntityNotFoundException::new);
        return instituteMapper.toResponseDto(institute, chiefTeacher);
    }

    /**
     * 마이페이지 - 교육기관 수정
     * @param updateInstituteRequest 교육기관 수정 요청 객체
     */
    @Override
    public void update(InstituteDto.UpdateInstituteRequest updateInstituteRequest) {

    }

    /**
     * 특정 교육기관 엔티티 조회
     *
     * @param instituteId 교육기관 고유번호
     * @return 특정 교육기관 엔티티 객체
     */
    public Institute getInstitute(Long instituteId) {
        return instituteRepository.findById(instituteId)
                .orElseThrow(() -> new EntityNotFoundException("교육기관이 존재하지 않습니다. instituteId: " + instituteId));
    }
}