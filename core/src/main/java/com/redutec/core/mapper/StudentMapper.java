package com.redutec.core.mapper;

import com.redutec.core.dto.StudentDto;
import com.redutec.core.criteria.StudentCriteria;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class StudentMapper {
    private final PasswordEncoder passwordEncoder;

    /**
     * CreateStudentRequest DTO를 기반으로 Student 등록 엔티티를 생성합니다.
     *
     * @param createStudentRequest 학생 등록에 필요한 데이터를 담은 DTO
     * @param institute 학생이 소속될 교육기관 엔티티
     * @param homeroom 학생이 소속될 학급 엔티티
     * @return 등록할 Student 엔티티
     */
    public Student toCreateEntity(
            StudentDto.CreateStudentRequest createStudentRequest,
            Institute institute,
            Homeroom homeroom
    ) {
        return Student.builder()
                .accountId(createStudentRequest.accountId())
                .password(passwordEncoder.encode(createStudentRequest.password()))
                .name(createStudentRequest.name())
                .phoneNumber(createStudentRequest.phoneNumber())
                .email(createStudentRequest.email())
                .birthday(createStudentRequest.birthday())
                .status(createStudentRequest.status())
                .authenticationStatus(createStudentRequest.authenticationStatus())
                .readingLevel(createStudentRequest.readingLevel())
                .raq(createStudentRequest.raq())
                .schoolGrade(createStudentRequest.schoolGrade())
                .description(createStudentRequest.description())
                .domain(createStudentRequest.domain())
                .institute(institute)
                .homeroom(homeroom)
                .build();
    }

    /**
     * CreateStudentRequest DTO를 기반으로 Student 수정 엔티티를 생성합니다.
     *
     * @param student 수정할 Student 엔티티
     * @param updateStudentRequest 학생 수정에 필요한 데이터를 담은 DTO
     * @param institute 학생이 소속될 교육기관 엔티티
     * @param homeroom 학생이 소속될 학급 엔티티
     * @return 수정할 Student 엔티티
     */
    public Student toUpdateEntity(
            Student student,
            StudentDto.UpdateStudentRequest updateStudentRequest,
            Institute institute,
            Homeroom homeroom
    ) {
        return Student.builder()
                .id(student.getId())
                .accountId(Optional.ofNullable(updateStudentRequest.accountId()).orElse(student.getAccountId()))
                .password(Optional.ofNullable(updateStudentRequest.newPassword())
                        .filter(newPassword -> !newPassword.isBlank())
                        .map(passwordEncoder::encode)
                        .orElse(student.getPassword()))
                .name(Optional.ofNullable(updateStudentRequest.name()).orElse(student.getName()))
                .phoneNumber(Optional.ofNullable(updateStudentRequest.phoneNumber()).orElse(student.getPhoneNumber()))
                .email(Optional.ofNullable(updateStudentRequest.email()).orElse(student.getEmail()))
                .birthday(Optional.ofNullable(updateStudentRequest.birthday()).orElse(student.getBirthday()))
                .status(Optional.ofNullable(updateStudentRequest.status()).orElse(student.getStatus()))
                .authenticationStatus(Optional.ofNullable(updateStudentRequest.authenticationStatus()).orElse(student.getAuthenticationStatus()))
                .readingLevel(Optional.ofNullable(updateStudentRequest.readingLevel()).orElse(student.getReadingLevel()))
                .raq(Optional.ofNullable(updateStudentRequest.raq()).orElse(student.getRaq()))
                .schoolGrade(Optional.ofNullable(updateStudentRequest.schoolGrade()).orElse(student.getSchoolGrade()))
                .bookMbti(Optional.ofNullable(updateStudentRequest.bookMbti()).orElse(student.getBookMbti()))
                .description(Optional.ofNullable(updateStudentRequest.description()).orElse(student.getDescription()))
                .domain(Optional.ofNullable(updateStudentRequest.domain()).orElse(student.getDomain()))
                .institute(Optional.ofNullable(institute).orElse(student.getInstitute()))
                .homeroom(Optional.ofNullable(homeroom).orElse(student.getHomeroom()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindStudentRequest 객체를 기반으로
     * StudentCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 학생 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 StudentCriteria 객체
     */
    public StudentCriteria toCriteria(StudentDto.FindStudentRequest findStudentRequest) {
        return new StudentCriteria(
                findStudentRequest.studentIds(),
                findStudentRequest.accountId(),
                findStudentRequest.name(),
                findStudentRequest.instituteName(),
                findStudentRequest.domains()
        );
    }

    /**
     * Student 엔티티를 기반으로 응답용 StudentResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param student 변환할 Student 엔티티 (null 가능)
     * @return Student 엔티티의 데이터를 담은 StudentResponse DTO, student가 null이면 null 반환
     */
    public StudentDto.StudentResponse toResponseDto(Student student) {
        // 교육기관 정보 담기
        Long instituteId = Optional.ofNullable(student.getInstitute())
                .map(Institute::getId)
                .orElse(null);
        String instituteName = Optional.ofNullable(student.getInstitute())
                .map(Institute::getName)
                .orElse(null);
        // 학급 정보 담기
        Long homeroomId = Optional.ofNullable(student.getHomeroom())
                .map(Homeroom::getId)
                .orElse(null);
        String homeroomName = Optional.ofNullable(student.getHomeroom())
                .map(Homeroom::getName)
                .orElse(null);
        return new StudentDto.StudentResponse(
                student.getId(),
                student.getAccountId(),
                student.getName(),
                student.getPhoneNumber(),
                student.getEmail(),
                student.getBirthday(),
                student.getStatus(),
                student.getAuthenticationStatus(),
                student.getReadingLevel(),
                student.getRaq(),
                student.getSchoolGrade(),
                student.getBookMbti(),
                student.getLastLoginIp(),
                student.getLastLoginAt(),
                student.getDescription(),
                student.getDomain(),
                instituteId,
                instituteName,
                homeroomId,
                homeroomName,
                student.getCreatedAt(),
                student.getUpdatedAt()
        );
    }

    /**
     * Page 형식의 Student 엔티티 목록을 StudentPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param studentPage Page 형태로 조회된 Student 엔티티 목록 (null 가능)
     * @return Student 엔티티 리스트와 페이지 정보를 담은 StudentPageResponse DTO, studentPage가 null이면 null 반환
     */
    public StudentDto.StudentPageResponse toPageResponseDto(Page<Student> studentPage) {
        return Optional.ofNullable(studentPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new StudentDto.StudentPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}