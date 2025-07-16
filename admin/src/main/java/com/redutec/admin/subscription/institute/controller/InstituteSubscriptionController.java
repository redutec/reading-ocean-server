package com.redutec.admin.subscription.institute.controller;

import com.redutec.admin.subscription.institute.service.InstituteSubscriptionService;
import com.redutec.core.dto.InstituteSubscriptionDto;
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
@RequestMapping("/subscriptions/institutes")
@Tag(name = "구독(교육기관) 관리 API", description = "구독(교육기관) 관리 API 모음")
public class InstituteSubscriptionController {
    private final ApiResponseManager apiResponseManager;
    private final InstituteSubscriptionService instituteSubscriptionService;

    @Operation(summary = "구독(교육기관) 등록", description = "구독(교육기관) 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid InstituteSubscriptionDto.CreateInstituteSubscriptionRequest createInstituteSubscriptionRequest
    ) {
        return apiResponseManager.create(instituteSubscriptionService.create(createInstituteSubscriptionRequest));
    }

    @Operation(summary = "조건에 맞는 구독(교육기관) 목록 조회", description = "조건에 맞는 구독(교육기관) 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid InstituteSubscriptionDto.FindInstituteSubscriptionRequest findInstituteSubscriptionRequest
    ) {
        return apiResponseManager.ok(instituteSubscriptionService.find(findInstituteSubscriptionRequest));
    }

    @Operation(summary = "특정 구독(교육기관) 조회", description = "특정 구독(교육기관) 정보를 조회하는 API")
    @GetMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponseBody> get(@PathVariable Long subscriptionId) {
        return apiResponseManager.ok(instituteSubscriptionService.get(subscriptionId));
    }

    @Operation(summary = "특정 구독(교육기관) 수정", description = "특정 구독(교육기관) 정보를 수정하는 API")
    @PutMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "구독(교육기관) ID") @PathVariable Long subscriptionId,
            @ParameterObject @Valid InstituteSubscriptionDto.UpdateInstituteSubscriptionRequest updateInstituteSubscriptionRequest
    ) {
        instituteSubscriptionService.update(subscriptionId, updateInstituteSubscriptionRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 구독(교육기관) 삭제", description = "특정 구독(교육기관) 정보를 삭제하는 API")
    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "구독(교육기관) ID") @PathVariable Long subscriptionId) {
        instituteSubscriptionService.delete(subscriptionId);
        return apiResponseManager.noContent();
    }
}