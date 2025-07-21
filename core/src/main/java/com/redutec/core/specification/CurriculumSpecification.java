package com.redutec.core.specification;

import com.redutec.core.criteria.CurriculumCriteria;
import com.redutec.core.entity.Book;
import com.redutec.core.entity.Curriculum;
import com.redutec.core.entity.CurriculumBook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class CurriculumSpecification {
    public static Specification<Curriculum> findWith(CurriculumCriteria curriculumCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(curriculumCriteria.curriculumIds())
                            .filter(curriculumIds -> !curriculumIds.isEmpty())
                            .map(curriculumIds -> root.get("id").in(curriculumIds)),
                    Optional.ofNullable(curriculumCriteria.studentIds())
                            .filter(studentIds -> !studentIds.isEmpty())
                            .map(studentIds -> root.get("student").get("id").in(studentIds)),
                    Optional.ofNullable(curriculumCriteria.name())
                            .filter(name -> !name.isEmpty())
                            .map(name -> criteriaBuilder.like(root.get("name"), "%" + name + "%")),
                    Optional.ofNullable(curriculumCriteria.description())
                            .filter(description -> !description.isEmpty())
                            .map(description -> criteriaBuilder.like(root.get("description"), "%" + description + "%")),
                    Optional.ofNullable(curriculumCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(curriculumCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> {
                                Join<Curriculum, CurriculumBook> curriculumBook = root.join("curriculumBooks", JoinType.LEFT);
                                Join<CurriculumBook, Book> book = curriculumBook.join("book", JoinType.LEFT);
                                return criteriaBuilder.like(book.get("title"), "%" + title + "%");
                            })
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}