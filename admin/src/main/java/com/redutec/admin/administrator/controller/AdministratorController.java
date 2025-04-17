package com.redutec.admin.administrator.controller;

import com.redutec.admin.administrator.dto.AdministratorDto;
import com.redutec.admin.administrator.service.AdministratorService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administrator")
@Tag(name = "시스템 관리자 관리 API", description = "시스템 관리자 관리 API 모음")
public class AdministratorController {
    private final ApiResponseManager apiResponseManager;
    private final AdministratorService administratorService;

    @Operation(summary = "시스템 관리자 등록", description = "시스템 관리자 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject AdministratorDto.CreateAdministratorRequest createAdministratorRequest
    ) {
        return apiResponseManager.success(administratorService.create(createAdministratorRequest));
    }

    @Operation(summary = "조건에 맞는 시스템 관리자 목록 조회", description = "조건에 맞는 시스템 관리자 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject AdministratorDto.FindAdministratorRequest findAdministratorRequest
    ) {
        return apiResponseManager.success(administratorService.find(findAdministratorRequest));
    }

    @Operation(summary = "시스템 관리자 정보 수정", description = "시스템 관리자 정보를 수정하는 API")
    @PatchMapping("/{administratorId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "시스템 관리자 ID") @PathVariable Long administratorId,
            @ParameterObject AdministratorDto.UpdateAdministratorRequest updateUserRequest
    ) {
        administratorService.update(administratorId, updateUserRequest);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "시스템 관리자 삭제", description = "시스템 관리자를 삭제하는 API")
    @DeleteMapping("/{administratorId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "시스템 관리자 ID") @PathVariable Long administratorId
    ) {
        administratorService.delete(administratorId);
        return apiResponseManager.success(null);
    }
}