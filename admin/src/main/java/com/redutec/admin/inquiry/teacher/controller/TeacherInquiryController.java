package com.redutec.admin.inquiry.teacher.controller;

import com.redutec.admin.inquiry.teacher.service.TeacherInquiryService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.TeacherInquiryDto;
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
@RequestMapping("/inquiry/teacher")
@Tag(name = "고객문의(교사) 관리 API", description = "고객문의(교사) 관리 API 모음")
public class TeacherInquiryController {
    private final ApiResponseManager apiResponseManager;
    private final TeacherInquiryService teacherInquiryService;

    @Operation(summary = "조건에 맞는 고객문의(교사) 목록 조회", description = "조건에 맞는 고객문의(교사) 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid TeacherInquiryDto.FindTeacherInquiryRequest findTeacherInquiryRequest
    ) {
        return apiResponseManager.ok(teacherInquiryService.find(findTeacherInquiryRequest));
    }

    @Operation(summary = "특정 고객문의(교사) 조회", description = "특정 고객문의(교사)를 조회하는 API")
    @GetMapping("/{teacherInquiryId}")
    public ResponseEntity<ApiResponseBody> findByTeacherInquiryId(@PathVariable Long teacherInquiryId) {
        return apiResponseManager.ok(teacherInquiryService.findById(teacherInquiryId));
    }

    @Operation(summary = "특정 고객문의(교사) 수정", description = "특정 고객문의(교사)를 수정하는 API")
    @PatchMapping("/{teacherInquiryId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "고객문의 ID") @PathVariable Long teacherInquiryId,
            @ParameterObject @Valid TeacherInquiryDto.UpdateTeacherInquiryRequest updateInquiryRequest
    ) {
        teacherInquiryService.update(teacherInquiryId, updateInquiryRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 고객문의(교사) 삭제", description = "특정 고객문의(교사)를 삭제하는 API")
    @DeleteMapping("/{teacherInquiryId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "고객문의(교사) ID") @PathVariable Long teacherInquiryId
    ) {
        teacherInquiryService.delete(teacherInquiryId);
        return apiResponseManager.noContent();
    }
}