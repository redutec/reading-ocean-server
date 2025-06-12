package com.redutec.core.specification;

import com.redutec.core.criteria.OrderStudentCriteria;
import com.redutec.core.entity.StudentOrder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class OrderStudentSpecification {
    public static Specification<StudentOrder> findWith(OrderStudentCriteria orderStudentCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(orderStudentCriteria.orderInstituteIds())
                            .filter(orderInstituteIds -> !orderInstituteIds.isEmpty())
                            .map(orderInstituteIds -> root.get("id").in(orderInstituteIds)),
                    Optional.ofNullable(orderStudentCriteria.hasDeliveryFee())
                            .map(hasFee -> hasFee
                                    ? criteriaBuilder.greaterThan(root.get("deliveryFee"), 0)
                                    : criteriaBuilder.equal(root.get("deliveryFee"), 0)),
                    Optional.ofNullable(orderStudentCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(orderStudentCriteria.studentIds())
                            .filter(studentIds -> !studentIds.isEmpty())
                            .map(studentIds -> root.get("student").get("id").in(studentIds))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}