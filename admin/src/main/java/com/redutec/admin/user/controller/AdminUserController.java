package com.redutec.admin.user.controller;

import com.redutec.core.dto.AdminUserDto;
import com.redutec.admin.user.service.AdminUserService;
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
@RequestMapping("/users")
@Tag(name = "어드민 사용자 관리 API", description = "어드민 사용자 관리 API 모음")
public class AdminUserController {
    private final ApiResponseManager apiResponseManager;
    private final AdminUserService adminUserService;

    @Operation(summary = "어드민 사용자 등록", description = "어드민 사용자 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject AdminUserDto.CreateAdminUserRequest createAdminUserRequest
    ) {
        return apiResponseManager.create(adminUserService.create(createAdminUserRequest));
    }

    @Operation(summary = "조건에 맞는 어드민 사용자 목록 조회", description = "조건에 맞는 어드민 사용자 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject AdminUserDto.FindAdminUserRequest findAdminUserRequest
    ) {
        return apiResponseManager.ok(adminUserService.find(findAdminUserRequest));
    }

    @Operation(summary = "어드민 사용자 정보 수정", description = "어드민 사용자 정보를 수정하는 API")
    @PutMapping("/{adminUserId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "어드민 사용자 ID") @PathVariable Long adminUserId,
            @ParameterObject AdminUserDto.UpdateAdminUserRequest updateAdminUserRequest
    ) {
        adminUserService.update(adminUserId, updateAdminUserRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "어드민 사용자 삭제", description = "어드민 사용자를 삭제하는 API")
    @DeleteMapping("/{adminUserId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "어드민 사용자 ID") @PathVariable Long adminUserId
    ) {
        adminUserService.delete(adminUserId);
        return apiResponseManager.noContent();
    }
}