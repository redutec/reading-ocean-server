package com.redutec.core.specification;

import com.redutec.core.criteria.ReadingDiagnosticTicketCriteria;
import com.redutec.core.entity.ReadingDiagnosticTicket;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class ReadingDiagnosticTicketSpecification {
    public static Specification<ReadingDiagnosticTicket> findWith(ReadingDiagnosticTicketCriteria readingDiagnosticTicketCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(readingDiagnosticTicketCriteria.readingDiagnoticTicketIds())
                            .filter(readingDiagnoticTicketIds -> !readingDiagnoticTicketIds.isEmpty())
                            .map(readingDiagnoticTicketIds -> root.get("id").in(readingDiagnoticTicketIds)),
                    Optional.ofNullable(readingDiagnosticTicketCriteria.readingDiagnoticVoucherIds())
                            .filter(readingDiagnoticVoucherIds -> !readingDiagnoticVoucherIds.isEmpty())
                            .map(readingDiagnoticVoucherIds -> root.get("voucher").get("id").in(readingDiagnoticVoucherIds)),
                    Optional.ofNullable(readingDiagnosticTicketCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("voucher").get("institute").get("id").in(instituteIds)),
                    Optional.ofNullable(readingDiagnosticTicketCriteria.serial())
                            .filter(serial -> !serial.isEmpty())
                            .map(serial -> criteriaBuilder.like(root.get("serial"), "%" + serial + "%")),
                    Optional.ofNullable(readingDiagnosticTicketCriteria.used())
                            .map(used -> criteriaBuilder.equal(root.get("used"), used))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}