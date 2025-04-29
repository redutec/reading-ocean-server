package com.redutec.core.specification;

import com.redutec.core.criteria.HomeroomCriteria;
import com.redutec.core.entity.Homeroom;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class HomeroomSpecification {
    public static Specification<Homeroom> findWith(
            HomeroomCriteria homeroomCriteria
    ) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(homeroomCriteria.homeroomIds())
                            .filter(homeroomIds -> !homeroomIds.isEmpty())
                            .map(homeroomIds -> root.get("id").in(homeroomIds)),
                    Optional.ofNullable(homeroomCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(homeroomCriteria.description())
                            .filter(description -> !description.isEmpty())
                            .map(description -> criteriaBuilder.like(root.get("description"), "%" + description + "%")),
                    Optional.ofNullable(homeroomCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("institute").get("id").in(instituteIds))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}