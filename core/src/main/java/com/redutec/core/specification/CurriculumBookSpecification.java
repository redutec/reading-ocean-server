package com.redutec.core.specification;

import com.redutec.core.criteria.CurriculumBookCriteria;
import com.redutec.core.entity.Book;
import com.redutec.core.entity.CurriculumBook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class CurriculumBookSpecification {
    public static Specification<CurriculumBook> findWith(CurriculumBookCriteria curriculumBookCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 중복 결과 방지
            query.distinct(true);
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(curriculumBookCriteria.curriculumBookIds())
                            .filter(curriculumBookIds -> !curriculumBookIds.isEmpty())
                            .map(curriculumBookIds -> root.get("id").in(curriculumBookIds)),
                    Optional.ofNullable(curriculumBookCriteria.curriculumIds())
                            .filter(curriculumIds -> !curriculumIds.isEmpty())
                            .map(curriculumIds -> root.get("curriculum").get("id").in(curriculumIds)),
                    Optional.ofNullable(curriculumBookCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> {
                                Join<CurriculumBook, Book> book = root.join("book", JoinType.LEFT);
                                return criteriaBuilder.like(book.get("title"), "%" + title + "%");
                            }),
                    Optional.ofNullable(curriculumBookCriteria.readingStatuses())
                            .filter(readingStatuses -> !readingStatuses.isEmpty())
                            .map(readingStatuses -> root.get("readingStatus").in(readingStatuses))

            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}