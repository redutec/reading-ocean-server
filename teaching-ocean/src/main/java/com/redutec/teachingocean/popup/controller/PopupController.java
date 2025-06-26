package com.redutec.teachingocean.popup.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.PopupDto;
import com.redutec.teachingocean.popup.service.PopupService;
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
@RequestMapping("/popups")
@Tag(name = "팝업 관리 API", description = "팝업 관리 API 모음")
public class PopupController {
    private final ApiResponseManager apiResponseManager;
    private final PopupService popupService;

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
}