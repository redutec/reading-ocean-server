package com.redutec.admin.branch.dto;

import com.redutec.core.meta.BranchStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BranchDto {
    @Schema(description = "지사 등록 요청 객체")
    public record CreateBranchRequest(
            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 20)
            String accountId,

            @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                    message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String password,

            @Schema(description = "권역", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 10)
            String region,

            @Schema(description = "지사명", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 20)
            String name,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BranchStatus status,

            @Schema(description = "영업 구역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String businessArea,

            @Schema(description = "지사장 이름", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 20)
            String managerName,

            @Schema(description = "지사장 연락처", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "\\d{11}")
            String managerPhoneNumber,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email
            String managerEmail,

            @Schema(description = "계약서 파일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            MultipartFile contractFile,

            @Schema(description = "계약일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate contractDate,

            @Schema(description = "갱신일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate renewalDate,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description
    ) {}

    @Schema(description = "지사 조회 요청 객체")
    public record FindBranchRequest(
            @Schema(description = "지사 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> branchIds,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String accountId,

            @Schema(description = "지사명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String name,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BranchStatus.class)
            @Enumerated(EnumType.STRING)
            List<BranchStatus> statuses,

            @Schema(description = "지사장 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String managerName,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "지사 수정 요청 객체")
    public record UpdateBranchRequest(
            @Schema(description = "로그인 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String accountId,

            @Schema(description = "기존 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                     message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String currentPassword,

            @Schema(description = "새로운 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                     message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
            String newPassword,

            @Schema(description = "권역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 10)
            String region,

            @Schema(description = "지사명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String name,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BranchStatus status,

            @Schema(description = "영업 구역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String businessArea,

            @Schema(description = "지사장 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 20)
            String managerName,

            @Schema(description = "지사장 연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "\\d{11}")
            String managerPhoneNumber,

            @Schema(description = "이메일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Email
            String managerEmail,

            @Schema(description = "계약서 파일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            MultipartFile contractFile,

            @Schema(description = "계약일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate contractDate,

            @Schema(description = "갱신일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate renewalDate,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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
            Long totalElements,
            Integer totalPages
    ) {}
}