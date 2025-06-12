package com.redutec.core.specification;

import com.redutec.core.criteria.StudentInquiryCriteria;
import com.redutec.core.entity.StudentInquiry;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class StudentInquirySpecification {
    public static Specification<StudentInquiry> findWith(StudentInquiryCriteria studentInquiryCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(studentInquiryCriteria.studentInquiryIds())
                            .filter(studentInquiryIds -> !studentInquiryIds.isEmpty())
                            .map(studentInquiryIds -> root.get("id").in(studentInquiryIds)),
                    Optional.ofNullable(studentInquiryCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains)),
                    Optional.ofNullable(studentInquiryCriteria.categories())
                            .filter(categories -> !categories.isEmpty())
                            .map(categories -> root.get("category").in(categories)),
                    Optional.ofNullable(studentInquiryCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(studentInquiryCriteria.studentAccountId())
                            .filter(studentAccountId -> !studentAccountId.isEmpty())
                            .map(studentAccountId -> criteriaBuilder.equal(root.get("student").get("accountId"), studentAccountId)),
                    Optional.ofNullable(studentInquiryCriteria.responderAccountId())
                            .filter(responderAccountId -> !responderAccountId.isEmpty())
                            .map(responderAccountId -> criteriaBuilder.like(root.get("responder").get("accountId"), "%" + responderAccountId + "%")),
                    Optional.ofNullable(studentInquiryCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(studentInquiryCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(studentInquiryCriteria.response())
                            .filter(response -> !response.isEmpty())
                            .map(response -> criteriaBuilder.like(root.get("response"), "%" + response + "%"))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}