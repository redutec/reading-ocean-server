package com.redutec.admin.policy.controller;

import com.redutec.admin.policy.service.PolicyService;
import com.redutec.core.dto.PolicyDto;
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
@RequestMapping("/policy")
@Tag(name = "정책 관리 API", description = "정책 관리 API 모음")
public class PolicyController {
    private final ApiResponseManager apiResponseManager;
    private final PolicyService policyService;

    @Operation(summary = "정책 등록", description = "정책 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid PolicyDto.CreatePolicyRequest createPolicyRequest
    ) {
        return apiResponseManager.create(policyService.create(createPolicyRequest));
    }

    @Operation(summary = "조건에 맞는 정책 목록 조회", description = "조건에 맞는 정책 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid PolicyDto.FindPolicyRequest findPolicyRequest
    ) {
        return apiResponseManager.ok(policyService.find(findPolicyRequest));
    }

    @Operation(summary = "특정 정책 조회", description = "특정 정책을 조회하는 API")
    @GetMapping("/{policyId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long policyId) {
        return apiResponseManager.ok(policyService.findById(policyId));
    }

    @Operation(summary = "특정 정책 수정", description = "특정 정책을 수정하는 API")
    @PutMapping("/{policyId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "정책 ID") @PathVariable Long policyId,
            @ParameterObject @Valid PolicyDto.UpdatePolicyRequest updatePolicyRequest
    ) {
        policyService.update(policyId, updatePolicyRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 정책 삭제", description = "특정 정책을 삭제하는 API")
    @DeleteMapping("/{policyId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "정책 ID") @PathVariable Long policyId) {
        policyService.delete(policyId);
        return apiResponseManager.noContent();
    }
}