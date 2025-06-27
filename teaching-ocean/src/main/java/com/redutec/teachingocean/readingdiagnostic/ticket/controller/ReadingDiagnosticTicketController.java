package com.redutec.teachingocean.readingdiagnostic.ticket.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.teachingocean.readingdiagnostic.ticket.service.ReadingDiagnosticTicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reading-diagnostic/tickets")
@Tag(name = "독서능력진단평가 채점권 관리 API", description = "독서능력진단평가 채점권 관리 API 모음")
public class ReadingDiagnosticTicketController {
    private final ApiResponseManager apiResponseManager;
    private final ReadingDiagnosticTicketService readingDiagnosticTicketService;

    @Operation(summary = "특정 독서능력진단평가 채점권 사용 처리", description = "특정 독서능력진단평가 채점권을 사용 처리하는 API")
    @PatchMapping("/{readingDiagnosticTicketSerial}")
    public ResponseEntity<ApiResponseBody> markAsUsed(
            @Parameter(description = "독서능력진단평가 채점권 시리얼") @PathVariable String readingDiagnosticTicketSerial
    ) {
        readingDiagnosticTicketService.markAsUsed(readingDiagnosticTicketSerial);
        return apiResponseManager.noContent();
    }
}