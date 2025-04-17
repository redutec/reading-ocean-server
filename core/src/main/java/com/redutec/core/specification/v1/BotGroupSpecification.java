package com.redutec.core.specification.v1;

import com.redutec.core.criteria.v1.BotGroupCriteria;
import com.redutec.core.entity.v1.BotGroup;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BotGroupSpecification {
    public static Specification<BotGroup> findWith(BotGroupCriteria botGroupCriteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (botGroupCriteria.getGroupNoList() != null && !botGroupCriteria.getGroupNoList().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("groupNo").in(botGroupCriteria.getGroupNoList()));
            }
            if (botGroupCriteria.getGroupName() != null && !botGroupCriteria.getGroupName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("groupName"), "%" + botGroupCriteria.getGroupName() + "%"));
            }
            return predicate;
        };
    }
}