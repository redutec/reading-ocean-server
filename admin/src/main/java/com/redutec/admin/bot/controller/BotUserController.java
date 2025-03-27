package com.redutec.admin.bot.controller;

import com.redutec.admin.bot.dto.BotUserDto;
import com.redutec.admin.bot.service.BotUserService;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 관리자 계정 API 컨트롤러.
 * 이 클래스는 관리자 계정과 관련된 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bot/user")
@Tag(name = "관리자 계정 관리", description = "관리자 계정 API 모음")
public class BotUserController {
    private final ApiResponseManager apiResponseManager;
    private final BotUserService botUserService;

    @Operation(summary = "관리자 계정 등록", description = "관리자 계정을 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseDto> create(@Valid @RequestBody BotUserDto.CreateBotUserRequest createBotUserRequest) throws NoSuchAlgorithmException {
        return apiResponseManager.success(botUserService.create(createBotUserRequest));
    }

    @Operation(summary = "조건에 맞는 관리자 계정 조회", description = "조건에 맞는 관리자 계정 조회 API")
    @GetMapping
    public ResponseEntity<ApiResponseDto> find(
            @Parameter(description = "관리자 계정 고유번호") @RequestParam(required = false) List<Long> userNoList,
            @Parameter(description = "관리자 계정 아이디") @RequestParam(required = false) String userId,
            @Parameter(description = "관리자 이름") @RequestParam(required = false) String userName,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(required = false) Integer page,
            @Parameter(description = "페이지 당 데이터 개수", example = "30") @RequestParam(required = false) Integer size
    ) {
        return apiResponseManager.success(botUserService.find(
                BotUserDto.FindBotUserRequest.builder()
                        .userNoList(userNoList)
                        .userId(userId)
                        .userName(userName)
                        .page(page)
                        .size(size)
                        .build()));
    }
}