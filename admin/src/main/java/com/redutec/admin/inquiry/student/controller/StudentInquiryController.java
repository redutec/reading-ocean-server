package com.redutec.admin.inquiry.student.controller;

import com.redutec.admin.inquiry.student.service.StudentInquiryService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.StudentInquiryDto;
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
@RequestMapping("/inquiries/students")
@Tag(name = "고객문의(학생) 관리 API", description = "고객문의(학생) 관리 API 모음")
public class StudentInquiryController {
    private final ApiResponseManager apiResponseManager;
    private final StudentInquiryService studentInquiryService;

    @Operation(summary = "조건에 맞는 고객문의(학생) 목록 조회", description = "조건에 맞는 고객문의(학생) 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid StudentInquiryDto.FindStudentInquiryRequest findStudentInquiryRequest
    ) {
        return apiResponseManager.ok(studentInquiryService.find(findStudentInquiryRequest));
    }

    @Operation(summary = "특정 고객문의(학생) 조회", description = "특정 고객문의(학생)를 조회하는 API")
    @GetMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseBody> findByStudentInquiryId(@PathVariable Long inquiryId) {
        return apiResponseManager.ok(studentInquiryService.findById(inquiryId));
    }

    @Operation(summary = "특정 고객문의(학생) 수정", description = "특정 고객문의(학생)를 수정하는 API")
    @PutMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "고객문의 ID") @PathVariable Long inquiryId,
            @ParameterObject @Valid StudentInquiryDto.UpdateStudentInquiryRequest updateInquiryRequest
    ) {
        studentInquiryService.update(inquiryId, updateInquiryRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 고객문의(학생) 삭제", description = "특정 고객문의(학생)를 삭제하는 API")
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "고객문의(학생) ID") @PathVariable Long inquiryId
    ) {
        studentInquiryService.delete(inquiryId);
        return apiResponseManager.noContent();
    }
}