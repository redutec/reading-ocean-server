package com.redutec.readingocean.edu.authentication.service;

import com.redutec.core.dto.ReadingOceanEduAuthenticationDto;
import com.redutec.core.entity.*;
import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.meta.Domain;
import com.redutec.core.repository.ReadingOceanEduMenuRepository;
import com.redutec.core.repository.RefreshTokenRepository;
import com.redutec.core.repository.StudentRepository;
import com.redutec.readingocean.edu.config.JwtUtil;
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
import java.util.List;
import java.util.Optional;

import static com.redutec.core.meta.AuthenticationStatus.PASSWORD_RESET;

/**
 * AuthenticationServiceImpl는 학생 인증 및 권한 관련 로직을 처리하는 서비스 클래스입니다.
 * 학생 로그인, 비밀번호 재설정, 토큰 발급 등의 기능을 제공합니다.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReadingOceanEduMenuRepository readingOceanEduMenuRepository;

    /**
     * 학생 로그인 처리
     *
     * @param loginRequest 로그인 요청 정보를 포함하는 데이터 전송 객체
     * @return Access Token 및 Refresh Token이 담긴 Map 객체
     */
    @Override
    public ReadingOceanEduAuthenticationDto.LoginResponse login(
            ReadingOceanEduAuthenticationDto.LoginRequest loginRequest
    ) {
        // 학생 엔티티 조회
        Student student = studentRepository.findByAccountId(loginRequest.accountId())
                .orElseThrow(() -> new EntityNotFoundException("로그인 아이디 또는 비밀번호를 확인해주세요."));
        // 학생 계정 상태 검증
        validateAuthenticationStatus(student);
        // 비밀번호가 일치하는지 검증
        Optional.of(student)
                .filter(admin -> passwordEncoder.matches(loginRequest.password(), admin.getPassword()))
                .orElseThrow(() -> {
                    handleFailedLoginAttempt(student);
                    return new BadCredentialsException("로그인 아이디 또는 비밀번호를 확인해주세요.");
                });
        // 현재 요청의 IP 주소를 Optional 체인으로 가져옴 (없으면 "unknown")
        String ipAddress = Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(HttpServletRequest::getRemoteAddr)
                .orElse("unknown");
        // 로그인에 성공하면 마지막 로그인 일시와 IP 업데이트 후 실패 횟수 초기화
        student.setLastLoginAt(LocalDateTime.now());
        student.setLastLoginIp(ipAddress);
        student.setFailedLoginAttempts(0);
        studentRepository.save(student);
        // AccessToken 생성
        String accessToken = jwtUtil.generateAccessToken(student, student.getInstitute(), student.getHomeroom());
        // RefreshToken 생성
        String refreshToken = jwtUtil.generateRefreshToken(student, student.getInstitute(), student.getHomeroom());
        // 생성한 RefreshToken을 DB에 저장
        jwtUtil.saveRefreshToken(refreshToken, student.getAccountId(), Domain.TEACHING_OCEAN);
        // 생성한 Token으로 로그인 응답 객체를 리턴
        return new ReadingOceanEduAuthenticationDto.LoginResponse(accessToken, refreshToken);
    }

    /**
     * 현재 로그인한 학생 정보 조회
     *
     * @return 학생 정보가 담긴 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ReadingOceanEduAuthenticationDto.AuthenticatedStudent getAuthenticatedStudent() {
        // 현재 접속한 계정 정보를 가져오기
        var accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        // 학생 엔티티 조회
        Student student = studentRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학생 계정입니다. accountId: " + accountId));
        // 소속 교육기관 정보 조회
        Institute institute = student.getInstitute();
        Long instituteId = Optional.ofNullable(institute)
                .map(Institute::getId)
                .orElse(null);
        String instituteName = Optional.ofNullable(institute)
                .map(Institute::getName)
                .orElse(null);
        // 학급 정보 조회
        Homeroom homeroom = student.getHomeroom();
        Long homeroomId = Optional.ofNullable(homeroom)
                .map(Homeroom::getId)
                .orElse(null);
        String homeroomName = Optional.ofNullable(homeroom)
                .map(Homeroom::getName)
                .orElse(null);
        // 접근 가능한 메뉴 정보 조회
        List<ReadingOceanEduMenu> readingOceanEduMenu = readingOceanEduMenuRepository.findAll();
        // 현재 로그인한 학생 정보를 리턴
        return new ReadingOceanEduAuthenticationDto.AuthenticatedStudent(
                student.getId(),
                accountId,
                student.getName(),
                student.getPhoneNumber(),
                student.getEmail(),
                student.getStatus(),
                student.getAuthenticationStatus(),
                student.getFailedLoginAttempts(),
                instituteId,
                instituteName,
                homeroomId,
                homeroomName
        );
    }

    /**
     * 비밀번호 초기화 처리
     *
     * @param resetPasswordRequest 비밀번호 초기화를 위한 DTO
     */
    @Override
    @Transactional
    public void resetPassword(ReadingOceanEduAuthenticationDto.ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        var student = studentRepository.findByAccountId(resetPasswordRequest.accountId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학생 계정입니다. accountId: " + resetPasswordRequest.accountId()));
        validateAuthenticationStatus(student);
        var newPasswordLength = 8;
        var newPassword = generateRandomPassword(newPasswordLength);
        updatePasswordAndStatus(student, newPassword, PASSWORD_RESET);
        // TODO 메일 또는 알림톡 발송 기능 구현
    }

    /**
     * 비밀번호 변경 처리
     *
     * @param updatePasswordRequest 비밀번호 변경을 위한 DTO
     */
    @Override
    @Transactional
    public void updatePassword(ReadingOceanEduAuthenticationDto.UpdatePasswordRequest updatePasswordRequest) {
        var student = studentRepository.findByAccountId(updatePasswordRequest.accountId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학생 계정입니다. accountId: " + updatePasswordRequest.accountId()));
        if (student.getAuthenticationStatus() != PASSWORD_RESET) {
            validateAuthenticationStatus(student);
        }
        if (!passwordEncoder.matches(updatePasswordRequest.password(), student.getPassword())) {
            throw new BadCredentialsException("로그인 아이디 또는 비밀번호를 확인해주세요");
        }
        updatePasswordAndStatus(student, updatePasswordRequest.newPassword(), AuthenticationStatus.ACTIVE);
    }

    /**
     * Refresh Token을 사용하여 새로운 Access Token을 발급
     *
     * @param refreshToken 리프레시 토큰
     * @return 새로운 Access Token이 담긴 Map 객체
     */
    @Override
    @Transactional
    public ReadingOceanEduAuthenticationDto.LoginResponse refreshAccessToken(String refreshToken) {
        // 새로운 Access Token 발급을 요청한 Refresh Token 조회
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .filter(RefreshToken::isExpired)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않거나 만료된 토큰입니다."));
        // refreshTokenEntity에 있는 학생 닉네임으로 학생 엔티티 조회
        Student student = studentRepository.findByAccountId(refreshTokenEntity.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계정입니다. accountId: " + refreshTokenEntity.getUsername()));
        // 새로운 Access Token 생성 후 리턴
        return new ReadingOceanEduAuthenticationDto.LoginResponse(
                jwtUtil.generateAccessToken(student, student.getInstitute(), student.getHomeroom()),
                refreshToken
        );
    }

    /**
     * 학생 계정 상태 검증
     *
     * @param student 검증할 학생 객체
     */
    public void validateAuthenticationStatus(Student student) {
        switch (student.getAuthenticationStatus()) {
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
     * @param student 로그인 실패한 학생 객체
     */
    private void handleFailedLoginAttempt(Student student) {
        // 비밀번호를 틀린 횟수 1회 추가
        student.setFailedLoginAttempts(student.getFailedLoginAttempts() + 1);
        // 비밀번호를 틀린 횟수가 5회 이상일 경우 계정 LOCKED
        if (student.getFailedLoginAttempts() >= 5) {
            student.setAuthenticationStatus(AuthenticationStatus.LOCKED);
            studentRepository.save(student);
            throw new ResponseStatusException(HttpStatus.LOCKED, "비밀번호 입력 실패 횟수가 5회를 초과하여 계정이 잠금 처리되었습니다. 비밀번호를 재설정해 주세요.");
        }
        studentRepository.saveAndFlush(student);
    }

    /**
     * 학생 비밀번호 및 상태 업데이트
     *
     * @param student 학생 객체
     * @param newPassword 새 비밀번호
     * @param newStatus 새 상태
     */
    private void updatePasswordAndStatus(
            Student student,
            String newPassword,
            AuthenticationStatus newStatus
    ) {
        student.setPassword(passwordEncoder.encode(newPassword));
        student.setAuthenticationStatus(newStatus);
        student.setFailedLoginAttempts(0);
        studentRepository.save(student);
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