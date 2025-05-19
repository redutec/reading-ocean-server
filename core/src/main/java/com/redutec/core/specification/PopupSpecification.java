package com.redutec.core.specification;

import com.redutec.core.criteria.PopupCriteria;
import com.redutec.core.entity.Popup;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class PopupSpecification {
    public static Specification<Popup> findWith(PopupCriteria popupCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(popupCriteria.popupIds())
                            .filter(popupIds -> !popupIds.isEmpty())
                            .map(popupIds -> root.get("id").in(popupIds)),
                    Optional.ofNullable(popupCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains)),
                    Optional.ofNullable(popupCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(popupCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(popupCriteria.visible())
                            .map(visible -> criteriaBuilder.equal(root.get("visible"), visible))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}