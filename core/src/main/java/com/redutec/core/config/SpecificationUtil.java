package com.redutec.core.config;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 여러 Optional<Predicate> 스트림을 조합해 하나의 Predicate 로 만들어 주는 유틸리티.
 */
@UtilityClass
public final class SpecificationUtil {
    /**
     * Optional<Predicate> 스트림을 받아, 내부 값을 리스트로 수집한 뒤
     * 비어 있으면 항상 true(Predicate conjunction), 그렇지 않으면 AND 결합된 Predicate 반환.
     *
     * @param predicateStream Optional<Predicate> 스트림
     * @param criteriaBuilder CriteriaBuilder
     * @return 조합된 Predicate
     */
    public static Predicate combinePredicate(
            Stream<Optional<Predicate>> predicateStream,
            CriteriaBuilder criteriaBuilder
    ) {
        List<Predicate> predicates = predicateStream
                .flatMap(Optional::stream)
                .toList();

        return predicates.isEmpty()
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}