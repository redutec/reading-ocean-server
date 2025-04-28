package com.redutec.admin.student.dto;

import com.redutec.core.meta.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class StudentDto {
    @Schema(description = "학생 등록 요청 객체")
    public record CreateStudentRequest(
            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 20)
            String accountId,

            @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String password,

            @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 4)
            String name,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "\\d{11}")
            String phoneNumber,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email
            String email,

            @Schema(description = "생년월일", requiredMode = Schema.RequiredMode.REQUIRED)
            @Pattern(regexp = "^[0-9]{8}$", message = "생년월일은 8자리의 숫자로만 구성되어야 합니다.")
            String birthday,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            StudentStatus status,

            @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            AuthenticationStatus authenticationStatus,

            @Schema(description = "독서 레벨", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            ReadingLevel readingLevel,

            @Schema(description = "RAQ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer raq,

            @Schema(description = "학년", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            SchoolGrade schoolGrade,

            @Schema(description = "도서 포인트", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer bookPoints,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "소속된 교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long instituteId,

            @Schema(description = "소속된 학급 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long instituteClassId
    ) {}

    @Schema(description = "학생 조회 요청 객체")
    public record FindStudentRequest(
            @Schema(description = "학생 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> studentIds,

            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String accountId,

            @Schema(description = "이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 4)
            String name,

            @Schema(description = "소속 교육기관명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String instituteName,

            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = Domain.class)
            @Enumerated(EnumType.STRING)
            List<Domain> domains,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "학생 수정 요청 객체")
    public record UpdateStudentRequest(
            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String accountId,

            @Schema(description = "기존 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String currentPassword,

            @Schema(description = "신규 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String newPassword,

            @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 4)
            String name,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "\\d{11}")
            String phoneNumber,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email
            String email,

            @Schema(description = "생년월일", requiredMode = Schema.RequiredMode.REQUIRED)
            @Pattern(regexp = "^[0-9]{8}$", message = "생년월일은 8자리의 숫자로만 구성되어야 합니다.")
            String birthday,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            StudentStatus status,

            @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            AuthenticationStatus authenticationStatus,

            @Schema(description = "독서 레벨", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            ReadingLevel readingLevel,

            @Schema(description = "RAQ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer raq,

            @Schema(description = "학년", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            SchoolGrade schoolGrade,

            @Schema(description = "도서 포인트", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer bookPoints,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "소속된 교육기관 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long instituteId,

            @Schema(description = "소속된 학급 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long instituteClassId
    ) {}

    @Schema(description = "학생 응답 객체")
    public record StudentResponse(
            Long studentId,
            String accountId,
            String name,
            String phoneNumber,
            String email,
            String birthday,
            StudentStatus status,
            AuthenticationStatus authenticationStatus,
            ReadingLevel readingLevel,
            Integer raq,
            SchoolGrade schoolGrade,
            Integer bookPoints,
            String lastLoginIp,
            LocalDateTime lastLoginAt,
            String description,
            Domain domain,
            Long instituteId,
            String instituteName,
            Long instituteClassId,
            String instituteClassName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "학생 응답 페이징 객체")
    public record StudentPageResponse(
            List<StudentResponse> students,
            Long totalElements,
            Integer totalPages
    ) {}
}