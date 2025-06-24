package com.redutec.core.entity;

import com.redutec.core.meta.ProductCategory;
import com.redutec.core.meta.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

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
    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    @Comment("설명")
    @Setter
    @Column(length = 1000)
    private String details;

    @Comment("가격")
    @Setter
    @Column(nullable = false)
    @PositiveOrZero
    private Integer price;

    @Comment("할인율")
    @Setter
    @Column
    @PositiveOrZero
    @Max(100)
    private Integer discountPercentage;

    @Comment("판매상품에 대한 정보가 담긴 파일명")
    @Setter
    @Column
    private String attachmentFileName;

    @Comment("분류")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category = ProductCategory.POWER_BOOK;

    @Comment("상태")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}