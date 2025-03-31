package com.redutec.admin.backoffice.controller;

import com.redutec.admin.backoffice.dto.BackOfficeGroupDto;
import com.redutec.admin.backoffice.service.BackOfficeGroupService;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponseDto> create(@Valid @RequestBody BackOfficeGroupDto.CreateBackOfficeGroupRequest createBackOfficeGroupRequest) {
        return apiResponseManager.success(backOfficeGroupService.create(createBackOfficeGroupRequest));
    }

    @Operation(summary = "조건에 맞는 관리자 그룹 조회", description = "조건에 맞는 관리자 그룹 조회 API")
    @GetMapping
    public ResponseEntity<ApiResponseDto> find(
            @Parameter(description = "관리자 그룹 고유번호") @RequestParam(required = false) List<Long> groupNoList,
            @Parameter(description = "관리자 그룹 아이디") @RequestParam(required = false) String groupName,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(required = false) Integer page,
            @Parameter(description = "페이지 당 데이터 개수", example = "30") @RequestParam(required = false) Integer size
    ) {
        return apiResponseManager.success(backOfficeGroupService.find(
                BackOfficeGroupDto.FindBackOfficeGroupRequest.builder()
                        .groupNoList(groupNoList)
                        .groupName(groupName)
                        .page(page)
                        .size(size)
                        .build()));
    }

    @Operation(summary = "특정 관리자 그룹 조회", description = "특정 관리자 그룹 조회 API")
    @GetMapping("/{groupNo}")
    public ResponseEntity<ApiResponseDto> findByGroupNo(@PathVariable Long groupNo) {
        return apiResponseManager.success(backOfficeGroupService.findByGroupNo(groupNo));
    }

    @Operation(summary = "관리자 그룹 수정", description = "관리자 그룹의 정보를 수정하는 API")
    @PutMapping("/{groupNo}")
    public ResponseEntity<ApiResponseDto> update(
            @PathVariable Long groupNo,
            @Valid @RequestBody BackOfficeGroupDto.UpdateBackOfficeGroupRequest updateBackOfficeGroupRequest) {
        backOfficeGroupService.update(groupNo, updateBackOfficeGroupRequest);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "관리자 그룹 삭제", description = "관리자 그룹 삭제 API")
    @DeleteMapping("/{groupNo}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long groupNo) {
        backOfficeGroupService.delete(groupNo);
        return apiResponseManager.success(null);
    }
}