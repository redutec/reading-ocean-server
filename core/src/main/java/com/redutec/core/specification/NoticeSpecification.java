package com.redutec.core.specification;

import com.redutec.core.criteria.NoticeCriteria;
import com.redutec.core.entity.Notice;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class NoticeSpecification {
    public static Specification<Notice> findWith(NoticeCriteria noticeCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(noticeCriteria.noticeIds())
                            .filter(noticeIds -> !noticeIds.isEmpty())
                            .map(noticeIds -> root.get("id").in(noticeIds)),
                    Optional.ofNullable(noticeCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains)),
                    Optional.ofNullable(noticeCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(noticeCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(noticeCriteria.visible())
                            .map(visible -> criteriaBuilder.equal(root.get("visible"), visible))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}