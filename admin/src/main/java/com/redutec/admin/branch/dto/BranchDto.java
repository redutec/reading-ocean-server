package com.redutec.admin.branch.dto;

import com.redutec.core.meta.BranchStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BranchDto {
    @Schema(description = "지사 등록 요청 객체")
    public record CreateBranchRequest(
        @Schema(description = "로그인 아이디")
        @Size(max = 20)
        String accountId,

        @Schema(description = "비밀번호")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
        String password,

        @Schema(description = "권역")
        @Size(max = 10)
        String region,

        @Schema(description = "지사명")
        @Size(max = 20)
        String name,

        @Schema(description = "상태")
        @Enumerated(EnumType.STRING)
        BranchStatus status,

        @Schema(description = "영업 구역")
        String businessArea,

        @Schema(description = "지사장 이름")
        @Size(max = 20)
        String managerName,

        @Schema(description = "지사장 연락처")
        @Pattern(regexp = "\\d{11}")
        String managerPhoneNumber,

        @Schema(description = "이메일")
        @Email
        String managerEmail,

        @Schema(description = "계약서 파일명")
        @Pattern(
                regexp = "(?i)^.+\\.(pdf|jpg|png)$",
                message = "파일 확장자는 pdf, jpg, png 만 가능합니다"
        )
        String contractFileName,

        @Schema(description = "계약일")
        LocalDate contractDate,

        @Schema(description = "갱신일")
        LocalDate renewalDate,

        @Schema(description = "비고")
        String description
    ) {}

    @Schema(description = "지사 조회 요청 객체")
    public record FindBranchRequest(
        @Schema(description = "지사 ID")
        List<@Positive Long> branchIds,

        @Schema(description = "이메일")
        String accountId,

        @Schema(description = "지사명")
        @Size(max = 20)
        String name,

        @Schema(description = "상태")
        @ElementCollection(targetClass = BranchStatus.class)
        @Enumerated(EnumType.STRING)
        List<BranchStatus> statuses,

        @Schema(description = "지사장 이름")
        String managerName,

        @Schema(description = "페이지 번호(0 이상의 정수)", example = "0")
        @PositiveOrZero
        Integer page,

        @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", example = "60")
        @Positive
        Integer size
    ) {}

    @Schema(description = "지사 수정 요청 객체")
    public record UpdateBranchRequest(
            @Schema(description = "로그인 아이디")
            @Size(max = 20)
            String accountId,

            @Schema(description = "비밀번호")
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String currentPassword,

            @Schema(description = "비밀번호")
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String newPassword,

            @Schema(description = "권역")
            @Size(max = 10)
            String region,

            @Schema(description = "지사명")
            @Size(max = 20)
            String name,

            @Schema(description = "상태")
            @Enumerated(EnumType.STRING)
            BranchStatus status,

            @Schema(description = "영업 구역")
            String businessArea,

            @Schema(description = "지사장 이름")
            @Size(max = 20)
            String managerName,

            @Schema(description = "지사장 연락처")
            @Pattern(regexp = "\\d{11}")
            String managerPhoneNumber,

            @Schema(description = "이메일")
            @Email
            String managerEmail,

            @Schema(description = "계약서 파일명")
            @Pattern(
                    regexp = "(?i)^.+\\.(pdf|jpg|png)$",
                    message = "파일 확장자는 pdf, jpg, png 만 가능합니다"
            )
            String contractFileName,

            @Schema(description = "계약일")
            LocalDate contractDate,

            @Schema(description = "갱신일")
            LocalDate renewalDate,

            @Schema(description = "비고")
            String description
    ) {}

    @Schema(description = "지사 응답 객체")
    public record BranchResponse(
        Long branchId,
        String accountId,
        String name,
        BranchStatus status,
        String businessArea,
        String managerName,
        String managerPhoneNumber,
        String managerEmail,
        String contractFileName,
        LocalDate contractDate,
        LocalDate renewalDate,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {}

    @Schema(description = "지사 응답 페이징 객체")
    public record BranchPageResponse(
        List<BranchResponse> branches,
        long totalElements,
        int totalPages
    ) {}
}