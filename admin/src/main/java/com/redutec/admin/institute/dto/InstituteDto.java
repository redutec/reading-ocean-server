package com.redutec.admin.institute.dto;

import com.redutec.core.meta.InstituteManagementType;
import com.redutec.core.meta.InstituteOperationStatus;
import com.redutec.core.meta.InstituteStatus;
import com.redutec.core.meta.InstituteType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InstituteDto {
    @Schema(description = "교육기관 등록 요청 객체")
    public record CreateInstituteRequest(
        @Schema(description = "지점명")
        @Size(max = 60)
        String name,

        @Schema(description = "사업자 등록명")
        @Size(max = 60)
        String businessRegistrationName,

        @Schema(description = "주소")
        @Size(max = 300)
        String address,

        @Schema(description = "우편번호")
        @Pattern(regexp = "\\d{5}")
        String zipCode,

        @Schema(description = "연락처")
        @Pattern(regexp = "\\d{11}")
        String phoneNumber,

        @Schema(description = "홈페이지")
        @Size(max = 100)
        String url,

        @Schema(description = "네이버 플레이스 URL")
        @Size(max = 60)
        String naverPlaceUrl,

        @Schema(description = "유형")
        @Enumerated(EnumType.STRING)
        InstituteType type,

        @Schema(description = "운영 유형")
        @Enumerated(EnumType.STRING)
        InstituteManagementType managementType,

        @Schema(description = "상태")
        @Enumerated(EnumType.STRING)
        InstituteStatus status,

        @Schema(description = "상태")
        @Enumerated(EnumType.STRING)
        InstituteOperationStatus operationStatus,

        @Schema(description = "소속 지사 ID")
        Long branchId
    ) {}

    @Schema(description = "교육기관 조회 요청 객체")
    public record FindInstituteRequest(
        @Schema(description = "교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        List<@Positive Long> instituteIds,

        @Schema(description = "지점명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 60)
        String name,

        @Schema(description = "사업자 등록명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 60)
        String businessRegistrationName,

        @Schema(description = "유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @ElementCollection(targetClass = InstituteType.class)
        @Enumerated(EnumType.STRING)
        List<InstituteType> types,

        @Schema(description = "운영 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @ElementCollection(targetClass = InstituteManagementType.class)
        @Enumerated(EnumType.STRING)
        List<InstituteManagementType> managementTypes,

        @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @ElementCollection(targetClass = InstituteStatus.class)
        @Enumerated(EnumType.STRING)
        List<InstituteStatus> statuses,

        @Schema(description = "운영 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @ElementCollection(targetClass = InstituteOperationStatus.class)
        @Enumerated(EnumType.STRING)
        List<InstituteOperationStatus> operationStatuses,






        

        @Schema(description = "페이지 번호(0 이상의 정수)", example = "0")
        @PositiveOrZero
        Integer page,

        @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", example = "60")
        @Positive
        Integer size
    ) {}

    @Schema(description = "교육기관 수정 요청 객체")
    public record UpdateInstituteRequest(
        @Schema(description = "로그인 아이디")
        @Size(max = 20)
        String accountId,

        @Schema(description = "기존 비밀번호")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                 message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
        String currentPassword,

        @Schema(description = "새로운 비밀번호")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,32}$",
                 message = "비밀번호는 영문 대소문자, 숫자, 특수문자 중 최소 3가지 이상의 조합으로 최소 8자 이상, 최대 32자 이하이어야 합니다.")
        String newPassword,

        @Schema(description = "권역")
        @Size(max = 10)
        String region,

        @Schema(description = "교육기관명")
        @Size(max = 20)
        String name,

        @Schema(description = "상태")
        @Enumerated(EnumType.STRING)
        InstituteStatus status,

        @Schema(description = "영업 구역")
        String businessArea,

        @Schema(description = "교육기관장 이름")
        @Size(max = 20)
        String managerName,

        @Schema(description = "교육기관장 연락처")
        @Pattern(regexp = "\\d{11}")
        String managerPhoneNumber,

        @Schema(description = "이메일")
        @Email
        String managerEmail,

        @Schema(description = "계약서 파일")
        MultipartFile contractFileName,

        @Schema(description = "계약일")
        LocalDate contractDate,

        @Schema(description = "갱신일")
        LocalDate renewalDate,

        @Schema(description = "비고")
        String description
    ) {}

    @Schema(description = "교육기관 응답 객체")
    public record InstituteResponse(
        Long instituteId,
        String accountId,
        String name,
        InstituteStatus status,
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

    @Schema(description = "교육기관 응답 페이징 객체")
    public record InstitutePageResponse(
        List<InstituteResponse> institutes,
        long totalElements,
        int totalPages
    ) {}
}