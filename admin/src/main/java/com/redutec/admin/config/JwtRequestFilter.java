package com.redutec.admin.config;

import com.redutec.admin.backoffice.dto.BackOfficeUserDto;
import com.redutec.admin.backoffice.mapper.BackOfficeUserMapper;
import com.redutec.admin.backoffice.service.BackOfficeUserService;
import com.redutec.core.entity.v1.BotUser;
import com.redutec.core.repository.v1.BotUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT 요청 필터 클래스.
 * HTTP 요청에서 JWT 토큰을 추출하고 검증하여 Spring Security의 인증 컨텍스트를 설정합니다.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final BackOfficeUserService backOfficeUserService;
    private final BotUserRepository botUserRepository;
    private final BackOfficeUserMapper backOfficeUserMapper;

    @Setter
    private UserDetailsService userDetailsService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    // Swagger UI와 같은 특정 경로는 필터링에서 제외
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/swagger-ui/**", "/api/v3/api-docs/**", "/api/swagger-resources/**", "/api/webjars/**",
            "/api/configuration/**", "/api/auth/**", "/api/academy/list", "/api/branch/list");

    /**
     * HTTP 요청을 필터링하여 JWT 토큰을 검증하고, 인증 정보를 설정합니다.
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param chain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException 입출력 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        // 예외 처리할 URI는 필터링을 하지 않고 체인으로 넘어감
        if (EXCLUDED_PATHS.stream().anyMatch(request.getRequestURI()::startsWith)) {
            chain.doFilter(request, response);
            return;
        }
        // JWT 토큰을 HTTP 요청 헤더에서 추출
        var accessToken = jwtUtil.extractTokenFromRequest(request);
        // 로컬 프로파일인 경우, 토큰이 없으면 샘플 데이터를 사용하여 토큰 생성
        if ("local".equals(activeProfile) && accessToken == null) {
            // 로컬용 임시 어드민 사용자 데이터로 accessToken 발급
            BotUser localBotUser = backOfficeUserService.getBackOfficeUser(1);
            BackOfficeUserDto.BackOfficeUserResponse localBotUserResponse = backOfficeUserMapper.toResponse(localBotUser);
            // BotUserResponse를 클레임으로 전달하여 Access Token 생성
            accessToken = jwtUtil.generateAccessToken(localBotUserResponse);
            log.info("**** Local profile detected with no token. Generated accessToken for test: {}", accessToken);

        }
        if (accessToken != null) {
            // 토큰을 검증
            if (jwtUtil.validateToken(accessToken)) {
                // 검증된 토큰에서 어드민 사용자 아이디 추출
                var userId = jwtUtil.extractUserIdFromToken(accessToken);
                // 어드민 사용자 아이디가 유효하고, 현재 인증이 없다면 사용자 인증 수행
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 로그인한 어드민 사용자 정보 조회
                    BotUser botUser = botUserRepository.findByUserId(userId).orElseThrow(() -> new EntityNotFoundException("No such BotUser"));
                    // 권한 부여 및 인증 객체 생성
                    var authorities = userDetailsService.loadUserByUsername(botUser.getUserId());
                    var authenticationToken = new UsernamePasswordAuthenticationToken(userId, accessToken, authorities.getAuthorities());
                    // SecurityContext에 인증 객체 설정
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 필터 체인 계속 진행
        chain.doFilter(request, response);
    }
}