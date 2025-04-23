package com.redutec.core.specification;

import com.redutec.core.criteria.AdminUserCriteria;
import com.redutec.core.entity.AdminUser;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

@Slf4j
public class AdminUserSpecification {
    public static Specification<AdminUser> findWith(
            AdminUserCriteria adminUserCriteria
    ) {
        return (root, query, criteriaBuilder) -> {
            log.info("email: {}", adminUserCriteria.email());
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(adminUserCriteria.adminUserIds())
                            .filter(adminUserIds -> !adminUserIds.isEmpty())
                            .map(adminUserIds -> root.get("id").in(adminUserIds)),
                    Optional.ofNullable(adminUserCriteria.email())
                            .filter(email -> !email.isEmpty())
                            .map(email -> criteriaBuilder.equal(root.get("email"), email)),
                    Optional.ofNullable(adminUserCriteria.nickname())
                            .filter(nickname -> !nickname.isEmpty())
                            .map(nickname -> criteriaBuilder.like(root.get("nickname"), "%" + nickname + "%")),
                    Optional.ofNullable(adminUserCriteria.roles())
                            .filter(roles -> !roles.isEmpty())
                            .map(roles -> root.get("roles").in(roles)),
                    Optional.ofNullable(adminUserCriteria.authenticationStatuses())
                            .filter(authenticationStatuses -> !authenticationStatuses.isEmpty())
                            .map(authenticationStatuses -> root.get("authenticationStatuses").in(authenticationStatuses))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}