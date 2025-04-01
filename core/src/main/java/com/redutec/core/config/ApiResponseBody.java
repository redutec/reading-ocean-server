package com.redutec.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * API 응답을 표준화하기 위한 데이터 전송 객체(DTO)입니다.
 */
@Builder
@Getter
@AllArgsConstructor
public class ApiResponseBody {
    private Integer status;
    private String message;
    private Object data;
}