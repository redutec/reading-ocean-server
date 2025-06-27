package com.redutec.admin.readingdiagnostic.ticket.service;

import com.redutec.core.entity.ReadingDiagnosticTicket;
import com.redutec.core.entity.ReadingDiagnosticVoucher;
import com.redutec.core.repository.ReadingDiagnosticTicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Service
@Slf4j
@AllArgsConstructor
public class ReadingDiagnosticTicketServiceImpl implements ReadingDiagnosticTicketService {
    private final ReadingDiagnosticTicketRepository readingDiagnosticTicketRepository;

    /**
     * 독서능력진단평가 채점권 엔티티 생성
     * @param readingDiagnosticVoucher 채점권이 소속할 바우처
     * @param ticketQuantity           생성할 채점권 개수
     */
    @Override
    public void createReadingDiagnosticTicket(
            ReadingDiagnosticVoucher readingDiagnosticVoucher,
            Integer ticketQuantity
    ) {
        // 랜덤 시리얼 생성을 위한 문자 집합과 난수 생성기 준비(비슷하게 생긴 숫자와 알파벳은 제거)
        String characterPool = "ACDEFGHIJKLMNOPQRTUVWXYZ23456789";
        RandomGenerator randomGenerator = RandomGeneratorFactory.getDefault().create();
        // 메모리에서 중복 없이 목표 개수만큼 시리얼 후보 생성
        Set<String> candidateSerials = new HashSet<>();
        while (candidateSerials.size() < ticketQuantity) {
            StringBuilder serialBuilder = new StringBuilder(16);
            for (int i = 0; i < 16; i++) {
                serialBuilder.append(
                        characterPool.charAt(randomGenerator.nextInt(characterPool.length()))
                );
            }
            candidateSerials.add(serialBuilder.toString());
        }
        // DB에 이미 존재하는 시리얼을 한 번에 조회하여 후보에서 제거
        List<String> existingSerials = readingDiagnosticTicketRepository.findAllSerialsBySerialIn(candidateSerials);
        existingSerials.forEach(candidateSerials::remove);
        // 제거한 시리얼 수만큼 재생성
        while (candidateSerials.size() < ticketQuantity) {
            StringBuilder serialBuilder = new StringBuilder(16);
            for (int i = 0; i < 16; i++) {
                serialBuilder.append(
                        characterPool.charAt(randomGenerator.nextInt(characterPool.length()))
                );
            }
            candidateSerials.add(serialBuilder.toString());
        }
        // 생성된 시리얼로 엔티티를 만들고, 바우처와 양방향 연관관계 설정
        List<ReadingDiagnosticTicket> readingDiagnosticTickets = new ArrayList<>();
        for (String serial : candidateSerials) {
            ReadingDiagnosticTicket ticket = ReadingDiagnosticTicket.builder()
                    .serial(serial)
                    .build();
            readingDiagnosticVoucher.addReadingDiagnosticTicket(ticket);
            readingDiagnosticTickets.add(ticket);
        }
        // 일괄 저장 후 반환
        readingDiagnosticTicketRepository.saveAll(readingDiagnosticTickets);
    }
}