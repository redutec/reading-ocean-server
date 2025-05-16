package com.redutec.teachingocean.notice.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.teachingocean.notice.dto.NoticeDto;
import com.redutec.teachingocean.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Tag(name = "공지사항 API", description = "공지사항 API 모음")
public class NoticeController {
    private final ApiResponseManager apiResponseManager;
    private final NoticeService noticeService;

    @Operation(summary = "조건에 맞는 공지사항 목록 조회", description = "조건에 맞는 공지사항 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid NoticeDto.FindNoticeRequest findNoticeRequest) {
        return apiResponseManager.ok(noticeService.find(findNoticeRequest));
    }

    @Operation(summary = "특정 공지사항 조회", description = "특정 공지사항을 조회하는 API")
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long noticeId) {
        return apiResponseManager.ok(noticeService.findById(noticeId));
    }
}