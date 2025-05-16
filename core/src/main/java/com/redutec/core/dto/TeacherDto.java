package com.redutec.core.dto;

import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.meta.TeacherRole;
import com.redutec.core.meta.TeacherStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class TeacherDto {
    @Schema(description = "교사 등록 요청 객체")
    public record CreateTeacherRequest(
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

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email
            String email,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "\\d{11}")
            String phoneNumber,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            TeacherStatus status,

            @Schema(description = "역할", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            TeacherRole role,

            @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            AuthenticationStatus authenticationStatus,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "소속된 교육기관 ID", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long instituteId,

            @Schema(description = "소속된 학급 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long homeroomId
    ) {}

    @Schema(description = "교사 조회 요청 객체")
    public record FindTeacherRequest(
            @Schema(description = "교사 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> teacherIds,

            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String accountId,

            @Schema(description = "이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 4)
            String name,

            @Schema(description = "소속 교육기관명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String instituteName,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = TeacherStatus.class)
            @Enumerated(EnumType.STRING)
            List<TeacherStatus> statuses,

            @Schema(description = "역할", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = TeacherRole.class)
            @Enumerated(EnumType.STRING)
            List<TeacherRole> roles,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "교사 수정 요청 객체")
    public record UpdateTeacherRequest(
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

            @Schema(description = "이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 4)
            String name,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "\\d{11}")
            String phoneNumber,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email
            String email,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            TeacherStatus status,

            @Schema(description = "역할", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            TeacherRole role,

            @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            AuthenticationStatus authenticationStatus,

            @Schema(description = "비밀번호를 잘못 입력한 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer failedLoginAttempts,

            @Schema(description = "마지막 로그인 IP", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String lastLoginIp,

            @Schema(description = "마지막 로그인 시각", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime lastLoginAt,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "소속된 교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long instituteId,

            @Schema(description = "소속된 학급 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long homeroomId
    ) {}

    @Schema(description = "교사 응답 객체")
    public record TeacherResponse(
            Long teacherId,
            String accountId,
            String name,
            String phoneNumber,
            String email,
            TeacherStatus status,
            TeacherRole role,
            AuthenticationStatus authenticationStatus,
            Integer failedLoginAttempts,
            String lastLoginIp,
            LocalDateTime lastLoginAt,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "교사 응답 페이징 객체")
    public record TeacherPageResponse(
            List<TeacherResponse> teachers,
            Long totalElements,
            Integer totalPages
    ) {}
}