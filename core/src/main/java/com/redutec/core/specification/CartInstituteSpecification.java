package com.redutec.core.specification;

import com.redutec.core.criteria.CartInstituteCriteria;
import com.redutec.core.entity.CartInstitute;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class CartInstituteSpecification {
    public static Specification<CartInstitute> findWith(CartInstituteCriteria cartInstituteCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(cartInstituteCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("institute").get("id").in(instituteIds)),
                    Optional.ofNullable(cartInstituteCriteria.productIds())
                            .filter(productIds -> !productIds.isEmpty())
                            .map(productIds -> root.get("items").get("product").get("id").in(productIds))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}