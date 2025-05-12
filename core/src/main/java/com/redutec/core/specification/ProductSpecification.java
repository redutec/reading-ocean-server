package com.redutec.core.specification;

import com.redutec.core.criteria.ProductCriteria;
import com.redutec.core.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class ProductSpecification {
    public static Specification<Product> findWith(ProductCriteria productCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(productCriteria.productIds())
                            .filter(productIds -> !productIds.isEmpty())
                            .map(productIds -> root.get("id").in(productIds)),
                    Optional.ofNullable(productCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(productCriteria.details())
                            .filter(details -> !details.isEmpty())
                            .map(details -> criteriaBuilder.like(root.get("details"), "%" + details + "%")),
                    Optional.ofNullable(productCriteria.minimumPrice())
                            .map(minimumPrice -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minimumPrice)),
                    Optional.ofNullable(productCriteria.maximumPrice())
                            .map(maximumPrice -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maximumPrice)),
                    Optional.ofNullable(productCriteria.minimumDiscountPercentage())
                            .map(minimumDiscountPercentage -> criteriaBuilder.greaterThanOrEqualTo(root.get("discountPercentage"), minimumDiscountPercentage)),
                    Optional.ofNullable(productCriteria.maximumDiscountPercentage())
                            .map(maximumDiscountPercentage -> criteriaBuilder.lessThanOrEqualTo(root.get("discountPercentage"), maximumDiscountPercentage)),
                    Optional.ofNullable(productCriteria.categories())
                            .filter(categories -> !categories.isEmpty())
                            .map(categories -> root.get("category").in(categories)),
                    Optional.ofNullable(productCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)));
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}