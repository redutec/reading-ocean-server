package com.redutec.core.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {
        MDC.put("requestId", UUID.randomUUID().toString());
        long start = System.currentTimeMillis();
        var uri = request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        var ip = Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .filter(s -> !s.isBlank())
                .orElse(request.getRemoteAddr());
        var method = request.getMethod();
        log.info("|| Request || [{}] {} from IP: {}", method, uri, ip);
        try {
            chain.doFilter(request, response);
        } finally {
            log.info(":: Response :: [{}] {} with status {} in {} ms",
                    method, uri, response.getStatus(), System.currentTimeMillis() - start);
            MDC.clear();
        }
    }
}