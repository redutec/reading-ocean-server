package com.redutec.admin.teacher.service;

import com.redutec.core.dto.TeacherDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.TeacherMapper;
import com.redutec.core.repository.InstituteRepository;
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
    private final InstituteRepository instituteRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 교사 등록
     * @param createTeacherRequest 교사 등록 정보를 담은 DTO
     * @return 등록된 교사 정보
     */
    @Override
    @Transactional
    public TeacherDto.TeacherResponse create(TeacherDto.CreateTeacherRequest createTeacherRequest) {
        // 등록 요청 객체에 교사가 소속한 교육기관 고유번호가 존재하면 교육기관 엔티티 조회
        Institute institute = Optional.ofNullable(createTeacherRequest.instituteId())
                .flatMap(instituteRepository::findById)
                .orElse(null);
        // 교사 등록
        return teacherMapper.toResponseDto(teacherRepository.save(teacherMapper.createEntity(
                createTeacherRequest,
                institute
        )));
    }

    /**
     * 조건에 맞는 교사 목록 조회
     * @param findTeacherRequest 조회 조건을 담은 DTO
     * @return 조회된 교사 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherDto.TeacherPageResponse find(TeacherDto.FindTeacherRequest findTeacherRequest) {
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
    public TeacherDto.TeacherResponse get(Long teacherId) {
        return teacherMapper.toResponseDto(getTeacher(teacherId));
    }

    /**
     * 특정 교사 수정
     * @param teacherId 수정할 교사의 ID
     * @param updateTeacherRequest 수정할 정보를 담은 DTO
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
        // 수정 요청 객체에 교육기관 고유번호가 있다면 교육기관 엔티티 조회
        Institute institute = Optional.ofNullable(updateTeacherRequest.instituteId())
                .flatMap(instituteRepository::findById)
                .orElseGet(teacher::getInstitute);
        // 교사 정보 수정
        teacherMapper.updateEntity(
                teacher,
                updateTeacherRequest,
                institute
        );
    }

    /**
     * 특정 교사 삭제
     * @param teacherId 삭제할 교사의 ID
     */
    @Override
    @Transactional
    public void delete(Long teacherId) {
        teacherRepository.delete(getTeacher(teacherId));
    }

    /**
     * 특정 교사 엔티티 조회
     * @param teacherId 교사 고유번호
     * @return 특정 교사 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Teacher getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교사입니다. teacherId: " + teacherId));
    }
}