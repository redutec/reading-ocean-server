package com.redutec.admin.administrator.dto;

import com.redutec.core.meta.AdministratorRole;
import com.redutec.core.meta.AuthenticationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class AdministratorDto {
    @Schema(description = "어드민 사용자 등록 요청 객체")
    public record CreateAdministratorRequest(
        @Schema(description = "이메일")
        @Email
        String email,

        @Schema(description = "비밀번호")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
        String password,

        @Schema(description = "이름")
        @Size(min = 1, max = 50)
        String nickname,

        @Schema(description = "연락처")
        @Pattern(regexp = "\\d{11}")
        String phoneNumber,

        @Schema(description = "권한")
        @Enumerated(EnumType.STRING)
        AdministratorRole role
    ) {}

    @Schema(description = "어드민 사용자 조회 요청 객체")
    public record FindAdministratorRequest(
        @Schema(description = "어드민 사용자 ID")
        List<@Positive Long> administratorIds,

        @Schema(description = "이메일")
        @Email
        String email,

        @Schema(description = "닉네임")
        @Size(max = 20, message = "닉네임은 20자를 넘을 수 없습니다")
        String nickname,

        @Schema(description = "권한")
        @ElementCollection(targetClass = AdministratorRole.class)
        @Enumerated(EnumType.STRING)
        List<AdministratorRole> roles,

        @Schema(description = "계정 상태")
        @ElementCollection(targetClass = AuthenticationStatus.class)
        @Enumerated(EnumType.STRING)
        List<AuthenticationStatus> authenticationStatuses,

        @Schema(description = "마지막 접속 검색 시작일")
        LocalDateTime lastLoginAtAfter,

        @Schema(description = "마지막 접속 검색 종료일")
        LocalDateTime lastLoginAtBefore,

        @Schema(description = "페이지 번호(0 이상의 정수)", example = "0")
        @PositiveOrZero
        Integer page,

        @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", example = "60")
        @Positive
        Integer size
    ) {}

    @Schema(description = "어드민 사용자 수정 요청 객체")
    public record UpdateAdministratorRequest(
        @Schema(description = "닉네임", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(min = 1, max = 20)
        String nickname,

        @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
        String password,

        @Schema(description = "권한", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Enumerated(EnumType.STRING)
        AdministratorRole role,

        @Schema(description = "계정 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Enumerated(EnumType.STRING)
        AuthenticationStatus authenticationStatus,

        @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Email
        String email,

        @Schema(description = "비밀번호를 잘못 입력한 횟수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        Integer failedLoginAttempts,

        @Schema(description = "마지막 로그인 IP", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String lastLoginIp,

        @Schema(description = "마지막 로그인 시각", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDateTime lastLoginAt

    ) {}

    @Schema(description = "어드민 사용자 응답 객체")
    public record AdministratorResponse(
        Long administratorId,
        String email,
        String nickname,
        List<Long> accessibleMenuIds,
        AdministratorRole role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {}

    @Schema(description = "어드민 사용자 응답 페이징 객체")
    public record AdministratorPageResponse(
        List<AdministratorResponse> administrators,
        long totalElements,
        int totalPages
    ) {}
}