package com.redutec.admin.backoffice.controller;

import com.redutec.admin.backoffice.dto.BackOfficeUserDto;
import com.redutec.admin.backoffice.service.BackOfficeUserService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * 관리자 계정 API 컨트롤러.
 * 이 클래스는 관리자 계정과 관련된 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/back-office/user")
@Tag(name = "관리자 계정 관리", description = "관리자 계정 API 모음")
public class BackOfficeUserController {
    private final ApiResponseManager apiResponseManager;
    private final BackOfficeUserService backOfficeUserService;

    @Operation(summary = "관리자 계정 등록", description = "관리자 계정을 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject BackOfficeUserDto.CreateBackOfficeUserRequest createBackOfficeUserRequest
    ) throws NoSuchAlgorithmException {
        return apiResponseManager.success(backOfficeUserService.create(createBackOfficeUserRequest));
    }

    @Operation(summary = "조건에 맞는 관리자 계정 조회", description = "조건에 맞는 관리자 계정 조회 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject BackOfficeUserDto.FindBackOfficeUserRequest findBackOfficeUserRequest
    ) {
        return apiResponseManager.success(backOfficeUserService.find(findBackOfficeUserRequest));
    }
}