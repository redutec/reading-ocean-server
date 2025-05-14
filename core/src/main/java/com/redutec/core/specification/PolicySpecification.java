package com.redutec.core.specification;

import com.redutec.core.criteria.PolicyCriteria;
import com.redutec.core.entity.Policy;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class PolicySpecification {
    public static Specification<Policy> findWith(PolicyCriteria policyCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(policyCriteria.policyIds())
                            .filter(policyIds -> !policyIds.isEmpty())
                            .map(policyIds -> root.get("id").in(policyIds)),
                    Optional.ofNullable(policyCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains)),
                    Optional.ofNullable(policyCriteria.types())
                            .filter(types -> !types.isEmpty())
                            .map(types -> root.get("type").in(types)),
                    Optional.ofNullable(policyCriteria.available())
                            .map(available -> criteriaBuilder.equal(root.get("available"), available))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}