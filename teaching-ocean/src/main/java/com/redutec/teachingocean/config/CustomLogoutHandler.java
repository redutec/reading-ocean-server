package com.redutec.teachingocean.config;

import com.redutec.core.entity.BlacklistedToken;
import com.redutec.core.meta.Domain;
import com.redutec.core.repository.BlacklistedTokenRepository;
import com.redutec.core.repository.RefreshTokenRepository;
import com.redutec.core.repository.TeacherRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 로그아웃 처리를 담당하는 컴포넌트입니다.
 * JWT 토큰을 블랙리스트에 추가하고, 관련된 리프레시 토큰을 삭제합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final JwtUtil jwtUtil;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 로그아웃 처리를 수행합니다. 요청에서 JWT 토큰을 추출하고, 해당 토큰을 블랙리스트에 추가합니다.
     *
     * @param request        JWT 토큰을 추출할 HttpServletRequest
     * @param response       현재 HTTP 응답 객체
     * @param authentication 현재 인증 정보를 담고 있는 Authentication 객체
     */
    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional.ofNullable(jwtUtil.extractTokenFromRequest(request))
                .ifPresent(token -> {
                    // 블랙리스트에 없으면 저장
                    blacklistedTokenRepository.findByToken(token)
                            .orElseGet(() -> blacklistedTokenRepository.save(
                                            BlacklistedToken.builder()
                                                    .token(token)
                                                    .build()
                                    )
                            );
                    // 리프레시 토큰 삭제
                    refreshTokenRepository.deleteByUsernameAndDomain(jwtUtil.extractUsername(token), Domain.TEACHING_OCEAN);
                });
    }
}