package com.redutec.admin.subscription.student.controller;

import com.redutec.core.dto.StudentSubscriptionDto;
import com.redutec.admin.subscription.student.service.SubscriptionStudentService;
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
@RequestMapping("/subscription/student")
@Tag(name = "구독(학생) 관리 API", description = "구독(학생) 관리 API 모음")
public class SubscriptionStudentController {
    private final ApiResponseManager apiResponseManager;
    private final SubscriptionStudentService subscriptionStudentService;

    @Operation(summary = "구독(학생) 등록", description = "구독(학생) 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(@ParameterObject @Valid StudentSubscriptionDto.CreateSubscriptionStudentRequest createSubscriptionStudentRequest) {
        return apiResponseManager.create(subscriptionStudentService.create(createSubscriptionStudentRequest));
    }

    @Operation(summary = "조건에 맞는 구독(학생) 목록 조회", description = "조건에 맞는 구독(학생) 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(@ParameterObject @Valid StudentSubscriptionDto.FindSubscriptionStudentRequest findSubscriptionStudentRequest) {
        return apiResponseManager.ok(subscriptionStudentService.find(findSubscriptionStudentRequest));
    }

    @Operation(summary = "특정 구독(학생) 조회", description = "특정 구독(학생) 정보를 조회하는 API")
    @GetMapping("/{subscriptionStudentId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long subscriptionStudentId) {
        return apiResponseManager.ok(subscriptionStudentService.findById(subscriptionStudentId));
    }

    @Operation(summary = "특정 구독(학생) 수정", description = "특정 구독(학생) 정보를 수정하는 API")
    @PatchMapping("/{subscriptionStudentId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "구독(학생) ID") @PathVariable Long subscriptionStudentId,
            @ParameterObject @Valid StudentSubscriptionDto.UpdateSubscriptionStudentRequest updateSubscriptionStudentRequest
    ) {
        subscriptionStudentService.update(subscriptionStudentId, updateSubscriptionStudentRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 구독(학생) 삭제", description = "특정 구독(학생) 정보를 삭제하는 API")
    @DeleteMapping("/{subscriptionStudentId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "구독(학생) ID") @PathVariable Long subscriptionStudentId) {
        subscriptionStudentService.delete(subscriptionStudentId);
        return apiResponseManager.noContent();
    }
}