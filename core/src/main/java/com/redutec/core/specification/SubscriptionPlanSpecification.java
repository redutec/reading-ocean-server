package com.redutec.core.specification;

import com.redutec.core.criteria.SubscriptionPlanCriteria;
import com.redutec.core.entity.SubscriptionPlan;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class SubscriptionPlanSpecification {
    public static Specification<SubscriptionPlan> findWith(SubscriptionPlanCriteria subscriptionPlanCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(subscriptionPlanCriteria.subscriptionPlanIds())
                            .filter(subscriptionPlanIds -> !subscriptionPlanIds.isEmpty())
                            .map(subscriptionPlanIds -> root.get("id").in(subscriptionPlanIds)),
                    Optional.ofNullable(subscriptionPlanCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(subscriptionPlanCriteria.details())
                            .filter(details -> !details.isEmpty())
                            .map(details -> criteriaBuilder.like(root.get("details"), "%" + details + "%")),
                    Optional.ofNullable(subscriptionPlanCriteria.minimumPrice())
                            .map(minimumPrice -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minimumPrice)),
                    Optional.ofNullable(subscriptionPlanCriteria.maximumPrice())
                            .map(maximumPrice -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maximumPrice)),
                    Optional.ofNullable(subscriptionPlanCriteria.minimumDiscountPercentage())
                            .map(minimumDiscountPercentage -> criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercentage"), minimumDiscountPercentage)),
                    Optional.ofNullable(subscriptionPlanCriteria.maximumDiscountPercentage())
                            .map(maximumDiscountPercentage -> criteriaBuilder.lessThanOrEqualTo(root.get("discountPercentage"), maximumDiscountPercentage)),
                    Optional.ofNullable(subscriptionPlanCriteria.minumumDurationDays())
                            .map(minumumDurationDays -> criteriaBuilder.greaterThanOrEqualTo(root.get("durationDays"), minumumDurationDays)),
                    Optional.ofNullable(subscriptionPlanCriteria.maximumDurationDays())
                            .map(maximumDurationDays -> criteriaBuilder.lessThanOrEqualTo(root.get("durationDays"), maximumDurationDays)),
                    Optional.ofNullable(subscriptionPlanCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(subscriptionPlanCriteria.autoRenew())
                            .map(autoRenew -> criteriaBuilder.equal(root.get("autoRenew"), autoRenew))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}