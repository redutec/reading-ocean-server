package com.redutec.core.specification;

import com.redutec.core.criteria.MeteredBillingCriteria;
import com.redutec.core.entity.MeteredBilling;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class MeteredBillingSpecification {
    public static Specification<MeteredBilling> findWith(MeteredBillingCriteria meteredBillingCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(meteredBillingCriteria.meteredBillingIds())
                            .filter(meteredBillingIds -> !meteredBillingIds.isEmpty())
                            .map(meteredBillingIds -> root.get("id").in(meteredBillingIds)),
                    Optional.ofNullable(meteredBillingCriteria.instituteIds())
                            .filter(instituteIds -> !instituteIds.isEmpty())
                            .map(instituteIds -> root.get("institute").get("id").in(instituteIds)),
                    Optional.ofNullable(meteredBillingCriteria.billingPeriodMonths())
                            .filter(billingPeriodMonths -> !billingPeriodMonths.isEmpty())
                            .map(billingPeriodMonths -> {
                                Predicate[] monthPredicates = billingPeriodMonths.stream()
                                        .map(yearMonth -> {
                                            LocalDate startOfMonth = yearMonth.atDay(1);
                                            LocalDate endOfMonth   = yearMonth.atEndOfMonth();
                                            return criteriaBuilder.between(
                                                    root.get("billingPeriodStart"),
                                                    startOfMonth,
                                                    endOfMonth
                                            );
                                        })
                                        .toArray(Predicate[]::new);
                                return criteriaBuilder.or(monthPredicates);
                            }),
                    Optional.ofNullable(meteredBillingCriteria.billingStatuses())
                            .filter(billingStatuses -> !billingStatuses.isEmpty())
                            .map(billingStatuses -> root.get("billingStatus").in(billingStatuses))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}