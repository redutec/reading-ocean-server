package com.redutec.readingocean.edu.authentication.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ReadingOceanEduAuthenticationDto;
import com.redutec.readingocean.edu.authentication.service.AuthenticationService;
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
 * 사용자 인증/인가 처리 API 컨트롤러.
 * 이 클래스는 사용자 인증 및 인가와 관련된 API 엔드포인트를 정의합니다.
 * 로그인, 사용자 정보 조회, 로그아웃, 비밀번호 초기화 및 변경 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "사용자 인증/인가 처리 관리 API", description = "사용자 인증/인가 처리 API 모음")
public class AuthenticationController {
    private final ApiResponseManager apiResponseManager;
    private final AuthenticationService authenticationService;

    /**
     * 로그인 API.
     * 로그인 요청을 처리하고 JWT Access Token과 Refresh Token을 발급합니다.
     *
     * @param loginRequest 로그인 요청 데이터
     * @return JWT Access Token과 Refresh Token을 포함한 응답
     */
    @Operation(summary = "로그인", description = "로그인 후 JWT Access Token과 Refresh Token을 발급하는 API")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseBody> login(@ParameterObject ReadingOceanEduAuthenticationDto.LoginRequest loginRequest) {
        return apiResponseManager.ok(authenticationService.login(loginRequest));
    }

    /**
     * Refresh Token 재발급 API.
     * 만료된 Access Token을 Refresh Token을 이용해 재발급합니다.
     *
     * @param refreshToken 클라이언트에서 제공된 Refresh Token
     * @return 새로운 Access Token
     */
    @Operation(summary = "Access Token 재발급", description = "Refresh Token을 이용해 새로운 Access Token을 발급하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponseBody> refreshToken(
            @Parameter(description = "Refresh Token") @RequestParam String refreshToken
    ) {
        // Refresh Token의 유효성 확인 및 Access Token 재발급
        return apiResponseManager.ok(authenticationService.refreshAccessToken(refreshToken));
    }

    /**
     * 현재 로그인한 학생 정보 조회 API.
     * JWT 토큰을 이용하여 현재 로그인한 학생 정보를 조회합니다.
     *
     * @return 로그인한 학생 정보
     */
    @Operation(summary = "현재 로그인한 학생 정보 조회", description = "JWT 토큰을 이용하여 현재 로그인한 학생 정보를 조회하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<ApiResponseBody> getAuthenticatedStudent() {
        return apiResponseManager.ok(authenticationService.getAuthenticatedStudent());
    }

    /**
     * 로그아웃 API.
     * 로그아웃 후 JWT 토큰을 블랙리스트에 추가합니다.
     * 실제 로그아웃 처리는 SecurityConfig에 의해 CustomLogoutHandler에서 처리합니다.
     */
    @Operation(summary = "로그아웃", description = "로그아웃 후 JWT 토큰을 블랙리스트에 추가하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseBody> logout() {
        return apiResponseManager.noContent();
    }

    /**
     * 비밀번호 초기화 API.
     * 사용자의 비밀번호를 초기화합니다.
     *
     * @param resetPasswordRequest 비밀번호 초기화 요청 데이터
     */
    @Operation(summary = "비밀번호 초기화", description = "사용자의 비밀번호를 초기화하는 API")
    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponseBody> resetPassword(
            @ParameterObject ReadingOceanEduAuthenticationDto.ResetPasswordRequest resetPasswordRequest
    ) throws MessagingException {
        authenticationService.resetPassword(resetPasswordRequest);
        return apiResponseManager.noContent();
    }

    /**
     * 비밀번호 변경 API.
     * 기존 계정 정보를 확인 후 새로운 비밀번호로 변경합니다.
     *
     * @param updatePasswordRequest 비밀번호 변경 요청 데이터
     */
    @Operation(summary = "비밀번호 변경", description = "기존 계정 정보를 확인 후 새로운 비밀번호로 변경하는 API")
    @PutMapping("/update-password")
    public ResponseEntity<ApiResponseBody> updatePassword(
            @ParameterObject ReadingOceanEduAuthenticationDto.UpdatePasswordRequest updatePasswordRequest
    ) {
        authenticationService.updatePassword(updatePasswordRequest);
        return apiResponseManager.noContent();
    }
}