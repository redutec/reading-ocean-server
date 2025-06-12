package com.redutec.core.specification;

import com.redutec.core.criteria.TeacherInquiryCriteria;
import com.redutec.core.entity.TeacherInquiry;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class TeacherInquirySpecification {
    public static Specification<TeacherInquiry> findWith(TeacherInquiryCriteria teacherInquiryCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(teacherInquiryCriteria.teacherInquiryIds())
                            .filter(teacherInquiryIds -> !teacherInquiryIds.isEmpty())
                            .map(teacherInquiryIds -> root.get("id").in(teacherInquiryIds)),
                    Optional.ofNullable(teacherInquiryCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains)),
                    Optional.ofNullable(teacherInquiryCriteria.categories())
                            .filter(categories -> !categories.isEmpty())
                            .map(categories -> root.get("category").in(categories)),
                    Optional.ofNullable(teacherInquiryCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(teacherInquiryCriteria.teacherAccountId())
                            .filter(teacherAccountId -> !teacherAccountId.isEmpty())
                            .map(teacherAccountId -> criteriaBuilder.equal(root.get("teacher").get("accountId"), teacherAccountId)),
                    Optional.ofNullable(teacherInquiryCriteria.responderAccountId())
                            .filter(responderAccountId -> !responderAccountId.isEmpty())
                            .map(responderAccountId -> criteriaBuilder.like(root.get("responder").get("accountId"), "%" + responderAccountId + "%")),
                    Optional.ofNullable(teacherInquiryCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(teacherInquiryCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(teacherInquiryCriteria.response())
                            .filter(response -> !response.isEmpty())
                            .map(response -> criteriaBuilder.like(root.get("response"), "%" + response + "%"))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}