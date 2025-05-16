package com.redutec.admin.inquiry.controller;

import com.redutec.core.dto.InquiryDto;
import com.redutec.admin.inquiry.service.InquiryService;
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
@RequestMapping("/inquiry")
@Tag(name = "고객문의 관리 API", description = "고객문의 관리 API 모음")
public class InquiryController {
    private final ApiResponseManager apiResponseManager;
    private final InquiryService inquiryService;

    @Operation(summary = "고객문의 등록", description = "고객문의 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@ParameterObject @Valid InquiryDto.CreateInquiryRequest createInquiryRequest) {
        return apiResponseManager.create(inquiryService.create(createInquiryRequest));
    }

    @Operation(summary = "조건에 맞는 고객문의 목록 조회", description = "조건에 맞는 고객문의 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid InquiryDto.FindInquiryRequest findInquiryRequest) {
        return apiResponseManager.ok(inquiryService.find(findInquiryRequest));
    }

    @Operation(summary = "특정 고객문의 조회", description = "특정 고객문의을 조회하는 API")
    @GetMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long inquiryId) {
        return apiResponseManager.ok(inquiryService.findById(inquiryId));
    }

    @Operation(summary = "특정 고객문의 수정", description = "특정 고객문의을 수정하는 API")
    @PatchMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "고객문의 ID") @PathVariable Long inquiryId,
            @ParameterObject @Valid InquiryDto.UpdateInquiryRequest updateInquiryRequest
    ) {
        inquiryService.update(inquiryId, updateInquiryRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 고객문의 삭제", description = "특정 고객문의을 삭제하는 API")
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "고객문의 ID") @PathVariable Long inquiryId) {
        inquiryService.delete(inquiryId);
        return apiResponseManager.noContent();
    }
}