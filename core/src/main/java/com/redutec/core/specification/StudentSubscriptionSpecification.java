package com.redutec.core.specification;

import com.redutec.core.criteria.StudentSubscriptionCriteria;
import com.redutec.core.entity.StudentSubscription;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class StudentSubscriptionSpecification {
    public static Specification<StudentSubscription> findWith(StudentSubscriptionCriteria studentSubscriptionCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(studentSubscriptionCriteria.studentSubscriptionIds())
                            .filter(studentSubscriptionIds -> !studentSubscriptionIds.isEmpty())
                            .map(studentSubscriptionIds -> root.get("id").in(studentSubscriptionIds)),
                    Optional.ofNullable(studentSubscriptionCriteria.subscriptionPlanIds())
                            .filter(subscriptionPlanIds -> !subscriptionPlanIds.isEmpty())
                            .map(subscriptionPlanIds -> root.get("subscriptionPlanId").in(subscriptionPlanIds)),
                    Optional.ofNullable(studentSubscriptionCriteria.studentIds())
                            .filter(studentIds -> !studentIds.isEmpty())
                            .map(studentIds -> root.get("student").get("id").in(studentIds))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}