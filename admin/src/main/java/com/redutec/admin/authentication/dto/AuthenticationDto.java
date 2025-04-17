package com.redutec.admin.authentication.dto;

import com.redutec.core.meta.AdministratorRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class AuthenticationDto {
    @Schema(description = "현재 로그인한 시스템 관리자의 정보가 담긴 객체")
    public record AuthenticatedAdministrator(
        Long administratorId,
        String nickname,
        String email,
        List<Long> accessibleMenus,
        AdministratorRole role
    ) {}

    @Schema(description = "시스템 관리자 로그인 요청 객체")
    public record LoginRequest(
        @Schema(description = "닉네임", example = "redutec")
        @Size(max = 20, message = "닉네임은 20자를 넘을 수 없습니다")
        String nickname,

        @Schema(description = "비밀번호", example = "Redutec123!")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
        String password
    ) {}

    @Schema(description = "시스템 관리자 로그인 응답 객체")
    public record LoginResponse(
        String accessToken,
        String refreshToken
    ) {}

    @Schema(description = "시스템 관리자 계정 비밀번호 초기화 요청 객체")
    public record ResetPasswordRequest(
        @Schema(description = "비밀번호를 초기화 할 계정의 닉네임")
        @NotBlank
        String nickname
    ) {}

    @Schema(description = "시스템 관리자 계정 비밀번호 변경 요청 객체")
    public record UpdatePasswordRequest(
        @Schema(description = "비밀번호를 변경할 계정의 닉네임")
        @NotBlank
        String nickname,

        @Schema(description = "기존 비밀번호")
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
        String password,

        @Schema(description = "신규 비밀번호")
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
        String newPassword
    ) {}
}
