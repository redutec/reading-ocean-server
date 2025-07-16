package com.redutec.admin.menu.teachingocean.controller;

import com.redutec.core.dto.TeachingOceanMenuDto;
import com.redutec.admin.menu.teachingocean.service.TeachingOceanMenuService;
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
@RequestMapping("/teaching-ocean/menus")
@Tag(name = "티칭오션 메뉴 관리 API", description = "티칭오션 메뉴 관리 API 모음")
public class TeachingOceanMenuController {
    private final ApiResponseManager apiResponseManager;
    private final TeachingOceanMenuService teachingOceanMenuService;

    @Operation(summary = "티칭오션 메뉴 등록", description = "티칭오션 메뉴 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject TeachingOceanMenuDto.CreateTeachingOceanMenuRequest createTeachingOceanMenuRequest
    ) {
        return apiResponseManager.create(teachingOceanMenuService.create(createTeachingOceanMenuRequest));
    }

    @Operation(summary = "조건에 맞는 티칭오션 메뉴 목록 조회", description = "조건에 맞는 티칭오션 메뉴 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject TeachingOceanMenuDto.FindTeachingOceanMenuRequest findTeachingOceanMenuRequest
    ) {
        return apiResponseManager.ok(teachingOceanMenuService.find(findTeachingOceanMenuRequest));
    }

    @Operation(summary = "특정 티칭오션 메뉴 조회", description = "특정 티칭오션 메뉴를 조회하는 API")
    @GetMapping("/{teachingOceanMenuId}")
    public ResponseEntity<ApiResponseBody> get(
            @Parameter(description = "티칭오션 메뉴 ID") @PathVariable Long teachingOceanMenuId
    ) {
        return apiResponseManager.ok(teachingOceanMenuService.get(teachingOceanMenuId));
    }

    @Operation(summary = "티칭오션 메뉴 정보 수정", description = "티칭오션 메뉴 정보를 수정하는 API")
    @PutMapping("/{teachingOceanMenuId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "티칭오션 메뉴 ID") @PathVariable Long teachingOceanMenuId,
            @ParameterObject TeachingOceanMenuDto.UpdateTeachingOceanMenuRequest updateTeachingOceanMenuRequest
    ) {
        teachingOceanMenuService.update(teachingOceanMenuId, updateTeachingOceanMenuRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "티칭오션 메뉴 삭제", description = "티칭오션 메뉴를 삭제하는 API")
    @DeleteMapping("/{teachingOceanMenuId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "티칭오션 메뉴 ID") @PathVariable Long teachingOceanMenuId
    ) {
        teachingOceanMenuService.delete(teachingOceanMenuId);
        return apiResponseManager.noContent();
    }
}