package com.redutec.core.specification;

import com.redutec.core.criteria.AdminMenuCriteria;
import com.redutec.core.entity.AdminMenu;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class AdminMenuSpecification {
    public static Specification<AdminMenu> findWith(AdminMenuCriteria adminMenuCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(adminMenuCriteria.adminMenuIds())
                            .filter(adminMenuIds -> !adminMenuIds.isEmpty())
                            .map(adminMenuIds -> root.get("id").in(adminMenuIds)),
                    Optional.ofNullable(adminMenuCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(adminMenuCriteria.url())
                            .filter(url -> !url.isEmpty())
                            .map(url -> criteriaBuilder.like(root.get("url"), "%" + url + "%")),
                    Optional.ofNullable(adminMenuCriteria.available())
                            .map(available -> criteriaBuilder.equal(root.get("available"), available)),
                    Optional.ofNullable(adminMenuCriteria.accessibleRoles())
                            .filter(accessibleRoles -> !accessibleRoles.isEmpty())
                            .map(accessibleRoles -> root.join("accessibleRoles").in(accessibleRoles))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}