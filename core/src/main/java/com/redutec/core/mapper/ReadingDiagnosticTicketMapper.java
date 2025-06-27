package com.redutec.core.mapper;

import com.redutec.core.criteria.ReadingDiagnosticTicketCriteria;
import com.redutec.core.dto.ReadingDiagnosticTicketDto;
import com.redutec.core.entity.ReadingDiagnosticTicket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class ReadingDiagnosticTicketMapper {
    /**
     * 이 메서드는 현재 FindReadingDiagnosticTicketRequest 객체를 기반으로
     * ReadingDiagnosticTicketCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 독서능력진단평가 티켓 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 ReadingDiagnosticTicketCriteria 객체
     */
    public ReadingDiagnosticTicketCriteria toCriteria(ReadingDiagnosticTicketDto.FindReadingDiagnosticTicketRequest findReadingDiagnosticTicketRequest) {
        return new ReadingDiagnosticTicketCriteria(
                findReadingDiagnosticTicketRequest.readingDiagnosticTicketIds(),
                findReadingDiagnosticTicketRequest.readingDiagnosticVoucherIds(),
                findReadingDiagnosticTicketRequest.instituteIds(),
                StringUtils.stripToNull(findReadingDiagnosticTicketRequest.serial()),
                findReadingDiagnosticTicketRequest.used()
        );
    }

    /**
     * ReadingDiagnosticTicket 엔티티를 기반으로 응답용 ReadingDiagnosticTicketResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param readingDiagnosticTicket 변환할 ReadingDiagnosticTicket 엔티티 (null 가능)
     * @return ReadingDiagnosticTicket 엔티티의 데이터를 담은 ReadingDiagnosticTicketResponse DTO, readingDiagnosticTicket가 null이면 null 반환
     */
    public ReadingDiagnosticTicketDto.ReadingDiagnosticTicketResponse toResponseDto(
            ReadingDiagnosticTicket readingDiagnosticTicket
    ) {
        return Optional.ofNullable(readingDiagnosticTicket)
                .map(rdt -> new ReadingDiagnosticTicketDto.ReadingDiagnosticTicketResponse(
                        rdt.getId(),
                        rdt.getReadingDiagnosticVoucher().getId(),
                        rdt.getReadingDiagnosticVoucher().getName(),
                        rdt.getReadingDiagnosticVoucher().getInstitute().getId(),
                        rdt.getReadingDiagnosticVoucher().getInstitute().getName(),
                        rdt.getSerial(),
                        rdt.getUsed(),
                        rdt.getCreatedAt(),
                        rdt.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 ReadingDiagnosticTicket 엔티티 목록을 ReadingDiagnosticTicketPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param readingDiagnosticTicketPage Page 형태로 조회된 ReadingDiagnosticTicket 엔티티 목록 (null 가능)
     * @return ReadingDiagnosticTicket 엔티티 리스트와 페이지 정보를 담은 ReadingDiagnosticTicketPageResponse DTO, readingDiagnosticTicketPage가 null이면 null 반환
     */
    public ReadingDiagnosticTicketDto.ReadingDiagnosticTicketPageResponse toPageResponseDto(Page<ReadingDiagnosticTicket> readingDiagnosticTicketPage) {
        return Optional.ofNullable(readingDiagnosticTicketPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new ReadingDiagnosticTicketDto.ReadingDiagnosticTicketPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}