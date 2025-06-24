package com.redutec.admin.popup.controller;

import com.redutec.admin.popup.service.PopupService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.PopupDto;
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
@RequestMapping("/popup")
@Tag(name = "팝업 관리 API", description = "팝업 관리 API 모음")
public class PopupController {
    private final ApiResponseManager apiResponseManager;
    private final PopupService popupService;

    @Operation(summary = "팝업 등록", description = "팝업 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@ParameterObject @Valid PopupDto.CreatePopupRequest createPopupRequest) {
        return apiResponseManager.create(popupService.create(createPopupRequest));
    }

    @Operation(summary = "조건에 맞는 팝업 목록 조회", description = "조건에 맞는 팝업 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid PopupDto.FindPopupRequest findPopupRequest) {
        return apiResponseManager.ok(popupService.find(findPopupRequest));
    }

    @Operation(summary = "특정 팝업 조회", description = "특정 팝업을 조회하는 API")
    @GetMapping("/{popupId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long popupId) {
        return apiResponseManager.ok(popupService.findById(popupId));
    }

    @Operation(summary = "특정 팝업 수정", description = "특정 팝업을 수정하는 API")
    @PutMapping("/{popupId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "팝업 ID") @PathVariable Long popupId,
            @ParameterObject @Valid PopupDto.UpdatePopupRequest updatePopupRequest
    ) {
        popupService.update(popupId, updatePopupRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 팝업 삭제", description = "특정 팝업을 삭제하는 API")
    @DeleteMapping("/{popupId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "팝업 ID") @PathVariable Long popupId) {
        popupService.delete(popupId);
        return apiResponseManager.noContent();
    }
}