package com.redutec.admin.bot.controller;

import com.redutec.admin.bot.dto.BotUserDto;
import com.redutec.admin.bot.service.BotUserService;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자 계정 API 컨트롤러.
 * 이 클래스는 관리자 계정과 관련된 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bot-user")
@Tag(name = "관리자 계정 관리", description = "관리자 계정 API 모음")
public class BotUserController {
    private final ApiResponseManager apiResponseManager;
    private final BotUserService botUserService;

    /**
     * 관리자 계정 조회 API
     * 주어진 조건에 따라 관리자 계정 데이터를 조회하여 반환합니다
     *
     * @return 조건에 맞는 관리자 계정 목록과 페이징 정보를 포함한 응답 객체를 반환합니다
     */
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
                BotUserDto.FindBotUser.builder()
                        .userNoList(userNoList)
                        .userId(userId)
                        .userName(userName)
                        .page(page)
                        .size(size)
                        .build()));
    }
}