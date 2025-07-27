package com.redutec.core.config;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

public final class WebRequestUtil {
    private WebRequestUtil() {}

    /**
     * 현재 요청의 클라이언트 IP를 꺼내옵니다.
     */
    public static String extractClientIp() {
        return RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes requestAttributes
                ? Objects.requireNonNullElse(requestAttributes.getRequest().getRemoteAddr(), "unknown")
                : "unknown";
    }

    /**
     * 현재 요청의 User-Agent 헤더 값을 꺼내옵니다.
     */
    public static String extractUserAgent() {
        return RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes requestAttributes
                ? Objects.requireNonNullElse(requestAttributes.getRequest().getHeader("User-Agent"), "unknown")
                : "unknown";
    }
}