package com.redutec.core.specification;

import com.redutec.core.criteria.BannerCriteria;
import com.redutec.core.entity.Banner;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.stream.Stream;

import static com.redutec.core.config.SpecificationUtil.combinePredicate;

public class BannerSpecification {
    public static Specification<Banner> findWith(BannerCriteria bannerCriteria) {
        return (root, query, criteriaBuilder) -> {
            // 각 조건에 맞는 Optional<Predicate> 생성
            Stream<Optional<Predicate>> predicateStream = Stream.of(
                    Optional.ofNullable(bannerCriteria.bannerIds())
                            .filter(bannerIds -> !bannerIds.isEmpty())
                            .map(bannerIds -> root.get("id").in(bannerIds)),
                    Optional.ofNullable(bannerCriteria.domains())
                            .filter(domains -> !domains.isEmpty())
                            .map(domains -> root.get("domain").in(domains)),
                    Optional.ofNullable(bannerCriteria.title())
                            .filter(title -> !title.isEmpty())
                            .map(title -> criteriaBuilder.like(root.get("title"), "%" + title + "%")),
                    Optional.ofNullable(bannerCriteria.content())
                            .filter(content -> !content.isEmpty())
                            .map(content -> criteriaBuilder.like(root.get("content"), "%" + content + "%")),
                    Optional.ofNullable(bannerCriteria.visible())
                            .map(visible -> criteriaBuilder.equal(root.get("visible"), visible))
            );
            // 수집된 Predicate 들을 하나로 결합
            return combinePredicate(predicateStream, criteriaBuilder);
        };
    }
}