package com.redutec.core.specification;

import com.redutec.core.criteria.InstituteCriteria;
import com.redutec.core.entity.Institute;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class InstituteSpecification {
    public static Specification<Institute> findWith(InstituteCriteria instituteCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(instituteCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("id").in(instituteIds)),
                    Optional.ofNullable(instituteCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(instituteCriteria.businessRegistrationName())
                            .filter(businessRegistrationName -> !businessRegistrationName.isEmpty())
                            .map(businessRegistrationName -> criteriaBuilder.like(root.get("businessRegistrationName"), "%" + businessRegistrationName + "%")),
                    Optional.ofNullable(instituteCriteria.types())
                            .filter(types -> !types.isEmpty())
                            .map(types -> root.get("type").in(types)),
                    Optional.ofNullable(instituteCriteria.managementTypes())
                            .filter(managementTypes -> !managementTypes.isEmpty())
                            .map(managementTypes -> root.get("managementType").in(managementTypes)),
                    Optional.ofNullable(instituteCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(instituteCriteria.operationStatuses())
                            .filter(operationStatuses -> !operationStatuses.isEmpty())
                            .map(operationStatuses -> root.get("operationStatus").in(operationStatuses)),
                    Optional.ofNullable(instituteCriteria.branchIds())
                            .filter(branchId -> !branchId.isEmpty())
                            .map(branchId -> root.get("branch").in(branchId))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}