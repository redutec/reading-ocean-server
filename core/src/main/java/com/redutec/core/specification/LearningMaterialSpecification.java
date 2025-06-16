package com.redutec.core.specification;

import com.redutec.core.criteria.LearningMaterialCriteria;
import com.redutec.core.entity.LearningMaterial;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class LearningMaterialSpecification {
    public static Specification<LearningMaterial> findWith(LearningMaterialCriteria learningMaterialCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(learningMaterialCriteria.learningMaterialIds())
                            .filter(learningMaterialIds -> !learningMaterialIds.isEmpty())
                            .map(learningMaterialIds -> root.get("id").in(learningMaterialIds)),
                    Optional.ofNullable(learningMaterialCriteria.categories())
                            .filter(categories -> !categories.isEmpty())
                            .map(categories -> root.get("category").in(categories)),
                    Optional.ofNullable(learningMaterialCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(learningMaterialCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(learningMaterialCriteria.authors())
                            .filter(authors -> !authors.isEmpty())
                            .map(authors -> root.get("author").in(authors)),
                    Optional.ofNullable(learningMaterialCriteria.available())
                            .map(available -> criteriaBuilder.equal(root.get("available"), available))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}