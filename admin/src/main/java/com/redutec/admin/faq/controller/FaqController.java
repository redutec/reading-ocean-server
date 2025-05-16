package com.redutec.admin.faq.controller;

import com.redutec.core.dto.FaqDto;
import com.redutec.admin.faq.service.FaqService;
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
@RequestMapping("/faq")
@Tag(name = "이용안내 관리 API", description = "이용안내 관리 API 모음")
public class FaqController {
    private final ApiResponseManager apiResponseManager;
    private final FaqService faqService;

    @Operation(summary = "이용안내 등록", description = "이용안내 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@ParameterObject @Valid FaqDto.CreateFaqRequest createFaqRequest) {
        return apiResponseManager.create(faqService.create(createFaqRequest));
    }

    @Operation(summary = "조건에 맞는 이용안내 목록 조회", description = "조건에 맞는 이용안내 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid FaqDto.FindFaqRequest findFaqRequest) {
        return apiResponseManager.ok(faqService.find(findFaqRequest));
    }

    @Operation(summary = "특정 이용안내 조회", description = "특정 이용안내를 조회하는 API")
    @GetMapping("/{faqId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long faqId) {
        return apiResponseManager.ok(faqService.findById(faqId));
    }

    @Operation(summary = "특정 이용안내 수정", description = "특정 이용안내를 수정하는 API")
    @PatchMapping("/{faqId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "이용안내 ID") @PathVariable Long faqId,
            @ParameterObject @Valid FaqDto.UpdateFaqRequest updateFaqRequest
    ) {
        faqService.update(faqId, updateFaqRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 이용안내 삭제", description = "특정 이용안내를 삭제하는 API")
    @DeleteMapping("/{faqId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "이용안내 ID") @PathVariable Long faqId) {
        faqService.delete(faqId);
        return apiResponseManager.noContent();
    }
}