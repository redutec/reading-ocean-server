package com.redutec.core.specification;

import com.redutec.core.criteria.BookCriteria;
import com.redutec.core.entity.Book;
import com.redutec.core.meta.SchoolGrade;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class BookSpecification {
    public static Specification<Book> findWith(BookCriteria bookCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(bookCriteria.bookIds())
                            .filter(bookIds -> !bookIds.isEmpty())
                            .map(bookIds -> root.get("id").in(bookIds)),
                    Optional.ofNullable(bookCriteria.isbn())
                            .filter(isbn -> !isbn.isEmpty())
                            .map(isbn -> criteriaBuilder.like(root.get("isbn"), "%" + isbn + "%")),
                    Optional.ofNullable(bookCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(bookCriteria.author())
                            .filter(author -> !author.isEmpty())
                            .map(author -> criteriaBuilder.like(root.get("author"), "%" + author + "%")),
                    Optional.ofNullable(bookCriteria.publisher())
                            .filter(publisher -> !publisher.isEmpty())
                            .map(publisher -> criteriaBuilder.like(root.get("publisher"), "%" + publisher + "%")),
                    Optional.ofNullable(bookCriteria.recommended())
                            .map(recommended -> criteriaBuilder.equal(root.get("recommended"), recommended)),
                    Optional.ofNullable(bookCriteria.ebookAvailable())
                            .map(ebookAvailable -> criteriaBuilder.equal(root.get("ebookAvailable"), ebookAvailable)),
                    Optional.ofNullable(bookCriteria.audiobookAvailable())
                            .map(audiobookAvailable -> criteriaBuilder.equal(root.get("audiobookAvailable"), audiobookAvailable)),
                    Optional.ofNullable(bookCriteria.visible())
                            .map(visible -> criteriaBuilder.equal(root.get("visible"), visible)),
                    Optional.ofNullable(bookCriteria.enabled())
                            .map(enabled -> criteriaBuilder.equal(root.get("enabled"), enabled)),
                    Optional.ofNullable(bookCriteria.minimumPageCount())
                            .map(minimumPageCount -> criteriaBuilder.greaterThanOrEqualTo(root.get("pageCount"), minimumPageCount)),
                    Optional.ofNullable(bookCriteria.maximumPageCount())
                            .map(maximumPageCount -> criteriaBuilder.lessThanOrEqualTo(root.get("pageCount"), maximumPageCount)),
                    Optional.ofNullable(bookCriteria.minimumSchoolGrade())
                            .map(minimumOrder -> {
                                List<SchoolGrade> schoolGrades = Arrays.stream(SchoolGrade.values())
                                        .filter(schoolGrade -> schoolGrade.getOrder() >= minimumOrder)
                                        .toList();
                                return root.get("schoolGrade").in(schoolGrades);
                            }),
                    Optional.ofNullable(bookCriteria.maximumSchoolGrade())
                            .map(maximumOrder -> {
                                List<SchoolGrade> schoolGrades = Arrays.stream(SchoolGrade.values())
                                        .filter(schoolGrade -> schoolGrade.getOrder() <= maximumOrder)
                                        .toList();
                                return root.get("schoolGrade").in(schoolGrades);
                            }),
                    Optional.ofNullable(bookCriteria.genres())
                            .filter(genres -> !genres.isEmpty())
                            .map(genres -> root.get("genre").in(genres)),
                    Optional.ofNullable(bookCriteria.subGenres())
                            .filter(subGenres -> !subGenres.isEmpty())
                            .map(subGenres -> root.get("subGenre").in(subGenres)),
                    Optional.ofNullable(bookCriteria.minimumBookPoints())
                            .map(minimumBookPoints -> criteriaBuilder.greaterThanOrEqualTo(root.get("bookPoints"), minimumBookPoints)),
                    Optional.ofNullable(bookCriteria.maximumBookPoints())
                            .map(maximumBookPoints -> criteriaBuilder.lessThanOrEqualTo(root.get("bookPoints"), maximumBookPoints)),
                    Optional.ofNullable(bookCriteria.minimumRaq())
                            .map(minimumRaq -> criteriaBuilder.greaterThanOrEqualTo(root.get("raq"), minimumRaq)),
                    Optional.ofNullable(bookCriteria.maximumRaq())
                            .map(maximumRaq -> criteriaBuilder.lessThanOrEqualTo(root.get("raq"), maximumRaq)),
                    Optional.ofNullable(bookCriteria.bookMbtiList())
                            .filter(bookMbtiList -> !bookMbtiList.isEmpty())
                            .map(bookMbtiList -> root.get("bookMbti").in(bookMbtiList)),
                    Optional.ofNullable(bookCriteria.subject())
                            .filter(subject -> !subject.isEmpty())
                            .map(subject -> criteriaBuilder.like(root.get("subject"), "%" + subject + "%")),
                    Optional.ofNullable(bookCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(bookCriteria.tags())
                            .filter(tags -> !tags.isEmpty())
                            .map(tags -> {
                                Predicate[] predicates = tags.stream()
                                        .map(tag -> criteriaBuilder.isMember(tag, root.get("tags")))
                                        .toArray(Predicate[]::new);
                                return criteriaBuilder.or(predicates);
                            })
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}