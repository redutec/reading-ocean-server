package com.redutec.readingocean.edu.config;

import com.redutec.core.entity.Student;
import com.redutec.core.meta.SampleData;
import com.redutec.core.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    @Setter
    private UserDetailsService userDetailsService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    // Swagger UI와 같은 특정 경로는 필터링에서 제외
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/configuration/**",
            "/auth/**"
    );

    /**
     * HTTP 요청을 필터링하여 JWT 토큰을 검증하고, 인증 정보를 설정합니다.
     * @param request HTTP 요청
     * @param response HTTP 응답
     * @param chain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException 입출력 예외
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        // 예외 처리할 URI는 필터링을 하지 않고 체인으로 넘어감
        if (EXCLUDED_PATHS.stream().anyMatch(request.getRequestURI()::startsWith)) {
            chain.doFilter(request, response);
            return;
        }
        // JWT 토큰을 HTTP 요청 헤더에서 추출
        var accessToken = jwtUtil.extractTokenFromRequest(request);
        // 로컬 프로파일인 경우, 토큰이 없으면 샘플 데이터를 사용하여 토큰 생성
        if ("local".equals(activeProfile) && accessToken == null) {
            // 로컬용 임시 학생 계정 데이터로 accessToken 발급
            Student testStudent = studentRepository.findByAccountId(SampleData.SampleStudent.SEONGNAM_STUDENT_1.getAccountId())
                    .orElseThrow(EntityNotFoundException::new);
            accessToken = jwtUtil.generateAccessToken(testStudent);
            log.info("**** Local profile detected with no token. Generated accessToken for test: {}", accessToken);
        }
        if (accessToken != null) {
            // 토큰을 검증
            if (jwtUtil.validateToken(accessToken)) {
                // 검증된 토큰에서 학생 계정 아이디 추출
                var accountId = jwtUtil.extractUsername(accessToken);
                // 학생 계정 아이디가 유효하고, 현재 인증이 없다면 사용자 인증 수행
                if (accountId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // 로그인한 학생 계정 정보 조회
                    Student student = studentRepository.findByAccountId(accountId)
                            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학생 계정입니다. accountId: " + accountId));
                    // 권한 부여 및 인증 객체 생성
                    var authorities = userDetailsService.loadUserByUsername(student.getAccountId());
                    var authenticationToken = new UsernamePasswordAuthenticationToken(authorities, accessToken, authorities.getAuthorities());
                    // SecurityContext에 인증 객체 설정
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 필터 체인 계속 진행
        chain.doFilter(request, response);
    }
}