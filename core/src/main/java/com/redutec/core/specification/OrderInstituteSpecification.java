package com.redutec.core.specification;

import com.redutec.core.criteria.OrderInstituteCriteria;
import com.redutec.core.entity.OrderInstitute;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class OrderInstituteSpecification {
    public static Specification<OrderInstitute> findWith(OrderInstituteCriteria orderInstituteCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(orderInstituteCriteria.orderInstituteIds())
                            .filter(orderInstituteIds -> !orderInstituteIds.isEmpty())
                            .map(orderInstituteIds -> root.get("id").in(orderInstituteIds)),
                    Optional.ofNullable(orderInstituteCriteria.hasDeliveryFee())
                            .map(hasDeliveryFee -> hasDeliveryFee
                                    ? criteriaBuilder.greaterThan(root.get("deliveryFee"), 0)
                                    : criteriaBuilder.equal(root.get("deliveryFee"), 0)),
                    Optional.ofNullable(orderInstituteCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(orderInstituteCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("institute").get("id").in(instituteIds))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}