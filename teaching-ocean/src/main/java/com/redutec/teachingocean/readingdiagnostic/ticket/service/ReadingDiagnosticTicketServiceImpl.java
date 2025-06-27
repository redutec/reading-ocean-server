package com.redutec.teachingocean.readingdiagnostic.ticket.service;

import com.redutec.core.entity.ReadingDiagnosticTicket;
import com.redutec.core.repository.ReadingDiagnosticTicketRepository;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ReadingDiagnosticTicketServiceImpl implements ReadingDiagnosticTicketService {
    private final ReadingDiagnosticTicketRepository readingDiagnosticTicketRepository;
    private final AuthenticationService authenticationService;

    /**
     * 특정 독서능력진단평가 채점권 사용 처리
     *
     * @param readingDiagnosticTicketSerial 사용 처리할 독서능력진단평가 채점권의 일련번호
     */
    @Override
    @Transactional
    public void markAsUsed(String readingDiagnosticTicketSerial) {
        // 파라미터로 들어온 일련번호에 해당하는 채점권 엔티티 조회
        ReadingDiagnosticTicket readingDiagnosticTicket = readingDiagnosticTicketRepository.findBySerial(readingDiagnosticTicketSerial)
                .orElseThrow(() -> new EntityNotFoundException("유효하지 않은 채점권입니다. readingDiagnosticTicketSerial: " + readingDiagnosticTicketSerial));
        // 현재 로그인한 교사가 속한 교육기관이 보유한 바우처의 채점권인지 검증
        Optional.of(readingDiagnosticTicket)
                .filter(ticket -> ticket.getReadingDiagnosticVoucher()
                        .getInstitute().getId()
                        .equals(authenticationService.getAuthenticatedTeacher().instituteId()))
                .orElseThrow(() -> new AccessDeniedException("해당 교육기관의 채점권이 아닙니다. readingDiagnosticTicketSerial: " + readingDiagnosticTicketSerial))
                .markAsUsed();
    }
}