package com.redutec.core.specification;

import com.redutec.core.criteria.AdministratorCriteria;
import com.redutec.core.entity.Administrator;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class AdministratorSpecification {
    public static Specification<Administrator> findWith(AdministratorCriteria administratorCriteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            return predicate;
        };
    }
}