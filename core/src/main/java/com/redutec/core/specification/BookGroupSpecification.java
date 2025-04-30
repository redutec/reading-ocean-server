package com.redutec.core.specification;

import com.redutec.core.criteria.BookGroupCriteria;
import com.redutec.core.entity.BookGroup;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class BookGroupSpecification {
    public static Specification<BookGroup> findWith(BookGroupCriteria bookGroupCriteria) {
        return (bookGroupRoot, criteriaQuery, criteriaBuilder) -> {
            Stream<Optional<Predicate>> stream = Stream.of(
                    Optional.ofNullable(bookGroupCriteria.bookGroupIds())
                            .filter(bookGroupIds -> !bookGroupIds.isEmpty())
                            .map(bookGroupIds -> bookGroupRoot.get("id").in(bookGroupIds)),
                    Optional.ofNullable(bookGroupCriteria.name())
                            .map(name -> criteriaBuilder.like(bookGroupRoot.get("name"), "%" + name + "%")),
                    Optional.ofNullable(bookGroupCriteria.yearMonth())
                            .map(yearMonth -> criteriaBuilder.like(bookGroupRoot.get("yearMonth"), "%" + yearMonth + "%")),
                    Optional.ofNullable(bookGroupCriteria.types())
                            .map(types -> bookGroupRoot.get("type").in(types)),
                    Optional.ofNullable(bookGroupCriteria.schoolGrades())
                            .map(schoolGrades -> bookGroupRoot.get("schoolGrade").in(schoolGrades))
            );
            return combinePredicate(stream, criteriaBuilder);
        };
    }
}