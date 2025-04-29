package com.redutec.admin.student.mapper;

import com.redutec.admin.student.dto.StudentDto;
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
     * CreateStudentRequest DTO를 기반으로 Student 엔티티를 생성합니다.
     *
     * @param createStudentRequest 학생 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Student 엔티티
     */
    public Student toEntity(
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
                .bookPoints(createStudentRequest.bookPoints())
                .description(createStudentRequest.description())
                .domain(createStudentRequest.domain())
                .institute(institute)
                .homeroom(homeroom)
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindStudentRequest 객체를 기반으로
     * StudentCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 학생 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 StudentCriteria 객체
     */
    public StudentCriteria toCriteria(
            StudentDto.FindStudentRequest findStudentRequest
    ) {
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
    public StudentDto.StudentResponse toResponseDto(
            Student student
    ) {
        return Optional.ofNullable(student)
                .map(s -> new StudentDto.StudentResponse(
                        s.getId(),
                        s.getAccountId(),
                        s.getName(),
                        s.getPhoneNumber(),
                        s.getEmail(),
                        s.getBirthday(),
                        s.getStatus(),
                        s.getAuthenticationStatus(),
                        s.getReadingLevel(),
                        s.getRaq(),
                        s.getSchoolGrade(),
                        s.getBookPoints(),
                        s.getLastLoginIp(),
                        s.getLastLoginAt(),
                        s.getDescription(),
                        s.getDomain(),
                        s.getInstitute().getId(),
                        s.getInstitute().getName(),
                        s.getHomeroom().getId(),
                        s.getHomeroom().getName(),
                        s.getCreatedAt(),
                        s.getUpdatedAt()
                ))
                .orElse(null);
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