package com.redutec.admin.branch.controller;

import com.redutec.core.dto.BranchDto;
import com.redutec.admin.branch.service.BranchService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/branches")
@Tag(name = "지사 관리 API", description = "지사 관리 API 모음")
public class BranchController {
    private final ApiResponseManager apiResponseManager;
    private final BranchService branchService;

    @Operation(summary = "지사 등록", description = "지사 정보를 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(
            @ModelAttribute @Valid BranchDto.CreateBranchRequest createBranchRequest
    ) {
        return apiResponseManager.create(branchService.create(createBranchRequest));
    }

    @Operation(summary = "조건에 맞는 지사 목록 조회", description = "조건에 맞는 지사 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid BranchDto.FindBranchRequest findBranchRequest
    ) {
        return apiResponseManager.ok(branchService.find(findBranchRequest));
    }

    @Operation(summary = "특정 지사 조회", description = "특정 지사를 조회하는 API")
    @GetMapping("/{branchId}")
    public ResponseEntity<ApiResponseBody> get(@PathVariable Long branchId) {
        return apiResponseManager.ok(branchService.get(branchId));
    }

    @Operation(summary = "특정 지사 수정", description = "특정 지사를 수정하는 API")
    @PutMapping(path = "/{branchId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "지사 ID") @PathVariable Long branchId,
            @ModelAttribute @Valid BranchDto.UpdateBranchRequest updateBranchRequest
    ) {
        branchService.update(branchId, updateBranchRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 지사 삭제", description = "특정 지사를 삭제하는 API")
    @DeleteMapping("/{branchId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "지사 ID") @PathVariable Long branchId
    ) {
        branchService.delete(branchId);
        return apiResponseManager.noContent();
    }
}
