package com.redutec.admin.student.controller;

import com.redutec.core.dto.StudentDto;
import com.redutec.admin.student.service.StudentService;
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
@RequestMapping("/students")
@Tag(name = "학생 관리 API", description = "학생 관리 API 모음")
public class StudentController {
    private final ApiResponseManager apiResponseManager;
    private final StudentService studentService;

    @Operation(summary = "학생 등록", description = "학생을 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid StudentDto.CreateStudentRequest createStudentRequest
    ) {
        return apiResponseManager.create(studentService.create(createStudentRequest));
    }

    @Operation(summary = "조건에 맞는 학생 목록 조회", description = "조건에 맞는 학생 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid StudentDto.FindStudentRequest findStudentRequest
    ) {
        return apiResponseManager.ok(studentService.find(findStudentRequest));
    }

    @Operation(summary = "특정 학생 조회", description = "특정 학생을 조회하는 API")
    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long studentId) {
        return apiResponseManager.ok(studentService.findById(studentId));
    }

    @Operation(summary = "특정 학생 수정", description = "특정 학생을 수정하는 API")
    @PutMapping("/{studentId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "학생 ID") @PathVariable Long studentId,
            @ParameterObject @Valid StudentDto.UpdateStudentRequest updateStudentRequest
    ) {
        studentService.update(studentId, updateStudentRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 학생 삭제", description = "특정 학생을 삭제하는 API")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "학생 ID") @PathVariable Long studentId
    ) {
        studentService.delete(studentId);
        return apiResponseManager.noContent();
    }
}