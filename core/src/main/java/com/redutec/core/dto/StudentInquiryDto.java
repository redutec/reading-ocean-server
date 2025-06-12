package com.redutec.core.dto;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.InquiryCategory;
import com.redutec.core.meta.InquiryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class StudentInquiryDto {
    @Schema(description = "고객문의(학생) 등록 요청 객체")
    public record CreateStudentInquiryRequest(
            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "문의 유형(예: 리딩오션, 기술지원 등)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InquiryCategory category,

            @Schema(description = "처리 상태(응답대기/처리중/응답완료/종료)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InquiryStatus status,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content
    ) {}

    @Schema(description = "고객문의(학생) 조회 요청 객체")
    public record FindStudentInquiryRequest(
            @Schema(description = "고객문의(학생) ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> studentInquiryIds,

            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = Domain.class)
            @Enumerated(EnumType.STRING)
            List<Domain> domains,

            @Schema(description = "문의 유형(예: 리딩오션, 기술지원 등)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = InquiryCategory.class)
            @Enumerated(EnumType.STRING)
            List<InquiryCategory> categories,

            @Schema(description = "처리 상태(응답대기/처리중/응답완료/종료)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = InquiryStatus.class)
            @Enumerated(EnumType.STRING)
            List<InquiryStatus> statuses,

            @Schema(description = "문의자(학생) 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20, message = "문의자(학생) 아이디는 20자를 초과할 수 없습니다")
            String inquirerAccountId,

            @Schema(description = "답변자(어드민 사용자) 로그인 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String responderAccountId,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "답변", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String response,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "고객문의(학생) 수정 요청 객체")
    public record UpdateStudentInquiryRequest(
            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "문의 유형(예: 리딩오션, 기술지원 등)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InquiryCategory category,

            @Schema(description = "처리 상태(응답대기/처리중/응답완료/종료)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InquiryStatus status,

            @Schema(description = "답변자(어드민 사용자) 로그인 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String responderAccountId,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "답변", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String response
    ) {}

    @Schema(description = "고객문의(학생) 응답 객체")
    public record StudentInquiryResponse(
            Long studentInquiryId,
            Domain domain,
            InquiryCategory category,
            InquiryStatus status,
            String inquirerAccountId,
            String responderAccountId,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "고객문의(학생) 응답 페이징 객체")
    public record StudentInquiryPageResponse(
            List<StudentInquiryResponse> inquiries,
            Long totalElements,
            Integer totalPages
    ) {}
}