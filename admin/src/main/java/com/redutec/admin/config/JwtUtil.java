package com.redutec.admin.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redutec.admin.authentication.dto.AuthenticationDto;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.AdminMenu;
import com.redutec.core.entity.RefreshToken;
import com.redutec.core.meta.Domain;
import com.redutec.core.repository.AdminMenuRepository;
import com.redutec.core.repository.AdminUserRepository;
import com.redutec.core.repository.RefreshTokenRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * JWT 토큰을 생성하고 검증하는 유틸리티 클래스입니다.
 * Access Token과 Refresh Token을 생성할 수 있으며, 토큰의 유효성을 확인하는 기능을 제공합니다.
 */
@Slf4j
@Component
public class JwtUtil {
    private final AdminUserRepository adminUserRepository;
    private final AdminMenuRepository adminMenuRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private Key key;

    /**
     * JwtUtil 생성자.
     * Secret Key를 생성하고 HMAC-SHA256 알고리즘을 사용합니다.
     */
    public JwtUtil(
            AdminUserRepository adminUserRepository,
            AdminMenuRepository adminMenuRepository,
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.adminUserRepository = adminUserRepository;
        this.adminMenuRepository = adminMenuRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Refresh Token의 만료 기간을 반환합니다.
     *
     * @return Refresh Token 만료 기간 (밀리초)
     */
    public long getRefreshTokenExpirationInMillis() {
        return refreshTokenExpiration;
    }

    /**
     * 어드민 사용자 정보를 JWT Claims로 변환
     *
     * @param adminUser 어드민 사용자 객체
     * @return JWT Claims 맵 객체
     */
    @Transactional(readOnly = true)
    protected AuthenticationDto.AuthenticatedAdminUser buildJwtClaims(AdminUser adminUser) {
        // 현재 접속한 어드민 사용자가 접근할 수 있는 메뉴 목록 조회
        List<Long> accessibleMenus = adminMenuRepository.findAllByAccessibleRolesContains(adminUser.getRole()).stream()
                .map(AdminMenu::getId)
                .toList();
        // 현재 로그인한 어드민 사용자의 정보를 JWT Claims 응답 객체로 변환하여 리턴
        return new AuthenticationDto.AuthenticatedAdminUser(
                adminUser.getId(),
                adminUser.getAccountId(),
                adminUser.getEmail(),
                adminUser.getNickname(),
                accessibleMenus,
                adminUser.getRole()
        );
    }

    /**
     * Access Token을 생성합니다.
     *
     * @param adminUser Access Token을 발급할 어드민 사용자 객체
     * @return 생성된 Access Token
     */
    @Transactional(readOnly = true)
    public String generateAccessToken(AdminUser adminUser) {
        // 어드민 사용자 엔티티를 JWT Claims Map으로 변환
        Map<String, Object> claims = new ObjectMapper().convertValue(buildJwtClaims(adminUser), new TypeReference<>() {});
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.get("accountId").toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(key)
                .compact();
    }

    /**
     * Refresh Token을 생성합니다.
     *
     * @param adminUser Refresh Token을 발급할 어드민 사용자 객체
     * @return 생성된 Refresh Token
     */
    @Transactional(readOnly = true)
    public String generateRefreshToken(
            AdminUser adminUser
    ) {
        Map<String, Object> claims = new ObjectMapper().convertValue(buildJwtClaims(adminUser), new TypeReference<>() {});
        return Jwts.builder()
                .setSubject(claims.get("accountId").toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(key)
                .compact();
    }

    /**
     * Refresh Token을 저장합니다.
     *
     * @param refreshToken Refresh Token
     * @param username 계정 로그인 아이디
     */
    public void saveRefreshToken(String refreshToken, String username, Domain domain) {
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .username(username)
                .domain(domain)
                .expiresAt(LocalDateTime.now().plus(Duration.ofMillis(getRefreshTokenExpirationInMillis())))
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    /**
     * 토큰에서 현재 로그인한 사용자의 로그인 계정을 추출합니다.
     *
     * @param token JWT 토큰
     * @return 토큰에서 추출된 어드민 사용자 로그인 계정
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Access Token 또는 Refresh Token의 유효성을 검증합니다.
     *
     * @param token JWT 토큰
     * @return 토큰이 유효하면 true, 그렇지 않으면 false
     */
    public boolean validateToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // 토큰이 만료되지 않았는지 확인
            return !claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * HttpServletRequest에서 토큰 정보를 가져옵니다.
     *
     * @param request HttpServletRequest 정보
     * @return JWT 토큰
     */
    public String extractTokenFromRequest(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // "Bearer " 부분을 제거하고 실제 토큰을 반환
        }
        return null;
    }
}