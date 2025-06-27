package com.redutec.core.mapper;

import com.redutec.core.criteria.ReadingDiagnosticVoucherCriteria;
import com.redutec.core.dto.ReadingDiagnosticTicketDto;
import com.redutec.core.dto.ReadingDiagnosticVoucherDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.ReadingDiagnosticVoucher;
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
public class ReadingDiagnosticVoucherMapper {
    /**
     * CreateReadingDiagnosticVoucherRequest DTO를 기반으로 ReadingDiagnosticVoucher 등록 엔티티를 생성합니다.
     *
     * @param createReadingDiagnosticVoucherRequest 독서능력진단평가 바우처 등록에 필요한 데이터를 담은 DTO
     * @param institute 바우처를 사용할 교육기관 엔티티
     * @return 등록할 ReadingDiagnosticVoucher 엔티티
     */
    public ReadingDiagnosticVoucher createEntity(
            ReadingDiagnosticVoucherDto.CreateReadingDiagnosticVoucherRequest createReadingDiagnosticVoucherRequest,
            Institute institute
    ) {
        return ReadingDiagnosticVoucher.builder()
                .name(StringUtils.stripToNull(createReadingDiagnosticVoucherRequest.name()))
                .institute(institute)
                .description(StringUtils.stripToNull(createReadingDiagnosticVoucherRequest.description()))
                .build();
    }

    /**
     * UpdateReadingDiagnosticVoucherRequest DTO를 기반으로 ReadingDiagnosticVoucher 엔티티를 수정합니다.
     *
     * @param updateReadingDiagnosticVoucherRequest 독서능력진단평가 바우처 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(
            ReadingDiagnosticVoucher readingDiagnosticVoucher,
            ReadingDiagnosticVoucherDto.UpdateReadingDiagnosticVoucherRequest updateReadingDiagnosticVoucherRequest,
            Institute institute
    ) {
        Optional.ofNullable(StringUtils.stripToNull(updateReadingDiagnosticVoucherRequest.name()))
                .ifPresent(readingDiagnosticVoucher::setName);
        Optional.ofNullable(StringUtils.stripToNull(updateReadingDiagnosticVoucherRequest.description()))
                .ifPresent(readingDiagnosticVoucher::setDescription);
        Optional.ofNullable(institute)
                .ifPresent(readingDiagnosticVoucher::setInstitute);
    }

    /**
     * 이 메서드는 현재 FindReadingDiagnosticVoucherRequest 객체를 기반으로
     * ReadingDiagnosticVoucherCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 독서능력진단평가 바우처 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 ReadingDiagnosticVoucherCriteria 객체
     */
    public ReadingDiagnosticVoucherCriteria toCriteria(ReadingDiagnosticVoucherDto.FindReadingDiagnosticVoucherRequest findReadingDiagnosticVoucherRequest) {
        return new ReadingDiagnosticVoucherCriteria(
                findReadingDiagnosticVoucherRequest.readingDiagnosticVoucherIds(),
                findReadingDiagnosticVoucherRequest.instituteIds(),
                StringUtils.stripToNull(findReadingDiagnosticVoucherRequest.name()),
                StringUtils.stripToNull(findReadingDiagnosticVoucherRequest.description())
        );
    }

    /**
     * ReadingDiagnosticVoucher 엔티티를 기반으로 응답용 ReadingDiagnosticVoucherResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param readingDiagnosticVoucher 변환할 ReadingDiagnosticVoucher 엔티티 (null 가능)
     * @return ReadingDiagnosticVoucher 엔티티의 데이터를 담은 ReadingDiagnosticVoucherResponse DTO, readingDiagnosticVoucher가 null이면 null 반환
     */
    public ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherResponse toResponseDto(
            ReadingDiagnosticVoucher readingDiagnosticVoucher
    ) {
        return Optional.ofNullable(readingDiagnosticVoucher)
                .map(rdv -> new ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherResponse(
                        rdv.getId(),
                        rdv.getName(),
                        rdv.getInstitute().getId(),
                        rdv.getInstitute().getName(),
                        rdv.getDescription(),
                        rdv.getReadingDiagnosticTickets().stream()
                                .map(readingDiagnosticTicket -> new ReadingDiagnosticTicketDto.ReadingDiagnosticTicketResponse(
                                        readingDiagnosticTicket.getId(),
                                        rdv.getId(),
                                        rdv.getName(),
                                        rdv.getInstitute().getId(),
                                        rdv.getInstitute().getName(),
                                        readingDiagnosticTicket.getSerial(),
                                        readingDiagnosticTicket.getUsed(),
                                        readingDiagnosticTicket.getCreatedAt(),
                                        readingDiagnosticTicket.getUpdatedAt()
                                ))
                                .collect(Collectors.toList()),
                        rdv.getCreatedAt(),
                        rdv.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 ReadingDiagnosticVoucher 엔티티 목록을 ReadingDiagnosticVoucherPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param readingDiagnosticVoucherPage Page 형태로 조회된 ReadingDiagnosticVoucher 엔티티 목록 (null 가능)
     * @return ReadingDiagnosticVoucher 엔티티 리스트와 페이지 정보를 담은 ReadingDiagnosticVoucherPageResponse DTO, readingDiagnosticVoucherPage가 null이면 null 반환
     */
    public ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherPageResponse toPageResponseDto(Page<ReadingDiagnosticVoucher> readingDiagnosticVoucherPage) {
        return Optional.ofNullable(readingDiagnosticVoucherPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new ReadingDiagnosticVoucherDto.ReadingDiagnosticVoucherPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}