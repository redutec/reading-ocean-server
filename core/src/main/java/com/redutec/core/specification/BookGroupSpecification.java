package com.redutec.core.specification;

import com.redutec.core.criteria.BookGroupCriteria;
import com.redutec.core.entity.BookGroup;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class BookGroupSpecification {
    public static Specification<BookGroup> findWith(BookGroupCriteria criteria) {
        return (bookGroupRoot, criteriaQuery, criteriaBuilder) -> {
            Stream<Optional<Predicate>> stream = Stream.of(
                    Optional.ofNullable(criteria.groupIds())
                            .filter(groupIds -> !groupIds.isEmpty())
                            .map(groupIds -> bookGroupRoot.get("id").in(groupIds)),
                    Optional.ofNullable(criteria.yearMonth())
                            .map(yearMonth -> criteriaBuilder.equal(bookGroupRoot.get("yearMonth"), yearMonth)),
                    Optional.ofNullable(criteria.type())
                            .map(type -> criteriaBuilder.equal(bookGroupRoot.get("type"), type)),
                    Optional.ofNullable(criteria.schoolGrade())
                            .map(schoolGrade -> criteriaBuilder.equal(bookGroupRoot.get("schoolGrade"), schoolGrade)),
                    Optional.ofNullable(criteria.bookIds())
                            .filter(bookIds -> !bookIds.isEmpty())
                            .map(bookIds -> {
                                Objects.requireNonNull(criteriaQuery).distinct(true);
                                return bookGroupRoot.join("books").get("id").in(bookIds);
                            })
            );
            return combinePredicate(stream, criteriaBuilder);
        };
    }
}