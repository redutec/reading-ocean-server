package com.redutec.admin.subscription.plan.controller;

import com.redutec.core.dto.SubscriptionPlanDto;
import com.redutec.admin.subscription.plan.service.SubscriptionPlanService;
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
@RequestMapping("/subscription/plan")
@Tag(name = "구독 상품 관리 API", description = "구독 상품 관리 API 모음")
public class SubscriptionPlanController {
    private final ApiResponseManager apiResponseManager;
    private final SubscriptionPlanService subscriptionPlanService;

    @Operation(summary = "구독 상품 등록", description = "구독 상품 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@ParameterObject @Valid SubscriptionPlanDto.CreateSubscriptionPlanRequest createSubscriptionPlanRequest) {
        return apiResponseManager.create(subscriptionPlanService.create(createSubscriptionPlanRequest));
    }

    @Operation(summary = "조건에 맞는 구독 상품 목록 조회", description = "조건에 맞는 구독 상품 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid SubscriptionPlanDto.FindSubscriptionPlanRequest findSubscriptionPlanRequest) {
        return apiResponseManager.ok(subscriptionPlanService.find(findSubscriptionPlanRequest));
    }

    @Operation(summary = "특정 구독 상품 조회", description = "특정 구독 상품를 조회하는 API")
    @GetMapping("/{subscriptionPlanId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long subscriptionPlanId) {
        return apiResponseManager.ok(subscriptionPlanService.findById(subscriptionPlanId));
    }

    @Operation(summary = "특정 구독 상품 수정", description = "특정 구독 상품를 수정하는 API")
    @PatchMapping("/{subscriptionPlanId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "구독 상품 ID") @PathVariable Long subscriptionPlanId,
            @ParameterObject @Valid SubscriptionPlanDto.UpdateSubscriptionPlanRequest updateSubscriptionPlanRequest
    ) {
        subscriptionPlanService.update(subscriptionPlanId, updateSubscriptionPlanRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 구독 상품 삭제", description = "특정 구독 상품를 삭제하는 API")
    @DeleteMapping("/{subscriptionPlanId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "구독 상품 ID") @PathVariable Long subscriptionPlanId) {
        subscriptionPlanService.delete(subscriptionPlanId);
        return apiResponseManager.noContent();
    }
}