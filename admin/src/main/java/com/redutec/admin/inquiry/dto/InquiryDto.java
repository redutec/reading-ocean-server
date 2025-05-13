package com.redutec.admin.inquiry.dto;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.InquirerType;
import com.redutec.core.meta.InquiryCategory;
import com.redutec.core.meta.InquiryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class InquiryDto {
    @Schema(description = "고객문의 등록 요청 객체")
    public record CreateInquiryRequest(
            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "문의자 구분(학생/교사/비회원)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InquirerType inquirerType,

            @Schema(description = "문의 유형(예: 리딩오션, 기술지원 등)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InquiryCategory category,

            @Schema(description = "처리 상태(응답대기/처리중/응답완료/종료)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InquiryStatus status,

            @Schema(description = "문의자 이메일", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Email(message = "이메일 형식으로 입력해주세요")
            String inquirerEmail,

            @Schema(description = "답변자(어드민 사용자) 닉네임", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String responderNickname,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "답변", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String response
    ) {}

    @Schema(description = "고객문의 조회 요청 객체")
    public record FindInquiryRequest(
            @Schema(description = "고객문의 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> inquiryIds,

            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = Domain.class)
            @Enumerated(EnumType.STRING)
            List<Domain> domains,

            @Schema(description = "문의자 구분(학생/교사/비회원)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = InquirerType.class)
            @Enumerated(EnumType.STRING)
            List<InquirerType> inquirerTypes,

            @Schema(description = "문의 유형(예: 리딩오션, 기술지원 등)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = InquiryCategory.class)
            @Enumerated(EnumType.STRING)
            List<InquiryCategory> categories,

            @Schema(description = "처리 상태(응답대기/처리중/응답완료/종료)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = InquiryStatus.class)
            @Enumerated(EnumType.STRING)
            List<InquiryStatus> statuses,

            @Schema(description = "문의자 이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email(message = "이메일 형식으로 입력해주세요")
            String inquirerEmail,

            @Schema(description = "답변자(어드민 사용자) 닉네임", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String responderNickname,

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

    @Schema(description = "고객문의 수정 요청 객체")
    public record UpdateInquiryRequest(
            @Schema(description = "서비스 도메인", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            Domain domain,

            @Schema(description = "문의자 구분(학생/교사/비회원)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InquirerType inquirerType,

            @Schema(description = "문의 유형(예: 리딩오션, 기술지원 등)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InquiryCategory category,

            @Schema(description = "처리 상태(응답대기/처리중/응답완료/종료)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InquiryStatus status,

            @Schema(description = "문의자 이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email(message = "이메일 형식으로 입력해주세요")
            String inquirerEmail,

            @Schema(description = "답변자(어드민 사용자) 닉네임", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String responderNickname,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "답변", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String response
    ) {}

    @Schema(description = "고객문의 응답 객체")
    public record InquiryResponse(
            Long inquiryId,
            Domain domain,
            InquirerType inquirerType,
            InquiryCategory category,
            InquiryStatus status,
            String inquirerEmail,
            String responderNickname,
            String title,
            String content,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "고객문의 응답 페이징 객체")
    public record InquiryPageResponse(
            List<InquiryResponse> inquiries,
            Long totalElements,
            Integer totalPages
    ) {}
}