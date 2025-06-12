package com.redutec.core.specification;

import com.redutec.core.criteria.SubscriptionStudentCriteria;
import com.redutec.core.entity.StudentSubscription;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class SubscriptionStudentSpecification {
    public static Specification<StudentSubscription> findWith(SubscriptionStudentCriteria subscriptionStudentCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(subscriptionStudentCriteria.subscriptionStudentIds())
                            .filter(subscriptionStudentIds -> !subscriptionStudentIds.isEmpty())
                            .map(subscriptionStudentIds -> root.get("id").in(subscriptionStudentIds)),
                    Optional.ofNullable(subscriptionStudentCriteria.subscriptionPlanIds())
                            .filter(subscriptionPlanIds -> !subscriptionPlanIds.isEmpty())
                            .map(subscriptionPlanIds -> root.get("subscriptionPlanId").in(subscriptionPlanIds)),
                    Optional.ofNullable(subscriptionStudentCriteria.studentIds())
                            .filter(studentIds -> !studentIds.isEmpty())
                            .map(studentIds -> root.get("student").get("id").in(studentIds))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}