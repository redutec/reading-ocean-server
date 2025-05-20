package com.redutec.core.specification;

import com.redutec.core.criteria.TeacherCriteria;
import com.redutec.core.entity.Teacher;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class TeacherSpecification {
    public static Specification<Teacher> findWith(TeacherCriteria teacherCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(teacherCriteria.teacherIds())
                            .filter(teacherIds -> !teacherIds.isEmpty())
                            .map(teacherIds -> root.get("id").in(teacherIds)),
                    Optional.ofNullable(teacherCriteria.accountId())
                            .filter(accountId -> !accountId.isEmpty())
                            .map(accountId -> criteriaBuilder.like(root.get("accountId"), "%" + accountId + "%")),
                    Optional.ofNullable(teacherCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(teacherCriteria.instituteName())
                            .filter(instituteName -> !instituteName.isEmpty())
                            .map(instituteName -> criteriaBuilder.like(root.get("institute").get("name"), "%" + instituteName + "%")),
                    Optional.ofNullable(teacherCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(teacherCriteria.roles())
                            .filter(roles -> !roles.isEmpty())
                            .map(roles -> root.get("roles").in(roles))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}