package com.redutec.core.specification;

import com.redutec.core.criteria.ReadingDiagnosticVoucherCriteria;
import com.redutec.core.entity.ReadingDiagnosticVoucher;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class ReadingDiagnosticVoucherSpecification {
    public static Specification<ReadingDiagnosticVoucher> findWith(ReadingDiagnosticVoucherCriteria readingDiagnosticVoucherCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(readingDiagnosticVoucherCriteria.readingDiagnoticVoucherIds())
                            .filter(readingDiagnoticVoucherIds -> !readingDiagnoticVoucherIds.isEmpty())
                            .map(readingDiagnoticVoucherIds -> root.get("id").in(readingDiagnoticVoucherIds)),
                    Optional.ofNullable(readingDiagnosticVoucherCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("institute").get("id").in(instituteIds)),
                    Optional.ofNullable(readingDiagnosticVoucherCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(readingDiagnosticVoucherCriteria.code())
                            .filter(code -> !code.isEmpty())
                            .map(code -> criteriaBuilder.like(root.get("code"), "%" + code + "%")),
                    Optional.ofNullable(readingDiagnosticVoucherCriteria.description())
                            .filter(description -> !description.isEmpty())
                            .map(description -> criteriaBuilder.like(root.get("description"), "%" + description + "%"))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}