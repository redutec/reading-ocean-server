package com.redutec.teachingocean.institute.homeroom.controller;

import com.redutec.core.dto.HomeroomDto;
import com.redutec.teachingocean.institute.homeroom.service.HomeroomService;
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
@RequestMapping("/institute/homeroom")
@Tag(name = "학급 관리 API", description = "학급 관리 API 모음")
public class HomeroomController {
    private final ApiResponseManager apiResponseManager;
    private final HomeroomService homeroomService;

    @Operation(summary = "학급 등록", description = "학급 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid HomeroomDto.CreateHomeroomRequest createHomeroomRequest
    ) {
        return apiResponseManager.create(homeroomService.create(createHomeroomRequest));
    }

    @Operation(summary = "조건에 맞는 학급 목록 조회", description = "조건에 맞는 학급 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid HomeroomDto.FindHomeroomRequest findHomeroomRequest
    ) {
        return apiResponseManager.ok(homeroomService.find(findHomeroomRequest));
    }

    @Operation(summary = "특정 학급 조회", description = "특정 학급를 조회하는 API")
    @GetMapping("/{homeroomId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long homeroomId) {
        return apiResponseManager.ok(homeroomService.findById(homeroomId));
    }

    @Operation(summary = "특정 학급 수정", description = "특정 학급를 수정하는 API")
    @PatchMapping("/{homeroomId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "학급 ID") @PathVariable Long homeroomId,
            @ParameterObject @Valid HomeroomDto.UpdateHomeroomRequest updateHomeroomRequest
    ) {
        homeroomService.update(homeroomId, updateHomeroomRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 학급 삭제", description = "특정 학급를 삭제하는 API")
    @DeleteMapping("/{homeroomId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "학급 ID") @PathVariable Long homeroomId
    ) {
        homeroomService.delete(homeroomId);
        return apiResponseManager.noContent();
    }
}