package com.redutec.admin.subscription.institute.controller;

import com.redutec.admin.subscription.institute.service.SubscriptionInstituteService;
import com.redutec.admin.subscription.institute.dto.SubscriptionInstituteDto;
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
@RequestMapping("/subscription/institute")
@Tag(name = "구독(교육기관) 관리 API", description = "구독(교육기관) 관리 API 모음")
public class SubscriptionInstituteController {
    private final ApiResponseManager apiResponseManager;
    private final SubscriptionInstituteService subscriptionInstituteService;

    @Operation(summary = "구독(교육기관) 등록", description = "구독(교육기관) 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@ParameterObject @Valid SubscriptionInstituteDto.CreateSubscriptionInstituteRequest createSubscriptionInstituteRequest) {
        return apiResponseManager.create(subscriptionInstituteService.create(createSubscriptionInstituteRequest));
    }

    @Operation(summary = "조건에 맞는 구독(교육기관) 목록 조회", description = "조건에 맞는 구독(교육기관) 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid SubscriptionInstituteDto.FindSubscriptionInstituteRequest findSubscriptionInstituteRequest) {
        return apiResponseManager.ok(subscriptionInstituteService.find(findSubscriptionInstituteRequest));
    }

    @Operation(summary = "특정 구독(교육기관) 조회", description = "특정 구독(교육기관) 정보를 조회하는 API")
    @GetMapping("/{subscriptionInstituteId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long subscriptionInstituteId) {
        return apiResponseManager.ok(subscriptionInstituteService.findById(subscriptionInstituteId));
    }

    @Operation(summary = "특정 구독(교육기관) 수정", description = "특정 구독(교육기관) 정보를 수정하는 API")
    @PatchMapping("/{subscriptionInstituteId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "구독(교육기관) ID") @PathVariable Long subscriptionInstituteId,
            @ParameterObject @Valid SubscriptionInstituteDto.UpdateSubscriptionInstituteRequest updateSubscriptionInstituteRequest
    ) {
        subscriptionInstituteService.update(subscriptionInstituteId, updateSubscriptionInstituteRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 구독(교육기관) 삭제", description = "특정 구독(교육기관) 정보를 삭제하는 API")
    @DeleteMapping("/{subscriptionInstituteId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "구독(교육기관) ID") @PathVariable Long subscriptionInstituteId) {
        subscriptionInstituteService.delete(subscriptionInstituteId);
        return apiResponseManager.noContent();
    }
}