package com.redutec.admin.account.controller;

import com.redutec.admin.account.dto.AccountDto;
import com.redutec.admin.account.service.AccountService;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 관리 API 컨트롤러.
 * 이 클래스는 회원 관리와 관련된 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Tag(name = "회원 관리 API", description = "회원 관리 API 모음")
public class AccountController {
    private final ApiResponseManager apiResponseManager;
    private final AccountService accountService;

    /**
     * 회원 조회 API
     * 주어진 조건에 따라 회원 데이터를 조회하여 반환합니다
     *
     * @return 조건에 맞는 회원 목록과 페이징 정보를 포함한 응답 객체를 반환합니다
     */
    @Operation(summary = "조건에 맞는 회원 조회", description = "조건에 맞는 회원 조회 API")
    @GetMapping
    public ResponseEntity<ApiResponseDto> find() {
        return apiResponseManager.success(accountService.find(
                AccountDto.FindAccountRequest.builder()
                        .build()));
    }
}
