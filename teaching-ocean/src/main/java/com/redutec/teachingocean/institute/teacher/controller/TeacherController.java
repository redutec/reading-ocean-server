package com.redutec.teachingocean.institute.teacher.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.TeacherDto;
import com.redutec.teachingocean.institute.teacher.service.TeacherService;
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
@RequestMapping("/institutes/teachers")
@Tag(name = "교사 관리 API", description = "교사 관리 API 모음")
public class TeacherController {
    private final ApiResponseManager apiResponseManager;
    private final TeacherService teacherService;

    @Operation(summary = "학사관리 - 교사관리 - 신규 교사 등록", description = "현재 로그인한 교사가 속한 교육기관에 신규 교사 등록")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid TeacherDto.CreateTeacherRequest createTeacherRequest
    ) {
        return apiResponseManager.create(teacherService.create(createTeacherRequest));
    }

    @Operation(summary = "학사관리 - 교사관리 - 교사 목록 조회", description = "현재 로그인한 교사가 속한 교육기관의 교사 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid TeacherDto.FindTeacherRequest findTeacherRequest
    ) {
        return apiResponseManager.ok(teacherService.find(findTeacherRequest));
    }

    @Operation(summary = "학사관리 - 교사관리 - 특정 교사 조회", description = "현재 로그인한 교사가 속한 교육기관의 특정 교사 조회")
    @GetMapping("/{teacherId}")
    public ResponseEntity<ApiResponseBody> get(@PathVariable Long teacherId) {
        return apiResponseManager.ok(teacherService.get(teacherId));
    }

    @Operation(summary = "학사관리 - 교사관리 - 특정 교사 수정", description = "현재 로그인한 교사가 속한 교육기관의 특정 교사 수정")
    @PutMapping("/{teacherId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "교사 ID") @PathVariable Long teacherId,
            @ParameterObject @Valid TeacherDto.UpdateTeacherRequest updateTeacherRequest
    ) {
        teacherService.update(teacherId, updateTeacherRequest);
        return apiResponseManager.noContent();
    }
}