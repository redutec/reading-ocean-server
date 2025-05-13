package com.redutec.admin.config;

import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.BlacklistedToken;
import com.redutec.core.meta.Domain;
import com.redutec.core.repository.AdminUserRepository;
import com.redutec.core.repository.BlacklistedTokenRepository;
import com.redutec.core.repository.RefreshTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final AdminUserRepository adminUserRepository;

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
        // HttpServletRequest에서 토큰을 가져온다.
        var token = jwtUtil.extractTokenFromRequest(request);
        if (token != null) {
            // 블랙리스트에 이미 존재하는지 확인
            boolean alreadyBlacklisted = blacklistedTokenRepository.existsByToken(token);
            if (!alreadyBlacklisted) {
                // JWT 토큰을 블랙리스트에 추가
                blacklistedTokenRepository.save(
                        BlacklistedToken.builder()
                                .token(token)
                                .build());
            }
            // JWT 토큰에서 시스템 사용자의 로그인 아이디를 추출
            String accountId = jwtUtil.extractUsername(token);
            if (accountId != null) {
                // AdminUser 엔티티를 시스템 사용자의 로그인 아이디로 조회
                AdminUser adminUser = adminUserRepository.findByAccountId(accountId)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 어드민 사용자입니다. accountId = " + accountId));
                // 해당 시스템 사용자의 리프레시 토큰 삭제
                refreshTokenRepository.deleteByUsernameAndDomain(adminUser.getAccountId(), Domain.ADMIN);
            }
        }
    }
}