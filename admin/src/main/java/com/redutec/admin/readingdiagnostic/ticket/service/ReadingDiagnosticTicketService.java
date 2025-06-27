package com.redutec.admin.readingdiagnostic.ticket.service;

import com.redutec.core.entity.ReadingDiagnosticVoucher;

public interface ReadingDiagnosticTicketService {
    /**
     * 독서능력진단평가 채점권 엔티티 생성
     *
     * @param readingDiagnosticVoucher 채점권이 소속할 바우처
     * @param ticketQuantity           생성할 채점권 개수
     */
    void createReadingDiagnosticTicket(ReadingDiagnosticVoucher readingDiagnosticVoucher, Integer ticketQuantity);
}