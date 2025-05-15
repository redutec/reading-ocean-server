package com.redutec.core.specification;

import com.redutec.core.criteria.BranchCriteria;
import com.redutec.core.entity.Branch;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class BranchSpecification {
    public static Specification<Branch> findWith(BranchCriteria branchCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(branchCriteria.branchIds())
                            .filter(branchIds -> !branchIds.isEmpty())
                            .map(branchIds -> root.get("id").in(branchIds)),
                    Optional.ofNullable(branchCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(branchCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("statuses").in(statuses)),
                    Optional.ofNullable(branchCriteria.managerTeacherName())
                            .filter(managerTeacherName -> !managerTeacherName.isEmpty())
                            .map(managerTeacherName -> criteriaBuilder.like(root.get("managerTeacher").get("name"), "%" + managerTeacherName + "%")),
                    Optional.ofNullable(branchCriteria.managerTeacherAccountId())
                            .filter(managerTeacherAccountId -> !managerTeacherAccountId.isEmpty())
                            .map(managerTeacherAccountId -> criteriaBuilder.like(root.get("managerTeacher").get("accountId"), "%" + managerTeacherAccountId + "%"))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}