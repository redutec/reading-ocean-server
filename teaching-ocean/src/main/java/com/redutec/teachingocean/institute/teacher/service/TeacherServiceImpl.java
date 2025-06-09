package com.redutec.teachingocean.institute.teacher.service;

import com.redutec.core.criteria.TeacherCriteria;
import com.redutec.core.dto.TeacherDto;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.TeacherMapper;
import com.redutec.core.repository.HomeroomRepository;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.TeacherSpecification;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;
    private final HomeroomRepository homeroomRepository;
    private final InstituteRepository instituteRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 학사관리 - 교사관리 - 신규 교사 등록
     * @param createTeacherRequest 교사 등록 정보를 담은 DTO
     * @return 등록한 교사 응답 객체
     */
    @Override
    @Transactional
    public TeacherDto.TeacherResponse create(TeacherDto.CreateTeacherRequest createTeacherRequest) {
        // 현재 로그인한 교사 정보를 조회
        var authenticatedTeacher = authenticationService.getAuthenticatedTeacher();
        // 현재 로그인한 교사가 속한 교육기관 엔티티 조회
        Institute institute = instituteRepository.findById(authenticatedTeacher.instituteId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교육기관입니다. instituteId: " + authenticatedTeacher.instituteId()));
        // 등록 요청 객체에 학급 고유번호가 있다면 학급 엔티티 조회
        Homeroom homeroom = homeroomRepository.findById(authenticatedTeacher.homeroomId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학급입니다. homeroomId: " + authenticatedTeacher.homeroomId()));
        // 신규 교사 등록
        return teacherMapper.toResponseDto(teacherRepository.save(teacherMapper.toCreateEntity(
                createTeacherRequest,
                institute,
                homeroom
        )));
    }

    /**
     * 학사관리 - 교사관리 - 교사 목록 조회
     * @param findTeacherRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 교사 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherDto.TeacherPageResponse find(TeacherDto.FindTeacherRequest findTeacherRequest) {
        // TeacherCriteria 설정(교육기관명은 현재 로그인한 교사가 속한 교육기관의 이름으로 Set)
        TeacherCriteria teacherCriteria = new TeacherCriteria(
                findTeacherRequest.teacherIds(),
                findTeacherRequest.accountId(),
                findTeacherRequest.name(),
                authenticationService.getAuthenticatedTeacher().instituteName(),
                findTeacherRequest.statuses(),
                findTeacherRequest.roles()
        );
        // 조건에 맞는 교육기관 조회
        return teacherMapper.toPageResponseDto(teacherRepository.findAll(
                TeacherSpecification.findWith(teacherCriteria),
                (findTeacherRequest.page() != null && findTeacherRequest.size() != null)
                        ? PageRequest.of(findTeacherRequest.page(), findTeacherRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 학사관리 - 교사관리 - 특정 교사 조회
     * @param teacherId 교사 고유번호
     * @return 특정 교사 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherDto.TeacherResponse findById(Long teacherId) {
        return teacherMapper.toResponseDto(getTeacher(teacherId));
    }

    /**
     * 학사관리 - 교사관리 - 특정 교사 수정
     * @param teacherId 수정할 교사의 ID
     * @param updateTeacherRequest 교사 수정 요청 객체
     */
    @Override
    @Transactional
    public void update(Long teacherId, TeacherDto.UpdateTeacherRequest updateTeacherRequest) {
        // 수정할 교사 엔티티 조회
        Teacher teacher = getTeacher(teacherId);
        // 수정 요청 객체에 newPassword가 존재한다면 현재 비밀번호와 currentPassword가 일치하는지 검증
        Optional.ofNullable(updateTeacherRequest.newPassword())
                .filter(newPassword -> !newPassword.isBlank())
                .ifPresent(newPassword -> {
                    // currentPassword 미입력 시 예외
                    Optional.ofNullable(updateTeacherRequest.currentPassword())
                            .filter(currentPassword -> !currentPassword.isBlank())
                            .orElseThrow(() -> new IllegalArgumentException("비밀번호를 변경하려면 현재 비밀번호를 입력해야 합니다."));
                    // currentPassword 불일치 시 예외
                    Optional.of(updateTeacherRequest.currentPassword())
                            .filter(currentPassword -> passwordEncoder.matches(currentPassword, teacher.getPassword()))
                            .orElseThrow(() -> new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다."));
                });
        // 수정 요청 객체에 교육기관 고유번호가 있다면 교육기관 엔티티 조회(없으면 Null)
        Institute institute = Optional.ofNullable(updateTeacherRequest.instituteId())
                .flatMap(instituteRepository::findById)
                .orElseGet(teacher::getInstitute);
        // 수정 요청 객체에 학급 고유번호가 있다면 학급 엔티티 조회(없으면 Null)
        Homeroom homeroom = Optional.ofNullable(updateTeacherRequest.homeroomId())
                .flatMap(homeroomRepository::findById)
                .orElseGet(teacher::getHomeroom);
        // 교사 정보 수정 엔티티 빌드 후 UPDATE
        teacherRepository.save(teacherMapper.toUpdateEntity(
                teacher,
                updateTeacherRequest,
                institute,
                homeroom
        ));
    }

    /**
     * 특정 교사 엔티티 조회
     * @param teacherId 교사 고유번호
     * @return 특정 교사 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Teacher getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교사입니다. teacherId = " + teacherId));
    }
}