package com.redutec.admin.teacher.service;

import com.redutec.admin.homeroom.service.HomeroomService;
import com.redutec.admin.institute.service.InstituteService;
import com.redutec.admin.teacher.dto.TeacherDto;
import com.redutec.admin.teacher.mapper.TeacherMapper;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.TeacherSpecification;
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
    private final PasswordEncoder passwordEncoder;
    private final InstituteService instituteService;
    private final HomeroomService homeroomService;

    /**
     * 교사 등록
     * @param createTeacherRequest 교사 등록 정보를 담은 DTO
     * @return 등록된 교사 정보
     */
    @Override
    @Transactional
    public TeacherDto.TeacherResponse create(
            TeacherDto.CreateTeacherRequest createTeacherRequest
    ) {
        return teacherMapper.toResponseDto(
                teacherRepository.save(
                        teacherMapper.toEntity(
                                createTeacherRequest,
                                instituteService.getInstitute(createTeacherRequest.instituteId()),
                                Optional.ofNullable(createTeacherRequest.homeroomId())
                                        .map(homeroomService::getHomeroom)
                                        .orElse(null)
                        )
                )
        );
    }

    /**
     * 조건에 맞는 교사 목록 조회
     * @param findTeacherRequest 조회 조건을 담은 DTO
     * @return 조회된 교사 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherDto.TeacherPageResponse find(
            TeacherDto.FindTeacherRequest findTeacherRequest
    ) {
        return teacherMapper.toPageResponseDto(teacherRepository.findAll(
                TeacherSpecification.findWith(teacherMapper.toCriteria(findTeacherRequest)),
                (findTeacherRequest.page() != null && findTeacherRequest.size() != null)
                        ? PageRequest.of(findTeacherRequest.page(), findTeacherRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 교사 조회
     * @param teacherId 교사 고유번호
     * @return 특정 교사 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherDto.TeacherResponse findById(
            Long teacherId
    ) {
        return teacherMapper.toResponseDto(getTeacher(teacherId));
    }

    /**
     * 특정 교사 엔티티 조회
     * @param teacherId 교사 고유번호
     * @return 특정 교사 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Teacher getTeacher(
            Long teacherId
    ) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교사입니다. id = " + teacherId));
    }

    /**
     * 특정 교사 수정
     * @param teacherId 수정할 교사의 ID
     * @param updateTeacherRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long teacherId,
            TeacherDto.UpdateTeacherRequest updateTeacherRequest
    ) {
        // 수정할 교사 엔티티 조회
        Teacher teacher = getTeacher(teacherId);
        // 수정 요청 객체에 학원 ID가 있다면 학원 엔티티 조회(없으면 Null)
        Institute institute = Optional.ofNullable(updateTeacherRequest.instituteId())
                .map(instituteService::getInstitute)
                .orElse(null);
        // 수정 요청 객체에 학급 ID가 있다면 학급 엔티티 조회(없으면 Null)
        Homeroom homeroom = Optional.ofNullable(updateTeacherRequest.homeroomId())
                .map(homeroomService::getHomeroom)
                .orElse(null);
        // 현재 비밀번호와 기존 비밀번호가 일치하면 진행. 다르다면 예외처리
        Optional.of(updateTeacherRequest.currentPassword())
                .filter(pwd -> passwordEncoder.matches(pwd, teacher.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다."));
        // 새로운 비밀번호를 암호화
        String encodedNewPassword = Optional.ofNullable(updateTeacherRequest.newPassword())
                .filter(pwd -> !pwd.isBlank())
                .map(passwordEncoder::encode)
                .orElse(null);
        // UPDATE 도메인 메서드로 변환
        teacher.updateTeacher(
                updateTeacherRequest.accountId(),
                encodedNewPassword,
                updateTeacherRequest.name(),
                updateTeacherRequest.phoneNumber(),
                updateTeacherRequest.email(),
                updateTeacherRequest.status(),
                updateTeacherRequest.role(),
                updateTeacherRequest.authenticationStatus(),
                updateTeacherRequest.description(),
                institute,
                homeroom
        );
        // 교사 엔티티 UPDATE
        teacherRepository.save(teacher);
    }

    /**
     * 특정 교사 삭제
     * @param teacherId 삭제할 교사의 ID
     */
    @Override
    @Transactional
    public void delete(
            Long teacherId
    ) {
        teacherRepository.delete(getTeacher(teacherId));
    }
}