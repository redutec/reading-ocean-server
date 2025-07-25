package com.redutec.readingocean.edu.authentication.service;

import com.redutec.core.dto.ReadingOceanEduAuthenticationDto;
import com.redutec.core.dto.StudentDto;
import com.redutec.core.entity.Student;
import jakarta.mail.MessagingException;
import org.springframework.web.server.ResponseStatusException;

/**
 * AuthenticationService는 사용자 인증 및 권한 부여와 관련된 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * 이 인터페이스는 로그인, 사용자 정보 조회, JWT 토큰 블랙리스트 추가, 비밀번호 초기화 및 변경 기능을 제공합니다.
 */
public interface AuthenticationService {
    /**
     * 사용자가 제공한 로그인 정보를 기반으로 로그인을 수행합니다.
     *
     * @param loginRequest 로그인 요청 정보를 포함하는 데이터 전송 객체입니다. 이 객체에는 사용자 아이디와 비밀번호 등이 포함됩니다.
     * @return 로그인 성공 시 반환되는 맵 객체입니다. Access & Refresh Token이 반환됩니다.
     */
    ReadingOceanEduAuthenticationDto.LoginResponse login(ReadingOceanEduAuthenticationDto.LoginRequest loginRequest);

    /**
     * JWT 토큰을 사용하여 현재 로그인된 사용자의 정보를 조회합니다.
     *
     * @return 사용자 정보 객체입니다. 사용자의 아이디, 이름, 권한 등의 정보가 포함됩니다.
     */
    StudentDto.StudentResponse getAuthenticatedStudent();

    /**
     * 사용자의 비밀번호를 초기화합니다.
     *
     * @param resetPasswordRequest 비밀번호 초기화를 위한 데이터 전송 객체입니다. 이 객체에는 초기화할 비밀번호와 관련된 정보가 포함됩니다.
     */
    void resetPassword(ReadingOceanEduAuthenticationDto.ResetPasswordRequest resetPasswordRequest) throws MessagingException;

    /**
     * 사용자의 비밀번호를 변경합니다.
     *
     * @param updatePasswordRequest 비밀번호 변경을 위한 데이터 전송 객체입니다. 이 객체에는 기존 비밀번호와 새 비밀번호가 포함됩니다.
     */
    void updatePassword(ReadingOceanEduAuthenticationDto.UpdatePasswordRequest updatePasswordRequest);

    /**
     * Refresh Token을 사용하여 새로운 Access Token을 발급합니다.
     * 이 메서드는 클라이언트가 제공한 Refresh Token의 유효성을 확인한 후,
     * 해당 사용자의 새로운 Access Token을 생성하여 반환합니다.
     * Refresh Token이 유효하지 않거나 만료된 경우 예외를 발생시킵니다.
     *
     * @param refreshToken 클라이언트에서 제공된 Refresh Token
     * @return 새로운 Access Token을 포함한 Map 객체
     * @throws ResponseStatusException Refresh Token이 유효하지 않거나 만료된 경우 발생
     */
    ReadingOceanEduAuthenticationDto.LoginResponse refreshAccessToken(String refreshToken);

    /**
     * 학생 계정 상태 검증
     *
     * @param student 검증할 학생 엔티티
     */
    void validateAuthenticationStatus(Student student);
}