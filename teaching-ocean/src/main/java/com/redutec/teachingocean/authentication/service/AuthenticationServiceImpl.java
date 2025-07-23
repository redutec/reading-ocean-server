package com.redutec.teachingocean.authentication.service;

import com.redutec.core.dto.TeacherDto;
import com.redutec.core.dto.TeachingOceanAuthenticationDto;
import com.redutec.core.entity.RefreshToken;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.TeacherMapper;
import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.meta.Domain;
import com.redutec.core.repository.RefreshTokenRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.repository.TeachingOceanMenuRepository;
import com.redutec.teachingocean.config.JwtUtil;
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
 * AuthenticationServiceImpl는 교사 인증 및 권한 관련 로직을 처리하는 서비스 클래스입니다.
 * 교사 로그인, 비밀번호 재설정, 토큰 발급 등의 기능을 제공합니다.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final TeacherRepository teacherRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeachingOceanMenuRepository teachingOceanMenuRepository;
    private final TeacherMapper teacherMapper;

    /**
     * 교사 로그인 처리
     *
     * @param loginRequest 로그인 요청 정보를 포함하는 데이터 전송 객체
     * @return Access Token 및 Refresh Token이 담긴 Map 객체
     */
    @Override
    @Transactional
    public TeachingOceanAuthenticationDto.LoginResponse login(TeachingOceanAuthenticationDto.LoginRequest loginRequest) {
        // 교사 엔티티 조회
        Teacher teacher = teacherRepository.findByAccountId(loginRequest.accountId())
                .orElseThrow(() -> new EntityNotFoundException("로그인 아이디 또는 비밀번호를 확인해주세요."));
        // 교사 계정 상태 검증
        validateAuthenticationStatus(teacher);
        // 비밀번호가 일치하는지 검증
        Optional.of(teacher)
                .filter(admin -> passwordEncoder.matches(loginRequest.password(), admin.getPassword()))
                .orElseThrow(() -> {
                    handleFailedLoginAttempt(teacher);
                    return new BadCredentialsException("로그인 아이디 또는 비밀번호를 확인해주세요.");
                });
        // 현재 요청의 IP 주소를 Optional 체인으로 가져옴 (없으면 "unknown")
        String ipAddress = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(HttpServletRequest::getRemoteAddr)
                .orElse("unknown");
        // 로그인에 성공하면 마지막 로그인 일시와 IP 업데이트 후 실패 횟수 초기화
        teacher.setLastLoginAt(LocalDateTime.now());
        teacher.setLastLoginIp(ipAddress);
        teacher.setFailedLoginAttempts(0);
        teacherRepository.save(teacher);
        // AccessToken 생성
        String accessToken = jwtUtil.generateAccessToken(teacher);
        // RefreshToken 생성
        String refreshToken = jwtUtil.generateRefreshToken(teacher);
        // 생성한 RefreshToken을 DB에 저장
        jwtUtil.saveRefreshToken(refreshToken, teacher.getAccountId(), Domain.TEACHING_OCEAN);
        // 생성한 Token으로 로그인 응답 객체를 리턴
        return new TeachingOceanAuthenticationDto.LoginResponse(accessToken, refreshToken);
    }

    /**
     * 현재 로그인한 교사 정보 조회
     *
     * @return 교사 정보가 담긴 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public TeacherDto.TeacherResponse getAuthenticatedTeacher() {
        // 현재 접속한 계정 정보를 가져오기
        var accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        // 교사 정보 조회
        Teacher teacher = teacherRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교사 계정입니다. accountId: " + accountId));
        // 현재 로그인한 교사 정보를 리턴
        return teacherMapper.toResponseDto(teacher);
    }

    /**
     * 비밀번호 초기화 처리
     *
     * @param resetPasswordRequest 비밀번호 초기화를 위한 DTO
     */
    @Override
    @Transactional
    public void resetPassword(TeachingOceanAuthenticationDto.ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        var teacher = teacherRepository.findByAccountId(resetPasswordRequest.accountId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교사 계정입니다. accountId: " + resetPasswordRequest.accountId()));
        validateAuthenticationStatus(teacher);
        var newPasswordLength = 8;
        var newPassword = generateRandomPassword(newPasswordLength);
        updatePasswordAndStatus(teacher, newPassword, PASSWORD_RESET);
        // TODO 메일 또는 알림톡 발송 기능 구현
    }

    /**
     * 비밀번호 변경 처리
     *
     * @param updatePasswordRequest 비밀번호 변경을 위한 DTO
     */
    @Override
    @Transactional
    public void updatePassword(TeachingOceanAuthenticationDto.UpdatePasswordRequest updatePasswordRequest) {
        var teacher = teacherRepository.findByAccountId(updatePasswordRequest.accountId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교사 계정입니다. accountId: " + updatePasswordRequest.accountId()));
        if (teacher.getAuthenticationStatus() != PASSWORD_RESET) {
            validateAuthenticationStatus(teacher);
        }
        if (!passwordEncoder.matches(updatePasswordRequest.password(), teacher.getPassword())) {
            throw new BadCredentialsException("로그인 아이디 또는 비밀번호를 확인해주세요");
        }
        updatePasswordAndStatus(teacher, updatePasswordRequest.newPassword(), AuthenticationStatus.ACTIVE);
    }

    /**
     * Refresh Token을 사용하여 새로운 Access Token을 발급
     *
     * @param refreshToken 리프레시 토큰
     * @return 새로운 Access Token이 담긴 Map 객체
     */
    @Override
    @Transactional
    public TeachingOceanAuthenticationDto.LoginResponse refreshAccessToken(String refreshToken) {
        // 새로운 Access Token 발급을 요청한 Refresh Token 조회
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .filter(RefreshToken::isExpired)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않거나 만료된 토큰입니다."));
        // refreshTokenEntity에 있는 교사 닉네임으로 교사 엔티티 조회
        Teacher teacher = teacherRepository.findByAccountId(refreshTokenEntity.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계정입니다. accountId: " + refreshTokenEntity.getUsername()));
        // 새로운 Access Token 생성 후 리턴
        return new TeachingOceanAuthenticationDto.LoginResponse(
                jwtUtil.generateAccessToken(teacher),
                refreshToken
        );
    }

    /**
     * 교사 계정 상태 검증
     *
     * @param teacher 검증할 교사 객체
     */
    public void validateAuthenticationStatus(Teacher teacher) {
        switch (teacher.getAuthenticationStatus()) {
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
     * @param teacher 로그인 실패한 교사 객체
     */
    private void handleFailedLoginAttempt(Teacher teacher) {
        // 비밀번호를 틀린 횟수 1회 추가
        teacher.setFailedLoginAttempts(teacher.getFailedLoginAttempts() + 1);
        // 비밀번호를 틀린 횟수가 5회 이상일 경우 계정 LOCKED
        if (teacher.getFailedLoginAttempts() >= 5) {
            teacher.setAuthenticationStatus(AuthenticationStatus.LOCKED);
            teacherRepository.save(teacher);
            throw new ResponseStatusException(HttpStatus.LOCKED, "비밀번호 입력 실패 횟수가 5회를 초과하여 계정이 잠금 처리되었습니다. 비밀번호를 재설정해 주세요.");
        }
        teacherRepository.saveAndFlush(teacher);
    }

    /**
     * 교사 비밀번호 및 상태 업데이트
     *
     * @param teacher 교사 객체
     * @param newPassword 새 비밀번호
     * @param newStatus 새 상태
     */
    private void updatePasswordAndStatus(Teacher teacher, String newPassword, AuthenticationStatus newStatus) {
        teacher.setPassword(passwordEncoder.encode(newPassword));
        teacher.setAuthenticationStatus(newStatus);
        teacher.setFailedLoginAttempts(0);
        teacherRepository.save(teacher);
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