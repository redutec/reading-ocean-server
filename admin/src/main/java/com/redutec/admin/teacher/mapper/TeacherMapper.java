package com.redutec.admin.teacher.mapper;

import com.redutec.admin.teacher.dto.TeacherDto;
import com.redutec.core.criteria.TeacherCriteria;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Teacher;
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
public class TeacherMapper {
    private final PasswordEncoder passwordEncoder;

    /**
     * CreateTeacherRequest DTO를 기반으로 Teacher 엔티티를 생성합니다.
     *
     * @param createTeacherRequest 교사 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Teacher 엔티티
     */
    public Teacher toEntity(
            TeacherDto.CreateTeacherRequest createTeacherRequest,
            Institute institute,
            Homeroom homeroom
    ) {
        return Teacher.builder()
                .accountId(createTeacherRequest.accountId())
                .password(passwordEncoder.encode(createTeacherRequest.password()))
                .name(createTeacherRequest.name())
                .phoneNumber(createTeacherRequest.phoneNumber())
                .email(createTeacherRequest.email())
                .status(createTeacherRequest.status())
                .authenticationStatus(createTeacherRequest.authenticationStatus())
                .failedLoginAttempts(0)
                .description(createTeacherRequest.description())
                .institute(institute)
                .homeroom(homeroom)
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindTeacherRequest 객체를 기반으로
     * TeacherCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 교사 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 TeacherCriteria 객체
     */
    public TeacherCriteria toCriteria(TeacherDto.FindTeacherRequest findTeacherRequest) {
        return new TeacherCriteria(
                findTeacherRequest.teacherIds(),
                findTeacherRequest.accountId(),
                findTeacherRequest.name(),
                findTeacherRequest.instituteName(),
                findTeacherRequest.statuses()
        );
    }

    /**
     * Teacher 엔티티를 기반으로 응답용 TeacherResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param teacher 변환할 Teacher 엔티티 (null 가능)
     * @return Teacher 엔티티의 데이터를 담은 TeacherResponse DTO, teacher가 null이면 null 반환
     */
    public TeacherDto.TeacherResponse toResponseDto(Teacher teacher) {
        return Optional.ofNullable(teacher)
                .map(t -> new TeacherDto.TeacherResponse(
                        t.getId(),
                        t.getAccountId(),
                        t.getName(),
                        t.getPhoneNumber(),
                        t.getEmail(),
                        t.getStatus(),
                        t.getRole(),
                        t.getAuthenticationStatus(),
                        t.getFailedLoginAttempts(),
                        t.getLastLoginIp(),
                        t.getLastLoginAt(),
                        t.getDescription(),
                        t.getCreatedAt(),
                        t.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Teacher 엔티티 목록을 TeacherPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param teacherPage Page 형태로 조회된 Teacher 엔티티 목록 (null 가능)
     * @return Teacher 엔티티 리스트와 페이지 정보를 담은 TeacherPageResponse DTO, teacherPage가 null이면 null 반환
     */
    public TeacherDto.TeacherPageResponse toPageResponseDto(Page<Teacher> teacherPage) {
        return Optional.ofNullable(teacherPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new TeacherDto.TeacherPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}