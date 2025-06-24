package com.redutec.admin.reading.diagnostic.ticket.service;

import com.redutec.core.dto.ReadingDiagnosticTicketDto;
import com.redutec.core.entity.ReadingDiagnosticVoucher;

public interface ReadingDiagnosticTicketService {
    /**
     * 독서능력진단평가 채점권 엔티티 생성
     *
     * @param readingDiagnosticVoucher 채점권이 소속할 바우처
     * @param ticketQuantity           생성할 채점권 개수
     */
    void createReadingDiagnosticTicket(ReadingDiagnosticVoucher readingDiagnosticVoucher, Integer ticketQuantity);

    /**
     * 조건에 맞는 독서능력진단평가 채점권 목록 조회
     * @param findReadingDiagnosticTicketRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 독서능력진단평가 채점권 목록 및 페이징 정보
     */
    ReadingDiagnosticTicketDto.ReadingDiagnosticTicketPageResponse find(ReadingDiagnosticTicketDto.FindReadingDiagnosticTicketRequest findReadingDiagnosticTicketRequest);

    /**
     * 특정 독서능력진단평가 채점권 조회
     * @param readingDiagnosticTicketId 독서능력진단평가 채점권 고유번호
     * @return 특정 독서능력진단평가 채점권 응답 객체
     */
    ReadingDiagnosticTicketDto.ReadingDiagnosticTicketResponse findById(Long readingDiagnosticTicketId);

    /**
     * 특정 독서능력진단평가 채점권 사용 처리
     * @param serial 사용 처리할 독서능력진단평가 채점권의 일련번호
     */
    void markAsUsed(String serial);

    /**
     * 특정 독서능력진단평가 채점권 삭제
     * @param readingDiagnosticTicketId 삭제할 독서능력진단평가 채점권의 ID
     */
    void delete(Long readingDiagnosticTicketId);
}