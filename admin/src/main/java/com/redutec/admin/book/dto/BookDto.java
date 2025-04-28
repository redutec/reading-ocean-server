package com.redutec.admin.book.dto;

import com.redutec.core.meta.BookManagementType;
import com.redutec.core.meta.BookOperationStatus;
import com.redutec.core.meta.BookStatus;
import com.redutec.core.meta.BookType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class BookDto {
    @Schema(description = "도서 등록 요청 객체")
    public record CreateBookRequest(
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
            String zipCode,

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
            BookType type,

            @Schema(description = "운영 유형", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BookManagementType managementType,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BookStatus status,

            @Schema(description = "운영 상태", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BookOperationStatus operationStatus,

            @Schema(description = "소속 지사 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Long branchId
    ) {}

    @Schema(description = "도서 조회 요청 객체")
    public record FindBookRequest(
            @Schema(description = "도서 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> bookIds,

            @Schema(description = "지점명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String name,

            @Schema(description = "사업자 등록명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 60)
            String businessRegistrationName,

            @Schema(description = "유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookType.class)
            @Enumerated(EnumType.STRING)
            List<BookType> types,

            @Schema(description = "운영 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookManagementType.class)
            @Enumerated(EnumType.STRING)
            List<BookManagementType> managementTypes,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookStatus.class)
            @Enumerated(EnumType.STRING)
            List<BookStatus> statuses,

            @Schema(description = "운영 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookOperationStatus.class)
            @Enumerated(EnumType.STRING)
            List<BookOperationStatus> operationStatuses,

            @Schema(description = "지사 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> branchIds,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "도서 수정 요청 객체")
    public record UpdateBookRequest(
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
            String zipCode,

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
            BookType type,

            @Schema(description = "운영 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BookManagementType managementType,

            @Schema(description = "상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BookStatus status,

            @Schema(description = "운영 상태", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BookOperationStatus operationStatus,

            @Schema(description = "소속 지사 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Long branchId
    ) {}

    @Schema(description = "도서 응답 객체")
    public record BookResponse(
            Long bookId,
            String name,
            String businessRegistrationName,
            String address,
            String zipCode,
            String phoneNumber,
            String url,
            String naverPlaceUrl,
            BookType type,
            BookManagementType managementType,
            BookStatus status,
            BookOperationStatus operationStatus,
            Long chiefTeacherId,
            String chiefTeacherName,
            Long branchId,
            String branchName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "도서 응답 페이징 객체")
    public record BookPageResponse(
            List<BookResponse> books,
            Long totalElements,
            Integer totalPages
    ) {}
}