package com.redutec.core.dto;

import com.redutec.core.meta.InstituteManagementType;
import com.redutec.core.meta.InstituteOperationStatus;
import com.redutec.core.meta.InstituteStatus;
import com.redutec.core.meta.InstituteType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class InstituteDto {
    @Schema(description = "교육기관 등록 요청 객체")
    public record CreateInstituteRequest(
            @Schema(description = "지점명", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 60)
            String name,

            @Schema(description = "사업자 등록명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String businessRegistrationName,

            @Schema(description = "주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 300)
            String address,

            @Schema(description = "우편번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "\\d{5}")
            String postalCode,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "\\d{11}")
            String phoneNumber,

            @Schema(description = "홈페이지", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String url,

            @Schema(description = "네이버 플레이스 URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String naverPlaceUrl,

            @Schema(description = "유형", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InstituteType type,

            @Schema(description = "운영 유형", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InstituteManagementType managementType,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InstituteStatus status,

            @Schema(description = "운영 상태", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            InstituteOperationStatus operationStatus,

            @Schema(description = "소속 지사 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
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

            @Schema(description = "지사 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> branchIds,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "교육기관 수정 요청 객체")
    public record UpdateInstituteRequest(
            @Schema(description = "지점명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String name,

            @Schema(description = "사업자 등록명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String businessRegistrationName,

            @Schema(description = "주소", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 300)
            String address,

            @Schema(description = "우편번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "\\d{5}")
            String postalCode,

            @Schema(description = "연락처", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "\\d{11}")
            String phoneNumber,

            @Schema(description = "홈페이지", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String url,

            @Schema(description = "네이버 플레이스 URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String naverPlaceUrl,

            @Schema(description = "유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InstituteType type,

            @Schema(description = "운영 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InstituteManagementType managementType,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InstituteStatus status,

            @Schema(description = "운영 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            InstituteOperationStatus operationStatus,

            @Schema(description = "소속 지사 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Long branchId
    ) {}

    @Schema(description = "교육기관 응답 객체")
    public record InstituteResponse(
            Long instituteId,
            String name,
            String businessRegistrationName,
            String address,
            String postalCode,
            String phoneNumber,
            String url,
            String naverPlaceUrl,
            InstituteType type,
            InstituteManagementType managementType,
            InstituteStatus status,
            InstituteOperationStatus operationStatus,
            Long chiefTeacherId,
            String chiefTeacherName,
            Long branchId,
            String branchName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "교육기관 응답 페이징 객체")
    public record InstitutePageResponse(
            List<InstituteResponse> institutes,
            Long totalElements,
            Integer totalPages
    ) {}
}