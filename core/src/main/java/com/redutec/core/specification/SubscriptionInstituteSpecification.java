package com.redutec.core.specification;

import com.redutec.core.criteria.SubscriptionInstituteCriteria;
import com.redutec.core.entity.InstituteSubscription;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class SubscriptionInstituteSpecification {
    public static Specification<InstituteSubscription> findWith(SubscriptionInstituteCriteria subscriptionInstituteCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(subscriptionInstituteCriteria.subscriptionInstituteIds())
                            .filter(subscriptionInstituteIds -> !subscriptionInstituteIds.isEmpty())
                            .map(subscriptionInstituteIds -> root.get("id").in(subscriptionInstituteIds)),
                    Optional.ofNullable(subscriptionInstituteCriteria.subscriptionPlanIds())
                            .filter(subscriptionPlanIds -> !subscriptionPlanIds.isEmpty())
                            .map(subscriptionPlanIds -> root.get("subscriptionPlanId").in(subscriptionPlanIds)),
                    Optional.ofNullable(subscriptionInstituteCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("institute").get("id").in(instituteIds))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}