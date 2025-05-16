package com.redutec.admin.menu.controller;

import com.redutec.admin.menu.service.AdminMenuService;
import com.redutec.core.dto.AdminMenuDto;
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
@RequestMapping("/menu/admin")
@Tag(name = "어드민 메뉴 관리 API", description = "어드민 메뉴 관리 API 모음")
public class AdminMenuController {
    private final ApiResponseManager apiResponseManager;
    private final AdminMenuService adminMenuService;

    @Operation(summary = "어드민 메뉴 등록", description = "어드민 메뉴 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject AdminMenuDto.CreateAdminMenuRequest createAdminMenuRequest
    ) {
        return apiResponseManager.create(adminMenuService.create(createAdminMenuRequest));
    }

    @Operation(summary = "조건에 맞는 어드민 메뉴 목록 조회", description = "조건에 맞는 어드민 메뉴 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject AdminMenuDto.FindAdminMenuRequest findAdminMenuRequest
    ) {
        return apiResponseManager.ok(adminMenuService.find(findAdminMenuRequest));
    }

    @Operation(summary = "어드민 메뉴 정보 수정", description = "어드민 메뉴 정보를 수정하는 API")
    @PatchMapping("/{adminMenuId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "어드민 메뉴 ID") @PathVariable Long adminMenuId,
            @ParameterObject AdminMenuDto.UpdateAdminMenuRequest updateAdminMenuRequest
    ) {
        adminMenuService.update(adminMenuId, updateAdminMenuRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "어드민 메뉴 삭제", description = "어드민 메뉴를 삭제하는 API")
    @DeleteMapping("/{adminMenuId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "어드민 메뉴 ID") @PathVariable Long adminMenuId
    ) {
        adminMenuService.delete(adminMenuId);
        return apiResponseManager.noContent();
    }
}