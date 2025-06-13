package com.redutec.core.specification;

import com.redutec.core.criteria.HeadquartersDocumentCriteria;
import com.redutec.core.entity.HeadquartersDocument;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class HeadquartersDocumentSpecification {
    public static Specification<HeadquartersDocument> findWith(HeadquartersDocumentCriteria headquartersDocumentCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(headquartersDocumentCriteria.headquartersDocumentIds())
                            .filter(headquartersDocumentIds -> !headquartersDocumentIds.isEmpty())
                            .map(headquartersDocumentIds -> root.get("id").in(headquartersDocumentIds)),
                    Optional.ofNullable(headquartersDocumentCriteria.categories())
                            .filter(categories -> !categories.isEmpty())
                            .map(categories -> root.get("category").in(categories)),
                    Optional.ofNullable(headquartersDocumentCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(headquartersDocumentCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(headquartersDocumentCriteria.authors())
                            .filter(authors -> !authors.isEmpty())
                            .map(authors -> root.get("author").in(authors)),
                    Optional.ofNullable(headquartersDocumentCriteria.available())
                            .map(available -> criteriaBuilder.equal(root.get("available"), available))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}