package com.redutec.teachingocean.readingdiagnostic.voucher.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ReadingDiagnosticVoucherDto;
import com.redutec.teachingocean.readingdiagnostic.voucher.service.ReadingDiagnosticVoucherService;
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
@RequestMapping("/reading-diagnostic/vouchers")
@Tag(name = "독서능력진단평가 바우처 관리 API", description = "독서능력진단평가 바우처 관리 API 모음")
public class ReadingDiagnosticVoucherController {
    private final ApiResponseManager apiResponseManager;
    private final ReadingDiagnosticVoucherService readingDiagnosticVoucherService;

    @Operation(summary = "현재 로그인한 교육기관이 보유한 독서능력진단평가 바우처 목록 조회", description = "현재 로그인한 교육기관이 보유한 독서능력진단평가 바우처 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid ReadingDiagnosticVoucherDto.FindReadingDiagnosticVoucherRequest findReadingDiagnosticVoucherRequest
    ) {
        return apiResponseManager.ok(readingDiagnosticVoucherService.find(findReadingDiagnosticVoucherRequest));
    }

    @Operation(summary = "특정 독서능력진단평가 바우처 조회(현재 로그인한 교육기관의 바우처만 조회 가능)", description = "특정 독서능력진단평가 바우처를 조회하는 API")
    @GetMapping("/{readingDiagnosticVoucherId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long readingDiagnosticVoucherId) {
        return apiResponseManager.ok(readingDiagnosticVoucherService.findById(readingDiagnosticVoucherId));
    }
}