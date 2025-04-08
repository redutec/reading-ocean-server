package com.redutec.admin.backoffice.controller;

import com.redutec.admin.backoffice.dto.BackOfficeGroupDto;
import com.redutec.admin.backoffice.service.BackOfficeGroupService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 그룹 API 컨트롤러.
 * 이 클래스는 관리자 그룹과 관련된 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/back-office/group")
@Tag(name = "관리자 그룹 관리", description = "관리자 그룹 API 모음")
public class BackOfficeGroupController {
    private final ApiResponseManager apiResponseManager;
    private final BackOfficeGroupService backOfficeGroupService;

    @Operation(summary = "관리자 그룹 등록", description = "관리자 그룹을 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject BackOfficeGroupDto.CreateBackOfficeGroupRequest createBackOfficeGroupRequest
    ) {
        return apiResponseManager.success(backOfficeGroupService.create(createBackOfficeGroupRequest));
    }

    @Operation(summary = "조건에 맞는 관리자 그룹 조회", description = "조건에 맞는 관리자 그룹 조회 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject BackOfficeGroupDto.FindBackOfficeGroupRequest findBackOfficeGroupRequest
    ) {
        return apiResponseManager.success(backOfficeGroupService.find(findBackOfficeGroupRequest));
    }

    @Operation(summary = "특정 관리자 그룹 조회", description = "특정 관리자 그룹 조회 API")
    @GetMapping("/{groupNo}")
    public ResponseEntity<ApiResponseBody> findByGroupNo(
            @Parameter(description = "조회할 관리자 그룹 고유번호") @PathVariable Integer groupNo
    ) {
        return apiResponseManager.success(backOfficeGroupService.findByGroupNo(groupNo));
    }

    @Operation(summary = "관리자 그룹 수정", description = "관리자 그룹의 정보를 수정하는 API")
    @PutMapping("/{groupNo}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "수정할 관리자 그룹 고유번호") @PathVariable Integer groupNo,
            @ParameterObject BackOfficeGroupDto.UpdateBackOfficeGroupRequest updateBackOfficeGroupRequest) {
        backOfficeGroupService.update(groupNo, updateBackOfficeGroupRequest);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "관리자 그룹 삭제", description = "관리자 그룹 삭제 API")
    @DeleteMapping("/{groupNo}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "삭제할 관리자 그룹 고유번호") @PathVariable Integer groupNo
    ) {
        backOfficeGroupService.delete(groupNo);
        return apiResponseManager.success(null);
    }
}