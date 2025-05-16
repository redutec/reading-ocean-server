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
     * 리소스가 성공적으로 생성되었을 때 사용할 응답을 생성합니다.
     * HTTP 상태 코드는 201 Created이며, 가능하면 Location 헤더에 생성된 리소스 URI를 포함해야 합니다.
     *
     * @param data 생성된 리소스의 표현(보통 DTO)
     * @return 201 Created 상태와 메시지, 생성된 데이터가 담긴 ResponseEntity<ApiResponseBody>
     */
    public ResponseEntity<ApiResponseBody> create(Object data) {
        return buildResponse(HttpStatus.CREATED, HttpStatus.CREATED.getReasonPhrase(), data);
    }

    /**
     * 리소스를 조회했을 때 사용할 응답을 생성합니다.
     * HTTP 상태 코드는 200 OK이며, 응답 바디에 조회된 리소스 표현(보통 DTO)을 포함합니다.
     *
     * @param data 조회된 리소스의 표현(보통 DTO)
     * @return 200 OK 상태와 메시지, 조회된 데이터가 담긴 ResponseEntity<ApiResponseBody>
     */
    public ResponseEntity<ApiResponseBody> ok(Object data) {
        return buildResponse(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 리소스 업데이트 또는 삭제 성공 시 응답을 생성합니다.
     * HTTP 상태 코드는 204 No Content이며, 본문 없이 반환합니다.
     *
     * @return 204 No Content 상태의 ResponseEntity<Void>
     */
    public ResponseEntity<ApiResponseBody> noContent() {
        return buildResponse(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.getReasonPhrase(), null);
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
     * @param status  HTTP 상태 코드
     * @param message 응답 메시지
     * @param data    응답에 포함할 데이터
     * @return 상태 코드와 메시지를 포함한 응답 객체
     */
    private ResponseEntity<ApiResponseBody> buildResponse(HttpStatus status, String message, Object data) {
        return ResponseEntity
                .status(status)
                .body(new ApiResponseBody(status.value(), message, data));
    }
}