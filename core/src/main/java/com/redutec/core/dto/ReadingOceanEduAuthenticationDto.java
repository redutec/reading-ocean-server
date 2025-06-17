package com.redutec.core.dto;

import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.meta.StudentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ReadingOceanEduAuthenticationDto {
    @Schema(description = "현재 로그인한 학생의 정보가 담긴 객체")
    public record AuthenticatedStudent(
            Long studentId,
            String accountId,
            String name,
            String phoneNumber,
            String email,
            StudentStatus status,
            AuthenticationStatus authenticationStatus,
            Integer failedLoginAttempts,
            Long instituteId,
            String instituteName,
            Long homeroomId,
            String homeroomName
    ) {}

    @Schema(description = "학생 로그인 요청 객체")
    public record LoginRequest(
            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "seongnam_chief")
            @NotNull
            String accountId,

            @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "Redutec123!")
            @NotNull
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String password
    ) {}

    @Schema(description = "학생 로그인 응답 객체")
    public record LoginResponse(
            String accessToken,
            String refreshToken
    ) {}

    @Schema(description = "학생 계정 비밀번호 초기화 요청 객체")
    public record ResetPasswordRequest(
            @Schema(description = "비밀번호를 초기화 할 학생 계정의 로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(min = 1, max = 20)
            String accountId
    ) {}

    @Schema(description = "학생 계정 비밀번호 변경 요청 객체")
    public record UpdatePasswordRequest(
            @Schema(description = "비밀번호를 변경할 학생 계정의 로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(min = 1, max = 20)
            String accountId,

            @Schema(description = "기존 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String password,

            @Schema(description = "신규 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String newPassword
    ) {}
}