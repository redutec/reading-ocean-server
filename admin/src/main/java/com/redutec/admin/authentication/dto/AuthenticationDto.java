package com.redutec.admin.authentication.dto;

import com.redutec.core.meta.AdminUserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class AuthenticationDto {
    @Schema(description = "현재 로그인한 어드민 사용자의 정보가 담긴 객체")
    public record AuthenticatedAdminUser(
            Long adminUserId,
            String email,
            String nickname,
            List<Long> accessibleMenus,
            AdminUserRole role
    ) {}

    @Schema(description = "어드민 사용자 로그인 요청 객체")
    public record LoginRequest(
            @Schema(description = "로그인 이메일", requiredMode = Schema.RequiredMode.REQUIRED, example = "redutec@redutec.co.kr")
            @NotNull
            @Email
            String email,

            @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED, example = "Redutec123!")
            @NotNull
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String password
    ) {}

    @Schema(description = "어드민 사용자 로그인 응답 객체")
    public record LoginResponse(
            String accessToken,
            String refreshToken
    ) {}

    @Schema(description = "어드민 사용자 계정 비밀번호 초기화 요청 객체")
    public record ResetPasswordRequest(
            @Schema(description = "비밀번호를 초기화 할 계정의 이메일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Email
            String email
    ) {}

    @Schema(description = "어드민 사용자 계정 비밀번호 변경 요청 객체")
    public record UpdatePasswordRequest(
            @Schema(description = "비밀번호를 변경할 계정의 이메일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Email
            String email,

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