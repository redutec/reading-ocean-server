package com.redutec.core.specification;

import com.redutec.core.criteria.InquiryCriteria;
import com.redutec.core.entity.Inquiry;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class InquirySpecification {
    public static Specification<Inquiry> findWith(InquiryCriteria inquiryCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(inquiryCriteria.inquiryIds())
                            .filter(inquiryIds -> !inquiryIds.isEmpty())
                            .map(inquiryIds -> root.get("id").in(inquiryIds)),
                    Optional.ofNullable(inquiryCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains)),
                    Optional.ofNullable(inquiryCriteria.inquirerTypes())
                            .filter(inquirerTypes -> !inquirerTypes.isEmpty())
                            .map(inquirerTypes -> root.get("inquirerType").in(inquirerTypes)),
                    Optional.ofNullable(inquiryCriteria.categories())
                            .filter(categories -> !categories.isEmpty())
                            .map(categories -> root.get("category").in(categories)),
                    Optional.ofNullable(inquiryCriteria.statuses())
                            .filter(statuses -> !statuses.isEmpty())
                            .map(statuses -> root.get("status").in(statuses)),
                    Optional.ofNullable(inquiryCriteria.inquirerEmail())
                            .filter(inquirerEmail -> !inquirerEmail.isEmpty())
                            .map(inquirerEmail -> criteriaBuilder.equal(root.get("inquirerEmail"), inquirerEmail)),
                    Optional.ofNullable(inquiryCriteria.responderNickname())
                            .filter(responderNickname -> !responderNickname.isEmpty())
                            .map(responderNickname -> criteriaBuilder.like(root.get("adminUser").get("nickname"), "%" + responderNickname + "%")),
                    Optional.ofNullable(inquiryCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(inquiryCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(inquiryCriteria.response())
                            .filter(response -> !response.isEmpty())
                            .map(response -> criteriaBuilder.like(root.get("response"), "%" + response + "%"))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}