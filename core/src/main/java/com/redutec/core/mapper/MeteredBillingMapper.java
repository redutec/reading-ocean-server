package com.redutec.core.mapper;

import com.redutec.core.criteria.MeteredBillingCriteria;
import com.redutec.core.dto.MeteredBillingDto;
import com.redutec.core.dto.MeteredBillingRecordDto;
import com.redutec.core.entity.MeteredBilling;
import com.redutec.core.repository.MeteredBillingRecordRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class MeteredBillingMapper {
    private final MeteredBillingRecordRepository meteredBillingRecordRepository;

    /**
     * 이 메서드는 현재 FindMeteredBillingRequest 객체를 기반으로
     * MeteredBillingCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 사용료 청구서 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 MeteredBillingCriteria 객체
     */
    public MeteredBillingCriteria toCriteria(
            MeteredBillingDto.FindMeteredBillingRequest findMeteredBillingRequest
    ) {
        return new MeteredBillingCriteria(
                findMeteredBillingRequest.meteredBillingIds(),
                findMeteredBillingRequest.instituteIds(),
                findMeteredBillingRequest.billingPeriodMonths(),
                findMeteredBillingRequest.billingStatuses()
        );
    }

    /**
     * MeteredBilling 엔티티를 기반으로 응답용 MeteredBillingResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param meteredBilling 변환할 MeteredBilling 엔티티 (null 가능)
     * @return MeteredBilling 엔티티의 데이터를 담은 MeteredBillingResponse DTO, meteredBilling가 null이면 null 반환
     */

    public MeteredBillingDto.MeteredBillingResponse toResponseDto(MeteredBilling meteredBilling) {
        return Optional.ofNullable(meteredBilling)
                .map(mb -> {
                    List<MeteredBillingRecordDto.MeteredBillingRecordResponse> meteredBillingRecordResponses =
                            meteredBillingRecordRepository
                                    .findByInstituteAndUsageDateBetween(
                                            mb.getInstitute(),
                                            mb.getBillingPeriodStart(),
                                            mb.getBillingPeriodEnd()
                                    ).stream()
                                    .map(record -> new MeteredBillingRecordDto.MeteredBillingRecordResponse(
                                            record.getId(),
                                            record.getUsageDate(),
                                            record.getActiveStudents(),
                                            record.getDailyAmount()
                                    ))
                                    .collect(Collectors.toList());
                    return new MeteredBillingDto.MeteredBillingResponse(
                            mb.getId(),
                            mb.getBillingPeriodStart(),
                            mb.getBillingPeriodEnd(),
                            mb.getInvoiceAmount(),
                            mb.getBillingStatus(),
                            mb.getPaymentDueDate(),
                            mb.getPaymentAt(),
                            mb.getRefundAt(),
                            meteredBillingRecordResponses,
                            mb.getCreatedAt(),
                            mb.getUpdatedAt()
                    );
                })
                .orElse(null);
    }

    /**
     * Page 형식의 MeteredBilling 엔티티 목록을 MeteredBillingPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param meteredBillingPage Page 형태로 조회된 MeteredBilling 엔티티 목록 (null 가능)
     * @return MeteredBilling 엔티티 리스트와 페이지 정보를 담은 MeteredBillingPageResponse DTO, meteredBillingPage가 null이면 null 반환
     */
    public MeteredBillingDto.MeteredBillingPageResponse toPageResponseDto(Page<MeteredBilling> meteredBillingPage) {
        return Optional.ofNullable(meteredBillingPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new MeteredBillingDto.MeteredBillingPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}