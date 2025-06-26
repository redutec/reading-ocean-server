package com.redutec.teachingocean.institute.homeroom.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.HomeroomDto;
import com.redutec.teachingocean.institute.homeroom.service.HomeroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutes/homerooms")
@Tag(name = "학급 API", description = "학급 API 모음")
public class HomeroomController {
    private final ApiResponseManager apiResponseManager;
    private final HomeroomService homeroomService;

    @Operation(summary = "학사관리 - 학급관리 - 신규 학급 등록", description = "현재 로그인한 교사가 속한 교육기관에 신규 학급 등록")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid HomeroomDto.CreateHomeroomRequest createHomeroomRequest
    ) {
        return apiResponseManager.create(homeroomService.create(createHomeroomRequest));
    }

    @Operation(summary = "학사관리 - 학급관리 - 학급 목록 조회", description = "현재 로그인한 교사가 속한 교육기관의 학급 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid HomeroomDto.FindHomeroomRequest findHomeroomRequest
    ) {
        return apiResponseManager.ok(homeroomService.find(findHomeroomRequest));
    }

    @Operation(summary = "학사관리 - 학급관리 - 특정 학급 조회", description = "현재 로그인한 교사가 속한 교육기관의 특정 학급 조회")
    @GetMapping("/{homeroomId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long homeroomId) {
        return apiResponseManager.ok(homeroomService.findById(homeroomId));
    }

    @Operation(summary = "학사관리 - 학급관리 - 특정 학급 수정", description = "현재 로그인한 교사가 속한 교육기관의 특정 학급 수정")
    @PutMapping("/{homeroomId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "학급 ID") @PathVariable Long homeroomId,
            @ParameterObject @Valid HomeroomDto.UpdateHomeroomRequest updateHomeroomRequest
    ) {
        homeroomService.update(homeroomId, updateHomeroomRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "학사관리 - 학급관리 - 선택 학급 삭제", description = "현재 로그인한 교사가 속한 교육기관의 특정 학급들을 삭제")
    @DeleteMapping("/{homeroomIds}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "학급 ID 목록") @PathVariable List<Long> homeroomIds
    ) {
        homeroomService.delete(homeroomIds);
        return apiResponseManager.noContent();
    }
}