package com.redutec.core.specification;

import com.redutec.core.criteria.AdministratorCriteria;
import com.redutec.core.entity.Administrator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;


public class AdministratorSpecification {
    public static Specification<Administrator> findWith(
            AdministratorCriteria administratorCriteria
    ) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(administratorCriteria.administratorIds())
                            .filter(administratorIds -> !administratorIds.isEmpty())
                            .map(administratorIds -> root.get("id").in(administratorIds)),
                    Optional.ofNullable(administratorCriteria.nickname())
                            .filter(nickname -> !nickname.isEmpty())
                            .map(nickname -> criteriaBuilder.like(root.get("nickname"), "%" + nickname + "%")),
                    Optional.ofNullable(administratorCriteria.email())
                            .filter(email -> !email.isEmpty())
                            .map(email -> criteriaBuilder.equal(root.get("email"), email)),
                    Optional.ofNullable(administratorCriteria.roles())
                            .filter(roles -> !roles.isEmpty())
                            .map(roles -> root.get("roles").in(roles)),
                    Optional.ofNullable(administratorCriteria.authenticationStatuses())
                            .filter(authenticationStatuses -> !authenticationStatuses.isEmpty())
                            .map(authenticationStatuses -> root.get("authenticationStatuses").in(authenticationStatuses))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}