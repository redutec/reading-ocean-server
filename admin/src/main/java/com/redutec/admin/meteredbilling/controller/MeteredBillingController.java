package com.redutec.admin.meteredbilling.controller;

import com.redutec.admin.meteredbilling.service.MeteredBillingService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.MeteredBillingDto;
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
@RequestMapping("/metered-billings")
@Tag(name = "월별 사용료 청구서 관리 API", description = "월별 사용료 청구서 관리 API 모음")
public class MeteredBillingController {
    private final ApiResponseManager apiResponseManager;
    private final MeteredBillingService meteredBillingService;

    @Operation(summary = "조건에 맞는 월별 사용료 청구서 목록 조회", description = "조건에 맞는 월별 사용료 청구서 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid MeteredBillingDto.FindMeteredBillingRequest findMeteredBillingRequest) {
        return apiResponseManager.ok(meteredBillingService.find(findMeteredBillingRequest));
    }

    @Operation(summary = "특정 월별 사용료 청구서 조회", description = "특정 월별 사용료 청구서를 조회하는 API")
    @GetMapping("/{meteredBillingId}")
    public ResponseEntity<ApiResponseBody> get(@PathVariable Long meteredBillingId) {
        return apiResponseManager.ok(meteredBillingService.get(meteredBillingId));
    }
}