package com.redutec.admin.student.controller;

import com.redutec.admin.student.dto.StudentDto;
import com.redutec.admin.student.service.StudentService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 학생 계정 관리 API 컨트롤러.
 * 이 클래스는 학생 계정 관리와 관련된 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Tag(name = "학생 계정 관리 API", description = "학생 계정 관리 API 모음")
public class StudentController {
    private final ApiResponseManager apiResponseManager;
    private final StudentService studentService;

    /**
     * 학생 계정 조회 API
     * 주어진 조건에 따라 학생 계정 데이터를 조회하여 반환합니다
     *
     * @return 조건에 맞는 학생 계정 목록과 페이징 정보를 포함한 응답 객체를 반환합니다
     */
    @Operation(summary = "조건에 맞는 학생 계정 조회", description = "조건에 맞는 학생 계정 조회 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject StudentDto.FindStudentRequest findStudentRequest
    ) {
        return apiResponseManager.success(studentService.find(findStudentRequest));
    }
}
