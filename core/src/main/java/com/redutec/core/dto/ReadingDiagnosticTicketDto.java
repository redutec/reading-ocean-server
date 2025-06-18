package com.redutec.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReadingDiagnosticTicketDto {
    @Schema(description = "독서능력진단평가 채점권 등록 요청 객체")
    public record CreateReadingDiagnosticTicketRequest(
            @Schema(description = "채점권이 소속할 바우처 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Positive
            Long readingDiagnosticVoucherId,

            @Schema(description = "채점권 일련번호", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(min = 16, max = 16, message = "채점권 일련번호는 16자의 영어 대문자와 숫자로만 구성됩니다")
            @Pattern(regexp = "^[A-Z0-9]{16}$", message = "채점권 일련번호는 정확히 16자의 영어 대문자와 숫자로만 구성되어야 합니다")
            String serial
    ) {}

    @Schema(description = "독서능력진단평가 채점권 조회 요청 객체")
    public record FindReadingDiagnosticTicketRequest(
            @Schema(description = "독서능력진단평가 채점권 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> readingDiagnosticTicketIds,

            @Schema(description = "독서능력진단평가 바우처 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> readingDiagnosticVoucherIds,

            @Schema(description = "독서능력진단평가 채점권을 사용하는 교육기관 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> instituteIds,

            @Schema(description = "채점권 일련번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(min = 16, max = 16, message = "채점권 일련번호는 16자의 영어 대문자와 숫자로만 구성됩니다")
            @Pattern(regexp = "^[A-Z0-9]{16}$", message = "채점권 일련번호는 정확히 16자의 영어 대문자와 숫자로만 구성되어야 합니다")
            String serial,

            @Schema(description = "독서능력진단평가 채점권 코드", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean used,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "독서능력진단평가 채점권 응답 객체")
    public record ReadingDiagnosticTicketResponse(
            Long readingDiagnosticTicketId,
            Long readingDiagnosticVoucherId,
            String readingDiagnosticVoucherName,
            String readingDiagnosticVoucherCode,
            Long instituteId,
            String instituteName,
            String serial,
            Boolean used,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "독서능력진단평가 채점권 응답 페이징 객체")
    public record ReadingDiagnosticTicketPageResponse(
            List<ReadingDiagnosticTicketResponse> readingDiagnosticTicketes,
            Long totalElements,
            Integer totalPages
    ) {}
}