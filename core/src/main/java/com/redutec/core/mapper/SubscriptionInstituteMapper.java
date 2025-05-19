package com.redutec.core.mapper;

import com.redutec.core.criteria.SubscriptionInstituteCriteria;
import com.redutec.core.dto.SubscriptionInstituteDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.SubscriptionInstitute;
import com.redutec.core.entity.SubscriptionPlan;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class SubscriptionInstituteMapper {
    /**
     * CreateSubscriptionInstituteRequest DTO를 기반으로 SubscriptionInstitute 등록 엔티티를 생성합니다.
     *
     * @param createSubscriptionInstituteRequest 구독(교육기관) 등록에 필요한 데이터를 담은 DTO
     * @param subscriptionPlan 구독 상품 엔티티
     * @param institute 구독 서비스를 신청한 교육기관 엔티티
     * @return 등록할 SubscriptionInstitute 엔티티
     */
    public SubscriptionInstitute toCreateEntity(
            SubscriptionInstituteDto.CreateSubscriptionInstituteRequest createSubscriptionInstituteRequest,
            SubscriptionPlan subscriptionPlan,
            Institute institute
    ) {
        return SubscriptionInstitute.builder()
                .subscriptionPlan(subscriptionPlan)
                .startedAt(createSubscriptionInstituteRequest.startedAt())
                .endedAt(createSubscriptionInstituteRequest.endedAt())
                .nextPaymentAt(createSubscriptionInstituteRequest.nextPaymentAt())
                .institute(institute)
                .build();
    }

    /**
     * UpdateSubscriptionInstituteRequest DTO를 기반으로 SubscriptionInstitute 수정 엔티티를 생성합니다.
     *
     * @param subscriptionInstitute 수정할 SubscriptionInstitute 엔티티
     * @param updateSubscriptionInstituteRequest 구독(교육기관) 수정에 필요한 데이터를 담은 DTO
     * @param subscriptionPlan 구독 상품 엔티티
     * @param institute 구독 서비스를 신청한 교육기관 엔티티
     * @return 수정할 SubscriptionInstitute 엔티티
     */
    public SubscriptionInstitute toUpdateEntity(
            SubscriptionInstitute subscriptionInstitute,
            SubscriptionInstituteDto.UpdateSubscriptionInstituteRequest updateSubscriptionInstituteRequest,
            SubscriptionPlan subscriptionPlan,
            Institute institute
    ) {
        return SubscriptionInstitute.builder()
                .id(subscriptionInstitute.getId())
                .subscriptionPlan(Optional.ofNullable(subscriptionPlan).orElse(subscriptionInstitute.getSubscriptionPlan()))
                .startedAt(Optional.ofNullable(updateSubscriptionInstituteRequest.startedAt()).orElse(subscriptionInstitute.getStartedAt()))
                .endedAt(Optional.ofNullable(updateSubscriptionInstituteRequest.endedAt()).orElse(subscriptionInstitute.getEndedAt()))
                .nextPaymentAt(Optional.ofNullable(updateSubscriptionInstituteRequest.nextPaymentAt()).orElse(subscriptionInstitute.getNextPaymentAt()))
                .institute(Optional.ofNullable(institute).orElse(subscriptionInstitute.getInstitute()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindSubscriptionInstituteRequest 객체를 기반으로
     * SubscriptionInstituteCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 구독(교육기관) 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findSubscriptionInstituteRequest 구독(교육기관) 검색 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 SubscriptionInstituteCriteria 객체
     */
    public SubscriptionInstituteCriteria toCriteria(
            SubscriptionInstituteDto.FindSubscriptionInstituteRequest findSubscriptionInstituteRequest
    ) {
        return new SubscriptionInstituteCriteria(
                findSubscriptionInstituteRequest.subscriptionInstituteIds(),
                findSubscriptionInstituteRequest.subscriptionPlanIds(),
                findSubscriptionInstituteRequest.instituteIds()
        );
    }

    /**
     * SubscriptionInstitute 엔티티를 기반으로 응답용 SubscriptionInstituteResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param subscriptionInstitute 변환할 SubscriptionInstitute 엔티티 (null 가능)
     * @return SubscriptionInstitute 엔티티의 데이터를 담은 SubscriptionInstituteResponse DTO, subscriptionInstitute가 null이면 null 반환
     */
    public SubscriptionInstituteDto.SubscriptionInstituteResponse toResponseDto(
            SubscriptionInstitute subscriptionInstitute
    ) {
        return Optional.ofNullable(subscriptionInstitute)
                .map(si -> new SubscriptionInstituteDto.SubscriptionInstituteResponse(
                        si.getId(),
                        si.getStartedAt(),
                        si.getEndedAt(),
                        si.getNextPaymentAt(),
                        si.getSubscriptionPlan().getId(),
                        si.getSubscriptionPlan().getName(),
                        si.getSubscriptionPlan().getDetails(),
                        si.getSubscriptionPlan().getPrice(),
                        si.getSubscriptionPlan().getDiscountPercentage(),
                        si.getSubscriptionPlan().getDurationDays(),
                        si.getSubscriptionPlan().getStatus(),
                        si.getSubscriptionPlan().getAutoRenew(),
                        si.getCreatedAt(),
                        si.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 SubscriptionInstitute 엔티티 목록을 SubscriptionInstitutePageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param subscriptionInstitutePage Page 형태로 조회된 SubscriptionInstitute 엔티티 목록 (null 가능)
     * @return SubscriptionInstitute 엔티티 리스트와 페이지 정보를 담은 SubscriptionInstitutePageResponse DTO, subscriptionInstitutePage가 null이면 null 반환
     */
    public SubscriptionInstituteDto.SubscriptionInstitutePageResponse toPageResponseDto(Page<SubscriptionInstitute> subscriptionInstitutePage) {
        return Optional.ofNullable(subscriptionInstitutePage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new SubscriptionInstituteDto.SubscriptionInstitutePageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}