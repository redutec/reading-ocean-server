package com.redutec.core.specification.v1;

import com.redutec.core.criteria.v1.ActAccountCriteria;
import com.redutec.core.entity.v1.ActAccount;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ActAccountSpecification {
    public static Specification<ActAccount> findWith(ActAccountCriteria actAccountCriteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (actAccountCriteria.getAccountNoList() != null && !actAccountCriteria.getAccountNoList().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("accountNo").in(actAccountCriteria.getAccountNoList()));
            }
            if (actAccountCriteria.getAcademyName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("academy").get("academyName"), "%" + actAccountCriteria.getAcademyName() + "%"));
            }
            if (actAccountCriteria.getName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + actAccountCriteria.getName() + "%"));
            }
            if (actAccountCriteria.getAccountId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("accountId"), "%" + actAccountCriteria.getAccountId() + "%"));
            }
            if (actAccountCriteria.getSignupDomainCodeList() != null) {
                predicate = criteriaBuilder.and(predicate, root.get("signupDomainCode").in(actAccountCriteria.getSignupDomainCodeList()));
            }
            return predicate;
        };
    }
}