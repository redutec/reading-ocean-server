package com.redutec.core.entity;

import com.redutec.core.meta.ProductCategory;
import com.redutec.core.meta.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Comment("판매상품")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("판매상품 고유번호")
    private Long id;

    @Comment("상품명")
    @Column(nullable = false, length = 100)
    private String name;

    @Comment("설명")
    @Column(length = 1000)
    private String details;

    @Comment("가격")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer price;

    @Comment("할인율")
    @Column
    @PositiveOrZero
    @Max(100)
    private Integer discountPercentage;

    @Comment("판매상품에 대한 정보가 담긴 파일명")
    @Column
    private String attachedFileName;

    @Comment("분류")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category = ProductCategory.POWER_BOOK;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateProduct(
            String name,
            String details,
            Integer price,
            Integer discountPercentage,
            String attachedFileName,
            ProductCategory category,
            ProductStatus status
    ) {
        this.name = Optional.ofNullable(name).orElse(this.name);
        this.details = Optional.ofNullable(details).orElse(this.details);
        this.price = Optional.ofNullable(price).orElse(this.price);
        this.discountPercentage = Optional.ofNullable(discountPercentage).orElse(this.discountPercentage);
        this.attachedFileName = Optional.ofNullable(attachedFileName).orElse(this.attachedFileName);
        this.category = Optional.ofNullable(category).orElse(this.category);
        this.status = Optional.ofNullable(status).orElse(this.status);
    }
}