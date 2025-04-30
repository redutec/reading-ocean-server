package com.redutec.admin.teacher.controller;

import com.redutec.admin.teacher.dto.TeacherDto;
import com.redutec.admin.teacher.service.TeacherService;
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
@RequestMapping("/teacher")
@Tag(name = "교사 관리 API", description = "교사 관리 API 모음")
public class TeacherController {
    private final ApiResponseManager apiResponseManager;
    private final TeacherService teacherService;

    @Operation(summary = "교사 등록", description = "교사를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid TeacherDto.CreateTeacherRequest createTeacherRequest
    ) {
        return apiResponseManager.success(teacherService.create(createTeacherRequest));
    }

    @Operation(summary = "조건에 맞는 교사 목록 조회", description = "조건에 맞는 교사 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid TeacherDto.FindTeacherRequest findTeacherRequest
    ) {
        return apiResponseManager.success(teacherService.find(findTeacherRequest));
    }

    @Operation(summary = "특정 교사 조회", description = "특정 교사를 조회하는 API")
    @GetMapping("/{teacherId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long teacherId) {
        return apiResponseManager.success(teacherService.findById(teacherId));
    }

    @Operation(summary = "특정 교사 수정", description = "특정 교사를 수정하는 API")
    @PatchMapping("/{teacherId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "교사 ID") @PathVariable Long teacherId,
            @ParameterObject @Valid TeacherDto.UpdateTeacherRequest updateTeacherRequest
    ) {
        teacherService.update(teacherId, updateTeacherRequest);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "특정 교사 삭제", description = "특정 교사를 삭제하는 API")
    @DeleteMapping("/{teacherId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "교사 ID") @PathVariable Long teacherId
    ) {
        teacherService.delete(teacherId);
        return apiResponseManager.success(null);
    }
}