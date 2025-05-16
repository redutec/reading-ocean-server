package com.redutec.core.mapper;

import com.redutec.core.criteria.InquiryCriteria;
import com.redutec.core.dto.InquiryDto;
import com.redutec.core.entity.AdminUser;
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
    /**
     * CreateInquiryRequest DTO를 기반으로 Inquiry 등록 엔티티를 생성합니다.
     *
     * @param createInquiryRequest Inquiry 등록에 필요한 데이터를 담은 DTO
     * @param responder 문의에 답변한 어드민 사용자 엔티티
     * @return 등록할 Inquiry 엔티티
     */
    public Inquiry toCreateEntity(
            InquiryDto.CreateInquiryRequest createInquiryRequest,
            AdminUser responder
    ) {
        return Inquiry.builder()
                .domain(createInquiryRequest.domain())
                .inquirerType(createInquiryRequest.inquirerType())
                .category(createInquiryRequest.category())
                .status(createInquiryRequest.status())
                .inquirerEmail(createInquiryRequest.inquirerEmail())
                .responder(responder)
                .title(createInquiryRequest.title())
                .content(createInquiryRequest.content())
                .response(createInquiryRequest.response())
                .build();
    }

    /**
     * UpdateInquiryRequest DTO를 기반으로 Inquiry 수정 엔티티를 생성합니다.
     *
     * @param inquiry 수정할 Inquiry 엔티티
     * @param updateInquiryRequest Inquiry 수정에 필요한 데이터를 담은 DTO
     * @param responder 문의에 답변한 어드민 사용자 엔티티
     * @return 수정할 Inquiry 엔티티
     */
    public Inquiry toUpdateEntity(
            Inquiry inquiry,
            InquiryDto.UpdateInquiryRequest updateInquiryRequest,
            AdminUser responder
    ) {
        return Inquiry.builder()
                .id(inquiry.getId())
                .domain(Optional.ofNullable(updateInquiryRequest.domain()).orElse(inquiry.getDomain()))
                .inquirerType(Optional.ofNullable(updateInquiryRequest.inquirerType()).orElse(inquiry.getInquirerType()))
                .category(Optional.ofNullable(updateInquiryRequest.category()).orElse(inquiry.getCategory()))
                .status(Optional.ofNullable(updateInquiryRequest.status()).orElse(inquiry.getStatus()))
                .inquirerEmail(Optional.ofNullable(updateInquiryRequest.inquirerEmail()).orElse(inquiry.getInquirerEmail()))
                .responder(Optional.ofNullable(responder).orElse(inquiry.getResponder()))
                .title(Optional.ofNullable(updateInquiryRequest.title()).orElse(inquiry.getTitle()))
                .content(Optional.ofNullable(updateInquiryRequest.content()).orElse(inquiry.getContent()))
                .response(Optional.ofNullable(updateInquiryRequest.response()).orElse(inquiry.getResponse()))
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
                findInquiryRequest.inquirerTypes(),
                findInquiryRequest.categories(),
                findInquiryRequest.statuses(),
                findInquiryRequest.inquirerEmail(),
                findInquiryRequest.responderAccountId(),
                findInquiryRequest.title(),
                findInquiryRequest.content(),
                findInquiryRequest.response()
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
                .map(inq -> new InquiryDto.InquiryResponse(
                        inq.getId(),
                        inq.getDomain(),
                        inq.getInquirerType(),
                        inq.getCategory(),
                        inq.getStatus(),
                        inq.getInquirerEmail(),
                        inq.getResponder().getAccountId(),
                        inq.getTitle(),
                        inq.getContent(),
                        inq.getCreatedAt(),
                        inq.getUpdatedAt()
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