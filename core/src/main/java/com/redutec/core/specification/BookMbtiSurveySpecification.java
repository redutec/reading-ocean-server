package com.redutec.core.specification;

import com.redutec.core.criteria.BookMbtiSurveyCriteria;
import com.redutec.core.entity.BookMbtiSurvey;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class BookMbtiSurveySpecification {
    public static Specification<BookMbtiSurvey> findWith(BookMbtiSurveyCriteria bookMbtiSurveyCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(bookMbtiSurveyCriteria.bookMbtiSurveyIds())
                            .filter(bookMbtiSurveyIds -> !bookMbtiSurveyIds.isEmpty())
                            .map(bookMbtiSurveyIds -> root.get("id").in(bookMbtiSurveyIds)),
                    Optional.ofNullable(bookMbtiSurveyCriteria.studentIds())
                            .filter(studentIds -> !studentIds.isEmpty())
                            .map(studentIds -> root.get("student").get("id").in(studentIds)),
                    Optional.ofNullable(bookMbtiSurveyCriteria.bookMbtiResults())
                            .filter(bookMbtiResults -> !bookMbtiResults.isEmpty())
                            .map(bookMbtiResults -> root.get("result").in(bookMbtiResults))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}