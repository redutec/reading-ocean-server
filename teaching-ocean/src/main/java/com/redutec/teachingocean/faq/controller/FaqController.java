package com.redutec.teachingocean.faq.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.FaqDto;
import com.redutec.teachingocean.faq.service.FaqService;
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
@RequestMapping("/faq")
@Tag(name = "이용안내 API", description = "이용안내 API 모음")
public class FaqController {
    private final ApiResponseManager apiResponseManager;
    private final FaqService faqService;

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
}