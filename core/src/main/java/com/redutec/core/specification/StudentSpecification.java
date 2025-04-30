package com.redutec.core.specification;

import com.redutec.core.criteria.StudentCriteria;
import com.redutec.core.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class StudentSpecification {
    public static Specification<Student> findWith(StudentCriteria studentCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(studentCriteria.studentIds())
                            .filter(studentIds -> !studentIds.isEmpty())
                            .map(studentIds -> root.get("id").in(studentIds)),
                    Optional.ofNullable(studentCriteria.accountId())
                            .filter(accountId -> !accountId.isEmpty())
                            .map(accountId -> criteriaBuilder.like(root.get("accountId"), "%" + accountId + "%")),
                    Optional.ofNullable(studentCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(studentCriteria.instituteName())
                            .filter(instituteName -> !instituteName.isEmpty())
                            .map(instituteName -> criteriaBuilder.like(root.get("institute").get("name"), "%" + instituteName + "%")),
                    Optional.ofNullable(studentCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}