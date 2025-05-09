package com.redutec.core.specification;

import com.redutec.core.criteria.FaqCriteria;
import com.redutec.core.entity.Faq;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class FaqSpecification {
    public static Specification<Faq> findWith(FaqCriteria faqCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(faqCriteria.faqIds())
                            .filter(faqIds -> !faqIds.isEmpty())
                            .map(faqIds -> root.get("id").in(faqIds)),
                    Optional.ofNullable(faqCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains)),
                    Optional.ofNullable(faqCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(faqCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(faqCriteria.visible())
                            .map(visible -> criteriaBuilder.equal(root.get("visible"), visible))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}