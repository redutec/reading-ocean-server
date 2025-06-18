package com.redutec.admin.reading.diagnostic.voucher.controller;

import com.redutec.admin.reading.diagnostic.voucher.service.ReadingDiagnosticVoucherService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ReadingDiagnosticVoucherDto;
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
@RequestMapping("/reading-diagnostic/voucher")
@Tag(name = "독서능력진단평가 바우처 관리 API", description = "독서능력진단평가 바우처 관리 API 모음")
public class ReadingDiagnosticVoucherController {
    private final ApiResponseManager apiResponseManager;
    private final ReadingDiagnosticVoucherService readingDiagnosticVoucherService;

    @Operation(summary = "독서능력진단평가 바우처 등록", description = "독서능력진단평가 바우처를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid ReadingDiagnosticVoucherDto.CreateReadingDiagnosticVoucherRequest createReadingDiagnosticVoucherRequest
    ) {
        return apiResponseManager.create(readingDiagnosticVoucherService.create(createReadingDiagnosticVoucherRequest));
    }

    @Operation(summary = "조건에 맞는 독서능력진단평가 바우처 목록 조회", description = "조건에 맞는 독서능력진단평가 바우처 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid ReadingDiagnosticVoucherDto.FindReadingDiagnosticVoucherRequest findReadingDiagnosticVoucherRequest
    ) {
        return apiResponseManager.ok(readingDiagnosticVoucherService.find(findReadingDiagnosticVoucherRequest));
    }

    @Operation(summary = "특정 독서능력진단평가 바우처 조회", description = "특정 독서능력진단평가 바우처를 조회하는 API")
    @GetMapping("/{readingDiagnosticVoucherId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long readingDiagnosticVoucherId) {
        return apiResponseManager.ok(readingDiagnosticVoucherService.findById(readingDiagnosticVoucherId));
    }

    @Operation(summary = "특정 독서능력진단평가 바우처 수정", description = "특정 독서능력진단평가 바우처를 수정하는 API")
    @PatchMapping("/{readingDiagnosticVoucherId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "독서능력진단평가 바우처 ID") @PathVariable Long readingDiagnosticVoucherId,
            @ParameterObject @Valid ReadingDiagnosticVoucherDto.UpdateReadingDiagnosticVoucherRequest updateReadingDiagnosticVoucherRequest
    ) {
        readingDiagnosticVoucherService.update(readingDiagnosticVoucherId, updateReadingDiagnosticVoucherRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 독서능력진단평가 바우처 삭제", description = "특정 독서능력진단평가 바우처를 삭제하는 API")
    @DeleteMapping("/{readingDiagnosticVoucherId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "독서능력진단평가 바우처 ID") @PathVariable Long readingDiagnosticVoucherId
    ) {
        readingDiagnosticVoucherService.delete(readingDiagnosticVoucherId);
        return apiResponseManager.noContent();
    }
}