package com.redutec.core.specification.v1;

import com.redutec.core.criteria.v1.BotUserCriteria;
import com.redutec.core.entity.v1.BotUser;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BotUserSpecification {
    public static Specification<BotUser> findWith(BotUserCriteria botUserCriteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (botUserCriteria.getUserNoList() != null && !botUserCriteria.getUserNoList().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("userNo").in(botUserCriteria.getUserNoList()));
            }
            if (botUserCriteria.getUserId() != null && !botUserCriteria.getUserId().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userId"), "%" + botUserCriteria.getUserId() + "%"));
            }
            if (botUserCriteria.getUserName() != null && !botUserCriteria.getUserName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("userName"), "%" + botUserCriteria.getUserName() + "%"));
            }
            return predicate;
        };
    }
}