package com.redutec.teachingocean.readingdiagnostic.ticket.service;

public interface ReadingDiagnosticTicketService {
    /**
     * 특정 독서능력진단평가 채점권 사용 처리
     * @param readingDiagnosticTicketSerial 사용 처리할 독서능력진단평가 채점권의 일련번호
     */
    void markAsUsed(String readingDiagnosticTicketSerial);
}