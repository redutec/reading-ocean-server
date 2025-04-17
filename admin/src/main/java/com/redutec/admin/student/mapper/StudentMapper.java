package com.redutec.admin.student.mapper;

import com.redutec.admin.config.JwtUtil;
import com.redutec.admin.student.dto.StudentDto;
import com.redutec.admin.student.service.StudentService;
import com.redutec.core.criteria.v1.ActAccountCriteria;
import com.redutec.core.entity.v1.ActAccount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class StudentMapper {
    private final StudentService studentService;
    private final JwtUtil jwtUtil;

    /**
     * CreateStudentRequest DTO를 기반으로 ActAccount 엔티티를 생성합니다.
     *
     * @param createStudentRequest 사이트 설정 생성에 필요한 데이터를 담은 DTO
     * @return ActAccount 엔티티
     */
    public ActAccount toEntity(
            StudentDto.CreateStudentRequest createStudentRequest
    ) {
        String adminId = Optional.ofNullable(jwtUtil.extractUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElse("admin");
        return ActAccount.builder()
                .build();
    }


    /**
     * FindStudentRequest DTO를 기반으로 검색 조건 객체인 ActAccountCriteria를 생성합니다.
     * 내부 검색 로직에서 사이트 설정 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findStudentRequest 사이트 설정 조회 조건을 담은 DTO
     * @return 해당 요청의 필드를 이용해 생성된 ActAccountCriteria 객체
     */
    public ActAccountCriteria toCriteria(
            StudentDto.FindStudentRequest findStudentRequest
    ) {
        return ActAccountCriteria.builder()
                .accountNoList(findStudentRequest.accountNoList())
                .academyName(findStudentRequest.academyName())
                .name(findStudentRequest.name())
                .accountId(findStudentRequest.accountId())
                .signupDomainCodeList(findStudentRequest.signupDomainCodeList())
                .build();
    }

    /**
     * Student 엔티티를 기반으로 응답용 StudentResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param actAccount 변환할 엔티티 (null 가능)
     * @return Student 엔티티의 데이터를 담은 StudentResponse DTO, company가 null이면 null 반환
     */
    public StudentDto.StudentResponse toResponseDto(
            ActAccount actAccount
    ) {
        return new StudentDto.StudentResponse(
                actAccount.getAccountNo(),
                actAccount.getAccountId(),
                actAccount.getEmail(),
                actAccount.getName(),
                actAccount.getMobileNo(),
                actAccount.getSchoolGrade(),
                actAccount.getAccountStatus(),
                actAccount.getSignupDomainCode(),
                actAccount.getAcademy().getAcademyNo(),
                actAccount.getAcademy().getAcademyName()
                );
    }

    /**
     * Page 형식의 엔티티 목록을 StudentPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param actAccountPage Page 형태로 조회된 엔티티 목록 (null 가능)
     * @return Student 엔티티 리스트와 페이지 정보를 담은 StudentPageResponse DTO, actAccountPage가 null이면 null 반환
     */
    public StudentDto.StudentPageResponse toPageResponseDto(
            Page<ActAccount> actAccountPage
    ) {
        return Optional.ofNullable(actAccountPage)
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