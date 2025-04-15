package com.redutec.core.specification;

import com.redutec.core.criteria.BdtArticleCriteria;
import com.redutec.core.entity.v1.BdtArticle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BdtArticleSpecification {
    public static Specification<BdtArticle> findWith(BdtArticleCriteria bdtArticleCriteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (bdtArticleCriteria.getArticleNoList() != null && !bdtArticleCriteria.getArticleNoList().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("articleNo").in(bdtArticleCriteria.getArticleNoList()));
            }
            if (bdtArticleCriteria.getArticleTitle() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("articleTitle"), "%" + bdtArticleCriteria.getArticleTitle() + "%"));
            }
            if (bdtArticleCriteria.getArticleContent() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("articleContent"), "%" + bdtArticleCriteria.getArticleContent() + "%"));
            }
            if (bdtArticleCriteria.getArticleContentDetail() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("articleContentDetail"), "%" + bdtArticleCriteria.getArticleContentDetail() + "%"));
            }
            if (bdtArticleCriteria.getDisplayYn() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("displayYn"), bdtArticleCriteria.getDisplayYn()));
            }
            if (bdtArticleCriteria.getDomainList() != null) {
                predicate = criteriaBuilder.and(predicate, root.get("domain").in(bdtArticleCriteria.getDomainList()));
            }
            return predicate;
        };
    }
}