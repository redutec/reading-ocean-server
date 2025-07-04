package com.redutec.core.specification;

import com.redutec.core.criteria.BookMbtiQuestionCriteria;
import com.redutec.core.entity.BookMbtiQuestion;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class BookMbtiQuestionSpecification {
    public static Specification<BookMbtiQuestion> findWith(BookMbtiQuestionCriteria bookMbtiQuestionCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(bookMbtiQuestionCriteria.bookMbtiQuestionIds())
                            .filter(bookMbtiQuestionIds -> !bookMbtiQuestionIds.isEmpty())
                            .map(bookMbtiQuestionIds -> root.get("id").in(bookMbtiQuestionIds)),
                    Optional.ofNullable(bookMbtiQuestionCriteria.types())
                            .filter(types -> !types.isEmpty())
                            .map(types -> root.get("type").in(types)),
                    Optional.ofNullable(bookMbtiQuestionCriteria.question())
                            .filter(question -> !question.isEmpty())
                            .map(question -> criteriaBuilder.like(root.get("question"), "%" + question + "%"))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}