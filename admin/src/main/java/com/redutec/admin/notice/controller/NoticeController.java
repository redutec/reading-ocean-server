package com.redutec.admin.notice.controller;

import com.redutec.core.dto.NoticeDto;
import com.redutec.admin.notice.service.NoticeService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Tag(name = "공지사항 관리 API", description = "공지사항 관리 API 모음")
public class NoticeController {
    private final ApiResponseManager apiResponseManager;
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 등록", description = "공지사항 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@ParameterObject @Valid NoticeDto.CreateNoticeRequest createNoticeRequest) {
        return apiResponseManager.create(noticeService.create(createNoticeRequest));
    }

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

    @Operation(summary = "특정 공지사항 수정", description = "특정 공지사항을 수정하는 API")
    @PatchMapping("/{noticeId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "공지사항 ID") @PathVariable Long noticeId,
            @ParameterObject @Valid NoticeDto.UpdateNoticeRequest updateNoticeRequest
    ) {
        noticeService.update(noticeId, updateNoticeRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 공지사항 삭제", description = "특정 공지사항을 삭제하는 API")
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "공지사항 ID") @PathVariable Long noticeId) {
        noticeService.delete(noticeId);
        return apiResponseManager.noContent();
    }
}