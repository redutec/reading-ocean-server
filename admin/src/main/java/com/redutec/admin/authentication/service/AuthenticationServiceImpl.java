package com.redutec.admin.authentication.service;

import com.redutec.admin.config.JwtUtil;
import com.redutec.core.dto.AdminAuthenticationDto;
import com.redutec.core.dto.AdminUserDto;
import com.redutec.core.entity.AdminMenu;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.RefreshToken;
import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.meta.Domain;
import com.redutec.core.repository.AdminMenuRepository;
import com.redutec.core.repository.AdminUserRepository;
import com.redutec.core.repository.RefreshTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
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
 * AuthenticationServiceImpl는 어드민 사용자 인증 및 권한 관련 로직을 처리하는 서비스 클래스입니다.
 * 어드민 사용자 로그인, 비밀번호 재설정, 토큰 발급 등의 기능을 제공합니다.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AdminUserRepository adminUserRepository;
    private final AdminMenuRepository adminMenuRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 어드민 사용자 로그인 처리
     *
     * @param loginRequest 로그인 요청 정보를 포함하는 데이터 전송 객체
     * @return Access Token 및 Refresh Token이 담긴 Map 객체
     */
    @Override
    public AdminAuthenticationDto.LoginResponse login(AdminAuthenticationDto.LoginRequest loginRequest) {
        // 어드민 사용자 엔티티 조회
        AdminUser adminUser = adminUserRepository.findByAccountId(loginRequest.accountId())
                .orElseThrow(() -> new BadCredentialsException("로그인 아이디 또는 비밀번호를 확인해주세요."));
        // 어드민 사용자 계정 상태 검증
        validateAuthenticationStatus(adminUser);
        // 비밀번호가 일치하는지 검증
        Optional.of(adminUser)
                .filter(admin -> passwordEncoder.matches(loginRequest.password(), admin.getPassword()))
                .orElseThrow(() -> {
                    handleFailedLoginAttempt(adminUser);
                    return new BadCredentialsException("로그인 아이디 또는 비밀번호를 확인해주세요.");
                });
        // 현재 요청의 IP 주소를 Optional 체인으로 가져옴 (없으면 "unknown")
        String ipAddress = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(HttpServletRequest::getRemoteAddr)
                .orElse("unknown");
        // 로그인에 성공하면 마지막 로그인 일시와 IP 업데이트 후 실패 횟수 초기화
        adminUser.setLastLoginAt(LocalDateTime.now());
        adminUser.setLastLoginIp(ipAddress);
        adminUser.setFailedLoginAttempts(0);
        adminUserRepository.save(adminUser);
        // AccessToken 생성
        String accessToken = jwtUtil.generateAccessToken(adminUser);
        // RefreshToken 생성
        String refreshToken = jwtUtil.generateRefreshToken(adminUser);
        // 생성한 RefreshToken을 DB에 저장
        jwtUtil.saveRefreshToken(refreshToken, adminUser.getAccountId(), Domain.ADMIN);
        // 생성한 Token으로 로그인 응답 객체를 리턴
        return new AdminAuthenticationDto.LoginResponse(accessToken, refreshToken);
    }

    /**
     * 현재 로그인한 어드민 사용자 정보 조회
     *
     * @return 어드민 사용자 정보가 담긴 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public AdminUserDto.AdminUserResponse getAuthenticatedAdminUser() {
        // 현재 접속한 계정 정보를 가져오기
        var accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        // 로그인 아이디로 AdminUser 엔티티 조회
        AdminUser adminUser = adminUserRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 어드민 사용자입니다. accountId: " + accountId));
        // 현재 로그인한 어드민 사용자 정보를 리턴
        return new AdminUserDto.AdminUserResponse(
                adminUser.getId(),
                accountId,
                adminUser.getNickname(),
                adminUser.getEmail(),
                adminMenuRepository.findAllByAccessibleRolesContains(adminUser.getRole()).stream()
                        .map(AdminMenu::getId)
                        .toList(),
                adminUser.getRole(),
                adminUser.getAuthenticationStatus(),
                adminUser.getFailedLoginAttempts(),
                adminUser.getLastLoginIp(),
                adminUser.getLastLoginAt(),
                adminUser.getCreatedAt(),
                adminUser.getUpdatedAt()
        );
    }

    /**
     * 비밀번호 초기화 처리
     *
     * @param resetPasswordRequest 비밀번호 초기화를 위한 DTO
     */
    @Override
    @Transactional
    public void resetPassword(AdminAuthenticationDto.ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        var adminUser = adminUserRepository.findByAccountId(resetPasswordRequest.accountId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 어드민 사용자입니다. accountId: " + resetPasswordRequest.accountId()));
        validateAuthenticationStatus(adminUser);
        var newPasswordLength = 8;
        var newPassword = generateRandomPassword(newPasswordLength);
        updatePasswordAndStatus(adminUser, newPassword, PASSWORD_RESET);
        // TODO 메일 또는 알림톡 발송 기능 구현
    }

    /**
     * 비밀번호 변경 처리
     *
     * @param updatePasswordRequest 비밀번호 변경을 위한 DTO
     */
    @Override
    @Transactional
    public void updatePassword(AdminAuthenticationDto.UpdatePasswordRequest updatePasswordRequest) {
        var adminUser = adminUserRepository.findByAccountId(updatePasswordRequest.accountId())
                .orElseThrow(() -> new BadCredentialsException("로그인 아이디 또는 비밀번호를 확인해주세요."));
        if (adminUser.getAuthenticationStatus() != PASSWORD_RESET) {
            validateAuthenticationStatus(adminUser);
        }
        if (!passwordEncoder.matches(updatePasswordRequest.password(), adminUser.getPassword())) {
            throw new BadCredentialsException("로그인 아이디 또는 비밀번호를 확인해주세요.");
        }
        updatePasswordAndStatus(adminUser, updatePasswordRequest.newPassword(), AuthenticationStatus.ACTIVE);
    }

    /**
     * Refresh Token을 사용하여 새로운 Access Token을 발급
     *
     * @param refreshToken 리프레시 토큰
     * @return 새로운 Access Token이 담긴 Map 객체
     */
    @Override
    @Transactional
    public AdminAuthenticationDto.LoginResponse refreshAccessToken(String refreshToken) {
        // 새로운 Access Token 발급을 요청한 Refresh Token 조회
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .filter(RefreshToken::isExpired)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않거나 만료된 토큰입니다."));
        // refreshTokenEntity에 있는 어드민 사용자 닉네임으로 어드민 사용자 엔티티 조회
        AdminUser adminUser = adminUserRepository.findByAccountId(refreshTokenEntity.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계정입니다. email: " + refreshTokenEntity.getUsername()));
        // 새로운 Access Token 생성 후 리턴
        return new AdminAuthenticationDto.LoginResponse(
                jwtUtil.generateAccessToken(adminUser),
                refreshToken
        );
    }

    /**
     * 어드민 사용자 계정 상태 검증
     *
     * @param adminUser 검증할 어드민 사용자 객체
     */
    public void validateAuthenticationStatus(AdminUser adminUser) {
        switch (adminUser.getAuthenticationStatus()) {
            case ACTIVE -> {}
            case INACTIVE, SUSPENDED ->
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "계정이 활성화되어 있지 않습니다.");
            case LOCKED ->
                    throw new ResponseStatusException(HttpStatus.LOCKED, "계정이 잠겨 있습니다. 비밀번호를 재설정해 주세요.");
            case PASSWORD_RESET ->
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "비밀번호가 초기화되었습니다. 로그인 전에 비밀번호를 변경해 주세요.");
            case WITHDRAWN ->
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "탈퇴한 계정입니다.");
            default ->
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "계정 상태 확인 중 서버에서 오류가 발생하였습니다.");
        }
    }

    /**
     * 로그인 실패 처리
     *
     * @param adminUser 로그인 실패한 어드민 사용자 객체
     */
    private void handleFailedLoginAttempt(AdminUser adminUser) {
        // 비밀번호를 틀린 횟수 1회 추가
        adminUser.setFailedLoginAttempts(adminUser.getFailedLoginAttempts() + 1);
        // 비밀번호를 틀린 횟수가 5회 이상일 경우 계정 LOCKED
        if (adminUser.getFailedLoginAttempts() >= 5) {
            adminUser.setAuthenticationStatus(AuthenticationStatus.LOCKED);
            adminUserRepository.save(adminUser);
            throw new ResponseStatusException(HttpStatus.LOCKED, "비밀번호 입력 실패 횟수가 5회를 초과하여 계정이 잠금 처리되었습니다. 비밀번호를 재설정해 주세요.");
        }
        adminUserRepository.saveAndFlush(adminUser);
    }

    /**
     * 어드민 사용자 비밀번호 및 상태 업데이트
     *
     * @param adminUser 어드민 사용자 객체
     * @param newPassword 새 비밀번호
     * @param newStatus 새 상태
     */
    private void updatePasswordAndStatus(AdminUser adminUser, String newPassword, AuthenticationStatus newStatus) {
        adminUser.setPassword(passwordEncoder.encode(newPassword));
        adminUser.setAuthenticationStatus(newStatus);
        adminUser.setFailedLoginAttempts(0);
        adminUserRepository.save(adminUser);
    }

    /**
     * 랜덤 비밀번호 생성
     *
     * @param length 비밀번호 길이
     * @return 랜덤 생성된 비밀번호
     */
    private String generateRandomPassword(int length) {
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
        log.info("**** 초기화된 비밀번호: {}", new String(password));
        return new String(password);
    }
}