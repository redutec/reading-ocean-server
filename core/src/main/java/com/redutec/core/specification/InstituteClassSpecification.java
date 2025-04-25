package com.redutec.core.specification;

import com.redutec.core.criteria.InstituteClassCriteria;
import com.redutec.core.entity.InstituteClass;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class InstituteClassSpecification {
    public static Specification<InstituteClass> findWith(
            InstituteClassCriteria instituteClassCriteria
    ) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(instituteClassCriteria.instituteClassIds())
                            .filter(instituteClassIds -> !instituteClassIds.isEmpty())
                            .map(instituteClassIds -> root.get("id").in(instituteClassIds)),
                    Optional.ofNullable(instituteClassCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(instituteClassCriteria.description())
                            .filter(description -> !description.isEmpty())
                            .map(description -> criteriaBuilder.like(root.get("description"), "%" + description + "%")),
                    Optional.ofNullable(instituteClassCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("institute").get("id").in(instituteIds))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}