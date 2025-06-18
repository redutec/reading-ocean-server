package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReadingDiagnosticVoucherDto {
    @Schema(description = "독서능력진단평가 바우처 등록 요청 객체")
    public record CreateReadingDiagnosticVoucherRequest(
            @Schema(description = "바우처 이름(사용자에게 보여질 이름)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 40, message = "바우처명은 최대 40자까지 입력할 수 있습니다")
            String name,

            @Schema(description = "바우처를 사용하는 교육기관 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long instituteId,

            @Schema(description = "생성할 채점권 개수", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Integer ticketQuantity,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description
    ) {}

    @Schema(description = "독서능력진단평가 바우처 조회 요청 객체")
    public record FindReadingDiagnosticVoucherRequest(
            @Schema(description = "독서능력진단평가 바우처 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> readingDiagnosticVoucherIds,

            @Schema(description = "독서능력진단평가 바우처를 사용하는 교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> instituteIds,

            @Schema(description = "독서능력진단평가 바우처 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 40, message = "바우처명은 최대 40자까지 입력할 수 있습니다")
            String name,

            @Schema(description = "독서능력진단평가 바우처 코드", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 8, message = "바우처 코드는 최대 8자까지 입력할 수 있습니다")
            @Pattern(regexp = "^[A-Z0-9]+$", message = "바우처 코드는 영어 대문자와 숫자만 사용할 수 있습니다")
            String code,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "독서능력진단평가 바우처 수정 요청 객체")
    public record UpdateReadingDiagnosticVoucherRequest(
            @Schema(description = "바우처 이름(사용자에게 보여질 이름)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 40, message = "바우처 이름은 최대 40자까지 입력할 수 있습니다")
            String name,

            @Schema(description = "바우처를 사용하는 교육기관 고유번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long instituteId,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description
    ) {}

    @Schema(description = "독서능력진단평가 바우처 응답 객체")
    public record ReadingDiagnosticVoucherResponse(
            Long readingDiagnosticVoucherId,
            String readingDiagnosticVoucherName,
            String readingDiagnosticVoucherCode,
            Long instituteId,
            String instituteName,
            String description,
            List<ReadingDiagnosticTicketDto.ReadingDiagnosticTicketResponse> tickets,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "독서능력진단평가 바우처 응답 페이징 객체")
    public record ReadingDiagnosticVoucherPageResponse(
            List<ReadingDiagnosticVoucherResponse> readingDiagnosticVoucheres,
            Long totalElements,
            Integer totalPages
    ) {}
}