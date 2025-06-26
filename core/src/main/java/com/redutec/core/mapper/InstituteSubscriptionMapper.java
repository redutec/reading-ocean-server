package com.redutec.core.mapper;

import com.redutec.core.criteria.InstituteSubscriptionCriteria;
import com.redutec.core.dto.InstituteSubscriptionDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.InstituteSubscription;
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
public class InstituteSubscriptionMapper {
    /**
     * CreateSubscriptionInstituteRequest DTO를 기반으로 InstituteSubscription 등록 엔티티를 생성합니다.
     * @param createInstituteSubscriptionRequest 구독(교육기관) 등록에 필요한 데이터를 담은 DTO
     * @param subscriptionPlan 구독 상품 엔티티
     * @param institute 구독 서비스를 신청한 교육기관 엔티티
     * @return 등록할 SubscriptionInstitute 엔티티
     */
    public InstituteSubscription createEntity(
            InstituteSubscriptionDto.CreateInstituteSubscriptionRequest createInstituteSubscriptionRequest,
            SubscriptionPlan subscriptionPlan,
            Institute institute
    ) {
        return InstituteSubscription.builder()
                .subscriptionPlan(subscriptionPlan)
                .startedAt(createInstituteSubscriptionRequest.startedAt())
                .endedAt(createInstituteSubscriptionRequest.endedAt())
                .nextPaymentAt(createInstituteSubscriptionRequest.nextPaymentAt())
                .institute(institute)
                .build();
    }

    /**
     * UpdateSubscriptionInstituteRequest DTO를 기반으로 InstituteSubscription 엔티티를 수정합니다.
     * @param instituteSubscription 수정할 SubscriptionInstitute 엔티티
     * @param updateInstituteSubscriptionRequest 구독(교육기관) 수정에 필요한 데이터를 담은 DTO
     * @param subscriptionPlan 구독 상품 엔티티
     * @param institute 구독 서비스를 신청한 교육기관 엔티티
     */
    public void updateEntity(
            InstituteSubscription instituteSubscription,
            InstituteSubscriptionDto.UpdateInstituteSubscriptionRequest updateInstituteSubscriptionRequest,
            SubscriptionPlan subscriptionPlan,
            Institute institute
    ) {
        Optional.ofNullable(subscriptionPlan)
                .ifPresent(instituteSubscription::setSubscriptionPlan);
        Optional.ofNullable(updateInstituteSubscriptionRequest.startedAt())
                .ifPresent(instituteSubscription::setStartedAt);
        Optional.ofNullable(updateInstituteSubscriptionRequest.endedAt())
                .ifPresent(instituteSubscription::setEndedAt);
        Optional.ofNullable(updateInstituteSubscriptionRequest.nextPaymentAt())
                .ifPresent(instituteSubscription::setNextPaymentAt);
        Optional.ofNullable(institute)
                .ifPresent(instituteSubscription::setInstitute);
    }
    
    /**
     * 이 메서드는 현재 FindSubscriptionInstituteRequest 객체를 기반으로
     * SubscriptionInstituteCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 구독(교육기관) 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findSubscriptionInstituteRequest 구독(교육기관) 검색 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 SubscriptionInstituteCriteria 객체
     */
    public InstituteSubscriptionCriteria toCriteria(
            InstituteSubscriptionDto.FindInstituteSubscriptionRequest findSubscriptionInstituteRequest
    ) {
        return new InstituteSubscriptionCriteria(
                findSubscriptionInstituteRequest.instituteSubscriptionIds(),
                findSubscriptionInstituteRequest.subscriptionPlanIds(),
                findSubscriptionInstituteRequest.instituteIds()
        );
    }

    /**
     * SubscriptionInstitute 엔티티를 기반으로 응답용 SubscriptionInstituteResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param instituteSubscription 변환할 SubscriptionInstitute 엔티티 (null 가능)
     * @return SubscriptionInstitute 엔티티의 데이터를 담은 SubscriptionInstituteResponse DTO, subscriptionInstitute가 null이면 null 반환
     */
    public InstituteSubscriptionDto.InstituteSubscriptionResponse toResponseDto(
            InstituteSubscription instituteSubscription
    ) {
        return Optional.ofNullable(instituteSubscription)
                .map(si -> new InstituteSubscriptionDto.InstituteSubscriptionResponse(
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
    public InstituteSubscriptionDto.InstituteSubscriptionPageResponse toPageResponseDto(Page<InstituteSubscription> subscriptionInstitutePage) {
        return Optional.ofNullable(subscriptionInstitutePage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new InstituteSubscriptionDto.InstituteSubscriptionPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}