package com.redutec.admin.authentication.controller;

import com.redutec.core.dto.AdminAuthenticationDto;
import com.redutec.admin.authentication.service.AuthenticationService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 어드민 사용자 인증/인가 처리 API 컨트롤러.
 * 이 클래스는 어드민 사용자 인증 및 인가와 관련된 API 엔드포인트를 정의합니다.
 * 로그인, 어드민 사용자 정보 조회, 로그아웃, 비밀번호 초기화 및 변경 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "어드민 사용자 인증/인가 처리 관리 API", description = "어드민 사용자 인증/인가 처리 API 모음")
public class AuthenticationController {
    private final ApiResponseManager apiResponseManager;
    private final AuthenticationService authenticationService;

    /**
     * 어드민 사용자 로그인 API.
     * 어드민 사용자 로그인 요청을 처리하고 JWT Access Token과 Refresh Token을 발급합니다.
     *
     * @param loginRequest 어드민 사용자 로그인 요청 데이터
     * @return JWT Access Token과 Refresh Token을 포함한 응답
     */
    @Operation(summary = "어드민 사용자 로그인", description = "로그인 후 JWT Access Token과 Refresh Token을 발급하는 API")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseBody> login(@ParameterObject AdminAuthenticationDto.LoginRequest loginRequest) {
        return apiResponseManager.ok(authenticationService.login(loginRequest));
    }

    /**
     * Refresh Token 재발급 API.
     * 만료된 Access Token을 Refresh Token을 이용해 재발급합니다.
     *
     * @param refreshToken 클라이언트에서 제공된 Refresh Token
     * @return 새로운 Access Token
     */
    @Operation(summary = "어드민 사용자의 Access Token 재발급", description = "Refresh Token을 이용해 새로운 Access Token을 발급하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponseBody> refreshToken(
            @Parameter(description = "Refresh Token") @RequestParam String refreshToken
    ) {
        return apiResponseManager.ok(authenticationService.refreshAccessToken(refreshToken));
    }

    /**
     * 현재 로그인한 어드민 사용자 정보 조회 API.
     * JWT 토큰을 이용하여 현재 로그인한 어드민 사용자 정보를 조회합니다.
     *
     * @return 어드민 사용자 정보
     */
    @Operation(summary = "현재 로그인한 어드민 사용자 정보 조회", description = "JWT 토큰을 이용하여 현재 로그인한 어드민 사용자 정보를 조회하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<ApiResponseBody> getAuthenticatedAdminUser() {
        return apiResponseManager.ok(authenticationService.getAuthenticatedAdminUser());
    }

    /**
     * 어드민 사용자 로그아웃 API.
     * 로그아웃 후 JWT 토큰을 블랙리스트에 추가합니다.
     * 실제 로그아웃 처리는 SecurityConfig에 의해 CustomLogoutHandler에서 처리합니다.
     */
    @Operation(summary = "어드민 사용자 로그아웃", description = "어드민 사용자 로그아웃 후 JWT 토큰을 블랙리스트에 추가하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseBody> logout() {
        return apiResponseManager.noContent();
    }

    /**
     * 어드민 사용자의 비밀번호 초기화 API.
     * 어드민 사용자의 비밀번호를 초기화합니다.
     *
     * @param resetPasswordRequest 어드민 사용자 비밀번호 초기화 요청 데이터
     */
    @Operation(summary = "어드민 사용자의 비밀번호 초기화", description = "어드민 사용자의 비밀번호를 초기화하는 API")
    @PatchMapping("/reset-password")
    public ResponseEntity<ApiResponseBody> resetPassword(
            @ParameterObject AdminAuthenticationDto.ResetPasswordRequest resetPasswordRequest
    ) throws MessagingException {
        authenticationService.resetPassword(resetPasswordRequest);
        return apiResponseManager.noContent();
    }

    /**
     * 어드민 사용자의 비밀번호 변경 API.
     * 기존 어드민 사용자 계정 정보를 확인 후 새로운 비밀번호로 변경합니다.
     *
     * @param updatePasswordRequest 어드민 사용자 비밀번호 변경 요청 데이터
     */
    @Operation(summary = "어드민 사용자의 비밀번호 변경", description = "기존 어드민 사용자 계정 정보를 확인 후 새로운 비밀번호로 변경하는 API")
    @PatchMapping("/update-password")
    public ResponseEntity<ApiResponseBody> updatePassword(
            @ParameterObject AdminAuthenticationDto.UpdatePasswordRequest updatePasswordRequest
    ) {
        authenticationService.updatePassword(updatePasswordRequest);
        return apiResponseManager.noContent();
    }
}