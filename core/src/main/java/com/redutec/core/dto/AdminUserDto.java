package com.redutec.core.dto;

import com.redutec.core.meta.AdminUserRole;
import com.redutec.core.meta.AuthenticationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class AdminUserDto {
    @Schema(description = "어드민 사용자 등록 요청 객체")
    public record CreateAdminUserRequest(
            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(min = 1, max = 20)
            String accountId,

            @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String password,

            @Schema(description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(min = 1, max = 50)
            String nickname,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Email
            String email,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "\\d{11}")
            String phoneNumber,

            @Schema(description = "권한", requiredMode = Schema.RequiredMode.REQUIRED, example = "DEVELOPMENT_TEAM_MEMBER")
            @NotNull
            @Enumerated(EnumType.STRING)
            AdminUserRole role,

            @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.REQUIRED, example = "INACTIVE")
            @NotNull
            @Enumerated(EnumType.STRING)
            AuthenticationStatus authenticationStatus,

            @Schema(description = "비밀번호를 잘못 입력한 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer failedLoginAttempts
    ) {}

    @Schema(description = "어드민 사용자 조회 요청 객체")
    public record FindAdminUserRequest(
            @Schema(description = "어드민 사용자 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> adminUserIds,

            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(min = 1, max = 20)
            String accountId,

            @Schema(description = "닉네임", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20, message = "닉네임은 20자를 넘을 수 없습니다")
            String nickname,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email
            String email,

            @Schema(description = "권한", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = AdminUserRole.class)
            @Enumerated(EnumType.STRING)
            List<AdminUserRole> roles,

            @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = AuthenticationStatus.class)
            @Enumerated(EnumType.STRING)
            List<AuthenticationStatus> authenticationStatuses,

            @Schema(description = "마지막 접속 검색 시작일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime lastLoginAtAfter,

            @Schema(description = "마지막 접속 검색 종료일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime lastLoginAtBefore,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "어드민 사용자 수정 요청 객체")
    public record UpdateAdminUserRequest(
            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(min = 1, max = 20)
            String accountId,

            @Schema(description = "기존 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String currentPassword,

            @Schema(description = "신규 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String newPassword,

            @Schema(description = "닉네임", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(min = 1, max = 20)
            String nickname,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email
            String email,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "\\d{11}")
            String phoneNumber,

            @Schema(description = "권한", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            AdminUserRole role,

            @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            AuthenticationStatus authenticationStatus,

            @Schema(description = "비밀번호를 잘못 입력한 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer failedLoginAttempts,

            @Schema(description = "마지막 로그인 IP", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String lastLoginIp,

            @Schema(description = "마지막 로그인 시각", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDateTime lastLoginAt
    ) {}

    @Schema(description = "어드민 사용자 응답 객체")
    public record AdminUserResponse(
            Long adminUserId,
            String accountId,
            String nickname,
            String email,
            List<Long> accessibleMenuIds,
            AdminUserRole role,
            AuthenticationStatus authenticationStatus,
            Integer failedLoginAttempts,
            String lastLoginIp,
            LocalDateTime lastLoginAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "어드민 사용자 응답 페이징 객체")
    public record AdminUserPageResponse(
            List<AdminUserResponse> adminUsers,
            Long totalElements,
            Integer totalPages
    ) {}
}