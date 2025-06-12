package com.redutec.core.mapper;

import com.redutec.core.dto.StudentSubscriptionDto;
import com.redutec.core.criteria.SubscriptionStudentCriteria;
import com.redutec.core.entity.Student;
import com.redutec.core.entity.StudentSubscription;
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
public class SubscriptionStudentMapper {
    /**
     * CreateSubscriptionStudentRequest DTO를 기반으로 SubscriptionStudent 등록 엔티티를 생성합니다.
     *
     * @param createSubscriptionStudentRequest 구독(학생) 등록에 필요한 데이터를 담은 DTO
     * @param subscriptionPlan 구독 상품 엔티티
     * @param student 구독 서비스를 신청한 학생 엔티티
     * @return 등록할 SubscriptionStudent 엔티티
     */
    public StudentSubscription toCreateEntity(
            StudentSubscriptionDto.CreateSubscriptionStudentRequest createSubscriptionStudentRequest,
            SubscriptionPlan subscriptionPlan,
            Student student
    ) {
        return StudentSubscription.builder()
                .subscriptionPlan(subscriptionPlan)
                .startedAt(createSubscriptionStudentRequest.startedAt())
                .endedAt(createSubscriptionStudentRequest.endedAt())
                .nextPaymentAt(createSubscriptionStudentRequest.nextPaymentAt())
                .student(student)
                .build();
    }

    /**
     * UpdateSubscriptionStudentRequest DTO를 기반으로 SubscriptionStudent 수정 엔티티를 생성합니다.
     *
     * @param studentSubscription 수정할 SubscriptionStudent 엔티티
     * @param updateSubscriptionStudentRequest 구독(학생) 수정에 필요한 데이터를 담은 DTO
     * @param subscriptionPlan 구독 상품 엔티티
     * @param student 구독 서비스를 신청한 학생 엔티티
     * @return 수정할 SubscriptionStudent 엔티티
     */
    public StudentSubscription toUpdateEntity(
            StudentSubscription studentSubscription,
            StudentSubscriptionDto.UpdateSubscriptionStudentRequest updateSubscriptionStudentRequest,
            SubscriptionPlan subscriptionPlan,
            Student student
    ) {
        return StudentSubscription.builder()
                .id(studentSubscription.getId())
                .subscriptionPlan(Optional.ofNullable(subscriptionPlan).orElse(studentSubscription.getSubscriptionPlan()))
                .startedAt(Optional.ofNullable(updateSubscriptionStudentRequest.startedAt()).orElse(studentSubscription.getStartedAt()))
                .endedAt(Optional.ofNullable(updateSubscriptionStudentRequest.endedAt()).orElse(studentSubscription.getEndedAt()))
                .nextPaymentAt(Optional.ofNullable(updateSubscriptionStudentRequest.nextPaymentAt()).orElse(studentSubscription.getNextPaymentAt()))
                .student(Optional.ofNullable(student).orElse(studentSubscription.getStudent()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindSubscriptionStudentRequest 객체를 기반으로
     * SubscriptionStudentCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 구독(학생) 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findSubscriptionStudentRequest 구독(학생) 검색 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 SubscriptionStudentCriteria 객체
     */
    public SubscriptionStudentCriteria toCriteria(
            StudentSubscriptionDto.FindSubscriptionStudentRequest findSubscriptionStudentRequest
    ) {
        return new SubscriptionStudentCriteria(
                findSubscriptionStudentRequest.subscriptionStudentIds(),
                findSubscriptionStudentRequest.subscriptionPlanIds(),
                findSubscriptionStudentRequest.studentIds()
        );
    }

    /**
     * SubscriptionStudent 엔티티를 기반으로 응답용 SubscriptionStudentResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param studentSubscription 변환할 SubscriptionStudent 엔티티 (null 가능)
     * @return SubscriptionStudent 엔티티의 데이터를 담은 SubscriptionStudentResponse DTO, subscriptionStudent가 null이면 null 반환
     */
    public StudentSubscriptionDto.SubscriptionStudentResponse toResponseDto(
            StudentSubscription studentSubscription
    ) {
        return Optional.ofNullable(studentSubscription)
                .map(ss -> new StudentSubscriptionDto.SubscriptionStudentResponse(
                        ss.getId(),
                        ss.getStartedAt(),
                        ss.getEndedAt(),
                        ss.getNextPaymentAt(),
                        ss.getSubscriptionPlan().getId(),
                        ss.getSubscriptionPlan().getName(),
                        ss.getSubscriptionPlan().getDetails(),
                        ss.getSubscriptionPlan().getPrice(),
                        ss.getSubscriptionPlan().getDiscountPercentage(),
                        ss.getSubscriptionPlan().getDurationDays(),
                        ss.getSubscriptionPlan().getStatus(),
                        ss.getSubscriptionPlan().getAutoRenew(),
                        ss.getCreatedAt(),
                        ss.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 SubscriptionStudent 엔티티 목록을 SubscriptionStudentPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param subscriptionStudentPage Page 형태로 조회된 SubscriptionStudent 엔티티 목록 (null 가능)
     * @return SubscriptionStudent 엔티티 리스트와 페이지 정보를 담은 SubscriptionStudentPageResponse DTO, subscriptionStudentPage가 null이면 null 반환
     */
    public StudentSubscriptionDto.SubscriptionStudentPageResponse toPageResponseDto(Page<StudentSubscription> subscriptionStudentPage) {
        return Optional.ofNullable(subscriptionStudentPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new StudentSubscriptionDto.SubscriptionStudentPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}