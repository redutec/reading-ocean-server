package com.redutec.core.specification;

import com.redutec.core.criteria.CmtConfigurationGeneralCriteria;
import com.redutec.core.entity.v1.CmtConfigurationGeneral;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

public class CmtConfigurationGeneralSpecification {
    public static Specification<CmtConfigurationGeneral> findWith(CmtConfigurationGeneralCriteria cmtConfigurationGeneralCriteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (cmtConfigurationGeneralCriteria.getConfigurationKeyList() != null && !cmtConfigurationGeneralCriteria.getConfigurationKeyList().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("configurationKey").in(cmtConfigurationGeneralCriteria.getConfigurationKeyList()));
            }
            if (cmtConfigurationGeneralCriteria.getConfigurationCategoryKey() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("configurationCategoryKey"), "%" + cmtConfigurationGeneralCriteria.getConfigurationCategoryKey() + "%"));
            }
            if (cmtConfigurationGeneralCriteria.getConfigurationCategoryName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("configurationCategoryName"), "%" + cmtConfigurationGeneralCriteria.getConfigurationCategoryName() + "%"));
            }
            if (cmtConfigurationGeneralCriteria.getConfigurationName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("configurationName"), "%" + cmtConfigurationGeneralCriteria.getConfigurationName() + "%"));
            }
            if (cmtConfigurationGeneralCriteria.getConfigurationContent() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("configurationContent"), "%" + cmtConfigurationGeneralCriteria.getConfigurationContent() + "%"));
            }
            if (cmtConfigurationGeneralCriteria.getUseYn() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("useYn"), cmtConfigurationGeneralCriteria.getUseYn()));
            }
            if (cmtConfigurationGeneralCriteria.getDescription() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("description"), "%" + cmtConfigurationGeneralCriteria.getDescription() + "%"));
            }
            return predicate;
        };
    }
}