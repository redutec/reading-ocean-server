package com.redutec.teachingocean.support.inquiry.controller;

import com.redutec.teachingocean.support.inquiry.service.InquiryService;
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
@RequestMapping("/inquiry")
@Tag(name = "고객문의 API", description = "고객문의 API")
public class InquiryController {
    private final ApiResponseManager apiResponseManager;
    private final InquiryService inquiryService;

    @Operation(summary = "고객센터 - 1:1 문의 - 고객문의 등록", description = "고객문의 등록")
    @PostMapping
    public ResponseEntity<ApiResponseBody> create(
            @ParameterObject @Valid TeacherInquiryDto.CreateTeacherInquiryRequest createTeacherInquiryRequest
    ) {
        return apiResponseManager.create(inquiryService.create(createTeacherInquiryRequest));
    }

    @Operation(summary = "고객센터 - 1:1 문의 - 고객문의 목록 조회", description = "현재 로그인한 교육기관이 등록한 고객문의 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid TeacherInquiryDto.FindTeacherInquiryRequest findTeacherInquiryRequest
    ) {
        return apiResponseManager.ok(inquiryService.find(findTeacherInquiryRequest));
    }

    @Operation(summary = "고객센터 - 1:1 문의 - 특정 고객문의 조회", description = "현재 로그인한 교육기관이 등록한 특정 고객문의를 조회")
    @GetMapping("/{teacherInquiryId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long teacherInquiryId) {
        return apiResponseManager.ok(inquiryService.findById(teacherInquiryId));
    }

    @Operation(summary = "고객센터 - 1:1 문의 - 특정 고객문의 수정", description = "특정 고객문의을 수정")
    @PatchMapping("/{teacherInquiryId}")
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "고객문의 ID") @PathVariable Long teacherInquiryId,
            @ParameterObject @Valid TeacherInquiryDto.UpdateTeacherInquiryRequest updateTeacherInquiryRequest
    ) {
        inquiryService.update(teacherInquiryId, updateTeacherInquiryRequest);
        return apiResponseManager.noContent();
    }
}