package com.redutec.core.specification;

import com.redutec.core.criteria.InstituteCartCriteria;
import com.redutec.core.entity.CartItem;
import com.redutec.core.entity.InstituteCart;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class InstituteCartSpecification {
    public static Specification<InstituteCart> findWith(InstituteCartCriteria instituteCartCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(instituteCartCriteria.instituteId())
                            .map(id -> criteriaBuilder.equal(root.get("instituteId"), id)),
                    Optional.ofNullable(instituteCartCriteria.productName())
                            .filter(productName -> !productName.isEmpty())
                            .map(productName -> {
                                // items 컬렉션을 product 엔티티와 join 한 뒤 name 컬럼으로 필터
                                Join<InstituteCart, CartItem> items = root.join("items", JoinType.INNER);
                                return criteriaBuilder.like(items.get("product").get("name"), productName + "%");
                            })
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}