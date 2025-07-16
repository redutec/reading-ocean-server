package com.redutec.admin.student.service;

import com.redutec.core.dto.StudentDto;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Student;
import com.redutec.core.mapper.StudentMapper;
import com.redutec.core.repository.HomeroomRepository;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.StudentRepository;
import com.redutec.core.specification.StudentSpecification;
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
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final InstituteRepository instituteRepository;
    private final HomeroomRepository homeroomRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 학생 등록
     * @param createStudentRequest 학생 등록 정보를 담은 DTO
     * @return 등록된 학생 정보
     */
    @Override
    @Transactional
    public StudentDto.StudentResponse create(StudentDto.CreateStudentRequest createStudentRequest) {
        // 등록 요청 객체에 교육기관 고유번호가 있다면 교육기관 엔티티 조회
        Institute institute = Optional.ofNullable(createStudentRequest.instituteId())
                .flatMap(instituteRepository::findById)
                .orElse(null);
        // 등록 요청 객체에 학급 고유번호가 있다면 학급 엔티티 조회
        Homeroom homeroom = Optional.ofNullable(createStudentRequest.homeroomId())
                .flatMap(homeroomRepository::findById)
                .orElse(null);
        return studentMapper.toResponseDto(studentRepository.save(studentMapper.createEntity(
                createStudentRequest,
                institute,
                homeroom
        )));
    }

    /**
     * 조건에 맞는 학생 목록 조회
     * @param findStudentRequest 조회 조건을 담은 DTO
     * @return 조회된 학생 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public StudentDto.StudentPageResponse find(StudentDto.FindStudentRequest findStudentRequest) {
        return studentMapper.toPageResponseDto(studentRepository.findAll(
                StudentSpecification.findWith(studentMapper.toCriteria(findStudentRequest)),
                (findStudentRequest.page() != null && findStudentRequest.size() != null)
                        ? PageRequest.of(findStudentRequest.page(), findStudentRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 학생 조회
     * @param studentId 학생 고유번호
     * @return 특정 학생 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public StudentDto.StudentResponse get(Long studentId) {
        return studentMapper.toResponseDto(getStudent(studentId));
    }

    /**
     * 특정 학생 수정
     * @param studentId 수정할 학생의 ID
     * @param updateStudentRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long studentId, StudentDto.UpdateStudentRequest updateStudentRequest) {
        // 수정할 학생 엔티티 조회
        Student student = getStudent(studentId);
        // 수정 요청 객체에 학원 ID가 있다면 학원 엔티티 조회(없으면 기존 값을 사용)
        Institute institute = Optional.ofNullable(updateStudentRequest.instituteId())
                .flatMap(instituteRepository::findById)
                .orElseGet(student::getInstitute);
        // 수정 요청 객체에 학급 ID가 있다면 학급 엔티티 조회(없으면 기존 값을 사용)
        Homeroom homeroom = Optional.ofNullable(updateStudentRequest.homeroomId())
                .flatMap(homeroomRepository::findById)
                .orElseGet(student::getHomeroom);
        // 수정 요청 객체에 newPassword가 존재한다면 현재 비밀번호와 currentPassword가 일치하는지 검증
        Optional.ofNullable(updateStudentRequest.newPassword())
                .filter(newPassword -> !newPassword.isBlank())
                .ifPresent(newPassword -> {
                    // currentPassword 미입력 시 예외
                    Optional.ofNullable(updateStudentRequest.currentPassword())
                            .filter(currentPassword -> !currentPassword.isBlank())
                            .orElseThrow(() -> new IllegalArgumentException("비밀번호를 변경하려면 현재 비밀번호를 입력해야 합니다."));
                    // currentPassword 불일치 시 예외
                    Optional.of(updateStudentRequest.currentPassword())
                            .filter(currentPassword -> passwordEncoder.matches(currentPassword, student.getPassword()))
                            .orElseThrow(() -> new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다."));
                });
        studentMapper.updateEntity(student, updateStudentRequest, institute, homeroom);
    }

    /**
     * 특정 학생 삭제
     * @param studentId 삭제할 학생의 ID
     */
    @Override
    @Transactional
    public void delete(Long studentId) {
        studentRepository.delete(getStudent(studentId));
    }

    /**
     * 특정 학생 엔티티 조회
     * @param studentId 학생 고유번호
     * @return 특정 학생 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학생입니다. studentId = " + studentId));
    }
}