package com.redutec.admin.authentication.service;

import com.redutec.admin.administrator.service.AdministratorService;
import com.redutec.admin.authentication.dto.AuthenticationDto;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.config.EncryptUtil;
import com.redutec.core.entity.Administrator;
import com.redutec.core.entity.AdministratorMenu;
import com.redutec.core.entity.RefreshToken;
import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.meta.Domain;
import com.redutec.core.repository.AdministratorMenuRepository;
import com.redutec.core.repository.AdministratorRepository;
import com.redutec.core.repository.RefreshTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.redutec.core.meta.AuthenticationStatus.PASSWORD_RESET;

/**
 * AuthenticationServiceImpl는 시스템 관리자 인증 및 권한 관련 로직을 처리하는 서비스 클래스입니다.
 * 시스템 관리자 로그인, 비밀번호 재설정, 토큰 발급 등의 기능을 제공합니다.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AdministratorRepository administratorRepository;
    private final AdministratorService administratorService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdministratorMenuRepository administratorMenuRepository;
    private final EncryptUtil encryptUtil;

    /**
     * 시스템 관리자 로그인 처리
     *
     * @param loginRequest 로그인 요청 정보를 포함하는 데이터 전송 객체
     * @return Access Token 및 Refresh Token이 담긴 Map 객체
     */
    @Override
    public AuthenticationDto.LoginResponse login(
            AuthenticationDto.LoginRequest loginRequest
    ) {
        // 시스템 관리자 엔티티 조회
        Administrator administrator = administratorService.findByNickname(loginRequest.nickname());
        // 시스템 관리자 계정 상태 검증
        validateAuthenticationStatus(administrator);
        // 비밀번호가 일치하는지 검증
        Optional.of(administrator)
                .filter(admin -> passwordEncoder.matches(loginRequest.password(), admin.getPassword()))
                .orElseThrow(() -> {
                    handleFailedLoginAttempt(administrator);
                    return new BadCredentialsException("Please check your email or password");
                });
        // 현재 요청의 IP 주소를 Optional 체인으로 가져옴 (없으면 "unknown")
        String ipAddress = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(HttpServletRequest::getRemoteAddr)
                .orElse("unknown");
        // 로그인에 성공하면 마지막 로그인 일시와 IP 업데이트 후 실패 횟수 초기화
        administrator.setLastLoginAt(LocalDateTime.now());
        administrator.setLastLoginIp(ipAddress);
        administrator.setFailedLoginAttempts(0);
        administratorRepository.save(administrator);
        // AccessToken 생성
        String accessToken = jwtUtil.generateAccessToken(administrator);
        // RefreshToken 생성
        String refreshToken = jwtUtil.generateRefreshToken(administrator);
        // 생성한 RefreshToken을 DB에 저장
        jwtUtil.saveRefreshToken(refreshToken, administrator.getEmail(), Domain.ADMIN);
        // 생성한 Token으로 로그인 응답 객체를 리턴
        return new AuthenticationDto.LoginResponse(accessToken, refreshToken);
    }

    /**
     * 현재 로그인한 시스템 관리자 정보 조회
     *
     * @return 시스템 관리자 정보가 담긴 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public AuthenticationDto.AuthenticatedAdministrator getAuthenticatedAdministrator() {
        // 현재 접속한 계정 정보를 가져오기
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 계정 정보에 담긴 이메일 정보 가져오기
        String nickname = (principal instanceof String)
                ? (String) principal
                : ((User) principal).getUsername();
        // 이메일로 Administrator 엔티티 조회
        Administrator administrator = administratorService.findByNickname(nickname);
        // 현재 로그인한 시스템 관리자 정보를 리턴
        return new AuthenticationDto.AuthenticatedAdministrator(
                administrator.getId(),
                administrator.getEmail(),
                administrator.getNickname(),
                administratorMenuRepository.findAllByAccessibleRolesContains(administrator.getRole()).stream()
                        .map(AdministratorMenu::getId)
                        .toList(),
                administrator.getRole()
        );
    }

    /**
     * 비밀번호 초기화 처리
     *
     * @param resetPasswordRequest 비밀번호 초기화를 위한 DTO
     */
    @Override
    @Transactional
    public void resetPassword(
            AuthenticationDto.ResetPasswordRequest resetPasswordRequest
    ) throws MessagingException {
        var administrator = administratorService.findByNickname(resetPasswordRequest.nickname());
        validateAuthenticationStatus(administrator);
        var newPasswordLength = 8;
        var newPassword = generateRandomPassword(newPasswordLength);
        updatePasswordAndStatus(administrator, newPassword, PASSWORD_RESET);
        // TODO 메일 또는 알림톡 발송 기능 구현
    }

    /**
     * 비밀번호 변경 처리
     *
     * @param updatePasswordRequest 비밀번호 변경을 위한 DTO
     */
    @Override
    @Transactional
    public void updatePassword(
            AuthenticationDto.UpdatePasswordRequest updatePasswordRequest
    ) {
        var administrator = administratorService.findByNickname(updatePasswordRequest.nickname());
        if (administrator.getAuthenticationStatus() != PASSWORD_RESET) {
            validateAuthenticationStatus(administrator);
        }
        if (!passwordEncoder.matches(updatePasswordRequest.password(), administrator.getPassword())) {
            throw new BadCredentialsException("Your email or password does not match.");
        }
        updatePasswordAndStatus(administrator, updatePasswordRequest.newPassword(), AuthenticationStatus.ACTIVE);
    }

    /**
     * Refresh Token을 사용하여 새로운 Access Token을 발급
     *
     * @param refreshToken 리프레시 토큰
     * @return 새로운 Access Token이 담긴 Map 객체
     */
    @Override
    @Transactional
    public AuthenticationDto.LoginResponse refreshAccessToken(
            String refreshToken
    ) {
        // 새로운 Access Token 발급을 요청한 Refresh Token 조회
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .filter(RefreshToken::isExpired)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token"));
        // refreshTokenEntity에 있는 시스템 관리자 닉네임으로 시스템 관리자 엔티티 조회
        Administrator administrator = administratorRepository.findByNickname(refreshTokenEntity.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("No such administrator"));
        // 새로운 Access Token 생성 후 리턴
        return new AuthenticationDto.LoginResponse(
                jwtUtil.generateAccessToken(administrator),
                refreshToken
        );
    }

    /**
     * 시스템 관리자 계정 상태 검증
     *
     * @param administrator 검증할 시스템 관리자 객체
     */
    public void validateAuthenticationStatus(
            Administrator administrator
    ) {
        switch (administrator.getAuthenticationStatus()) {
            case ACTIVE -> {}
            case INACTIVE, SUSPENDED ->
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your account is not active.");
            case LOCKED ->
                    throw new ResponseStatusException(HttpStatus.LOCKED, "Your account is locked. Please reset your password.");
            case PASSWORD_RESET ->
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Your password has been reset. Please change your password before logging in.");
            case WITHDRAWN ->
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Your account is withdrawn.");
            default ->
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected account status.");
        }
    }

    /**
     * 로그인 실패 처리
     *
     * @param administrator 로그인 실패한 시스템 관리자 객체
     */
    private void handleFailedLoginAttempt(Administrator administrator) {
        // 비밀번호를 틀린 횟수 1회 추가
        administrator.setFailedLoginAttempts(administrator.getFailedLoginAttempts() + 1);
        // 비밀번호를 틀린 횟수가 5회 이상일 경우 계정 LOCKED
        if (administrator.getFailedLoginAttempts() >= 5) {
            administrator.setAuthenticationStatus(AuthenticationStatus.LOCKED);
            administratorRepository.save(administrator);
            throw new ResponseStatusException(HttpStatus.LOCKED, "Your account is locked due to too many failed login attempts. please reset your password.");
        }
        administratorRepository.saveAndFlush(administrator);
    }

    /**
     * 시스템 관리자 비밀번호 및 상태 업데이트
     *
     * @param administrator 시스템 관리자 객체
     * @param newPassword 새 비밀번호
     * @param newStatus 새 상태
     */
    private void updatePasswordAndStatus(
            Administrator administrator,
            String newPassword,
            AuthenticationStatus newStatus
    ) {
        administrator.setPassword(passwordEncoder.encode(newPassword));
        administrator.setAuthenticationStatus(newStatus);
        administrator.setFailedLoginAttempts(0);
        administratorRepository.save(administrator);
    }

    /**
     * 랜덤 비밀번호 생성
     *
     * @param length 비밀번호 길이
     * @return 랜덤 생성된 비밀번호
     */
    private String generateRandomPassword(
            int length
    ) {
        var upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        var lower = "abcdefghijklmnopqrstuvwxyz";
        var digits = "0123456789";
        var specials = "!@#$%^&*()-_=+<>";
        var allChars = upper + lower + digits + specials;
        var random = new SecureRandom();
        var password = new char[length];
        password[0] = upper.charAt(random.nextInt(upper.length()));
        password[1] = lower.charAt(random.nextInt(lower.length()));
        password[2] = digits.charAt(random.nextInt(digits.length()));
        password[3] = specials.charAt(random.nextInt(specials.length()));
        for (int i = 4; i < length; i++) {
            password[i] = allChars.charAt(random.nextInt(allChars.length()));
        }
        for (int i = password.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            var temp = password[index];
            password[index] = password[i];
            password[i] = temp;
        }
        log.info("**** reset password: {}", new String(password));
        return new String(password);
    }
}