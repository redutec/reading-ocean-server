package com.redutec.core.specification;

import com.redutec.core.criteria.CartCriteria;
import com.redutec.core.entity.Cart;
import com.redutec.core.entity.CartItem;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class CartSpecification {
    public static Specification<Cart> findWith(CartCriteria cartCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(cartCriteria.instituteId())
                            .map(id -> criteriaBuilder.equal(root.get("instituteId"), id)),
                    Optional.ofNullable(cartCriteria.productName())
                            .filter(productName -> !productName.isEmpty())
                            .map(productName -> {
                                // items 컬렉션을 product 엔티티와 join 한 뒤 name 컬럼으로 필터
                                Join<Cart, CartItem> items = root.join("items", JoinType.INNER);
                                return criteriaBuilder.like(items.get("product").get("name"), productName + "%");
                            })
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}