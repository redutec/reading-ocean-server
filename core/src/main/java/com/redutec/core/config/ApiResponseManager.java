package com.redutec.core.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * ApiResponseManager는 API 호출의 응답을 관리하는 클래스입니다.
 * 각 메서드는 다양한 상황에 맞는 API 응답을 생성합니다.
 */
@Slf4j
@Component
@AllArgsConstructor
public class ApiResponseManager {
    /**
     * API를 정상 호출한 경우의 응답을 생성합니다.
     *
     * @param data 리턴할 데이터
     * @return 상태 코드와 메시지, 데이터를 포함한 응답 객체
     */
    public ResponseEntity<ApiResponseBody> success(Object data) {
        return buildResponse(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * API 호출 후 기능 작동 중 에러가 발생한 경우의 응답을 생성합니다.
     *
     * @param status  HTTP 상태 코드
     * @param message 에러 메시지
     * @return 상태 코드와 메시지를 포함한 응답 객체
     */
    public ResponseEntity<ApiResponseBody> error(HttpStatus status, String message) {
        return buildResponse(status, message, null);
    }

    /**
     * 공통 응답 빌드를 처리하는 메서드.
     *
     * @param status HTTP 상태 코드
     * @param data   응답에 포함할 데이터
     * @return 상태 코드와 메시지를 포함한 응답 객체
     */
    private ResponseEntity<ApiResponseBody> buildResponse(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status)
                .body(new ApiResponseBody(
                        status.value(),
                        message,
                        data
                     )
                );
    }
}