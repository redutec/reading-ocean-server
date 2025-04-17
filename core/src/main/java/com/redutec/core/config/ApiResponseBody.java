package com.redutec.core.config;

/**
 * API 응답을 표준화하기 위한 record 객체입니다.
 */
public record ApiResponseBody(
    Integer status,
    String message,
    Object data
) {}