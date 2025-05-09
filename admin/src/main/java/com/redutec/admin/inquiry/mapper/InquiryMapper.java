package com.redutec.admin.inquiry.mapper;

import com.redutec.admin.inquiry.dto.InquiryDto;
import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.InquiryCriteria;
import com.redutec.core.entity.Inquiry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class InquiryMapper {
    private final FileUtil fileUtil;

    /**
     * CreateInquiryRequest DTO를 기반으로 Inquiry 엔티티를 생성합니다.
     *
     * @param createInquiryRequest 고객문의 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Inquiry 엔티티
     */
    public Inquiry toEntity(InquiryDto.CreateInquiryRequest createInquiryRequest) {
        return Inquiry.builder()
                .domain(createInquiryRequest.domain())
                .title(createInquiryRequest.title())
                .content(createInquiryRequest.content())
                .visible(createInquiryRequest.visible())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindInquiryRequest 객체를 기반으로
     * InquiryCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 고객문의 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 InquiryCriteria 객체
     */
    public InquiryCriteria toCriteria(InquiryDto.FindInquiryRequest findInquiryRequest) {
        return new InquiryCriteria(
                findInquiryRequest.inquiryIds(),
                findInquiryRequest.domains(),
                findInquiryRequest.title(),
                findInquiryRequest.content(),
                findInquiryRequest.visible()
        );
    }

    /**
     * Inquiry 엔티티를 기반으로 응답용 InquiryResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param inquiry 변환할 Inquiry 엔티티 (null 가능)
     * @return Inquiry 엔티티의 데이터를 담은 InquiryResponse DTO, inquiry가 null이면 null 반환
     */
    public InquiryDto.InquiryResponse toResponseDto(Inquiry inquiry) {
        return Optional.ofNullable(inquiry)
                .map(f -> new InquiryDto.InquiryResponse(
                        f.getId(),
                        f.getDomain(),
                        f.getTitle(),
                        f.getContent(),
                        f.getVisible(),
                        f.getCreatedAt(),
                        f.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Inquiry 엔티티 목록을 InquiryPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param inquiryPage Page 형태로 조회된 Inquiry 엔티티 목록 (null 가능)
     * @return Inquiry 엔티티 리스트와 페이지 정보를 담은 InquiryPageResponse DTO, inquiryPage가 null이면 null 반환
     */
    public InquiryDto.InquiryPageResponse toPageResponseDto(Page<Inquiry> inquiryPage) {
        return Optional.ofNullable(inquiryPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new InquiryDto.InquiryPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}