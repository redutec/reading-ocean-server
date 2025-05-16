package com.redutec.admin.student.service;

import com.redutec.admin.homeroom.service.HomeroomService;
import com.redutec.admin.institute.service.InstituteService;
import com.redutec.core.dto.StudentDto;
import com.redutec.core.mapper.StudentMapper;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Student;
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
    private final PasswordEncoder passwordEncoder;
    private final InstituteService instituteService;
    private final HomeroomService homeroomService;

    /**
     * 학생 등록
     * @param createStudentRequest 학생 등록 정보를 담은 DTO
     * @return 등록된 학생 정보
     */
    @Override
    @Transactional
    public StudentDto.StudentResponse create(StudentDto.CreateStudentRequest createStudentRequest) {
        return studentMapper.toResponseDto(
                studentRepository.save(
                        studentMapper.toEntity(
                                createStudentRequest,
                                Optional.ofNullable(createStudentRequest.instituteId())
                                        .map(instituteService::getInstitute)
                                        .orElse(null),
                                Optional.ofNullable(createStudentRequest.homeroomId())
                                        .map(homeroomService::getHomeroom)
                                        .orElse(null)
                        )
                )
        );
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
    public StudentDto.StudentResponse findById(Long studentId) {
        return studentMapper.toResponseDto(getStudent(studentId));
    }

    /**
     * 특정 학생 엔티티 조회
     * @param studentId 학생 고유번호
     * @return 특정 학생 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학생입니다. id = " + studentId));
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
        // 수정 요청 객체에 학원 ID가 있다면 학원 엔티티 조회(없으면 Null)
        Institute institute = Optional.ofNullable(updateStudentRequest.instituteId())
                .map(instituteService::getInstitute)
                .orElse(null);
        // 수정 요청 객체에 학급 ID가 있다면 학급 엔티티 조회(없으면 Null)
        Homeroom homeroom = Optional.ofNullable(updateStudentRequest.homeroomId())
                .map(homeroomService::getHomeroom)
                .orElse(null);
        // 현재 비밀번호와 기존 비밀번호가 일치하면 진행. 다르다면 예외처리
        Optional.of(updateStudentRequest.currentPassword())
                .filter(currentPassword -> passwordEncoder.matches(currentPassword, student.getPassword()))
                .orElseThrow(() -> new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다."));
        // 새로운 비밀번호를 암호화
        String encodedNewPassword = Optional.ofNullable(updateStudentRequest.newPassword())
                .filter(newPassword -> !newPassword.isBlank())
                .map(passwordEncoder::encode)
                .orElse(null);
        // UPDATE 도메인 메서드로 변환
        student.updateStudent(
                updateStudentRequest.accountId(),
                encodedNewPassword,
                updateStudentRequest.name(),
                updateStudentRequest.phoneNumber(),
                updateStudentRequest.email(),
                updateStudentRequest.birthday(),
                updateStudentRequest.status(),
                updateStudentRequest.authenticationStatus(),
                updateStudentRequest.readingLevel(),
                updateStudentRequest.raq(),
                updateStudentRequest.schoolGrade(),
                updateStudentRequest.bookPoints(),
                updateStudentRequest.bookMbti(),
                updateStudentRequest.description(),
                updateStudentRequest.domain(),
                institute,
                homeroom
        );
        // 학생 엔티티 UPDATE
        studentRepository.save(student);
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
}