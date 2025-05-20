package com.redutec.teachingocean.institute.student.controller;

import com.redutec.teachingocean.institute.student.service.StudentService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.StudentDto;
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
@RequestMapping("/institute/student")
@Tag(name = "학생 관리 API", description = "학생 관리 API 모음")
public class StudentController {
    private final ApiResponseManager apiResponseManager;
    private final StudentService studentService;

    @Operation(summary = "학사관리 - 학생 관리 - 신규 학생 등록", description = "현재 로그인한 교사가 속한 교육기관에 신규 학생 등록")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid StudentDto.CreateStudentRequest createStudentRequest
    ) {
        return apiResponseManager.create(studentService.create(createStudentRequest));
    }

    @Operation(summary = "학사관리 - 학생 관리 - 학생 목록 조회", description = "현재 로그인한 교사가 속한 교육기관의 학생들을 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid StudentDto.FindStudentRequest findStudentRequest
    ) {
        return apiResponseManager.ok(studentService.find(findStudentRequest));
    }

    @Operation(summary = "학사관리 - 학생 관리 - 특정 학생 조회", description = "현재 로그인한 교사가 속한 교육기관의 특정 학생 조회")
    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long studentId) {
        return apiResponseManager.ok(studentService.findById(studentId));
    }

    @Operation(summary = "학사관리 - 학생 관리 - 특정 학생 수정", description = "현재 로그인한 교사가 속한 교육기관의 특정 학생 수정")
    @PatchMapping("/{studentId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "학생 ID") @PathVariable Long studentId,
            @ParameterObject @Valid StudentDto.UpdateStudentRequest updateStudentRequest
    ) {
        studentService.update(studentId, updateStudentRequest);
        return apiResponseManager.noContent();
    }
}