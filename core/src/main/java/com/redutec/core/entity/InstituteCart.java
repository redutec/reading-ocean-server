package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 교육기관 당 1:1 매핑된 장바구니.
 * Institute를 곧바로 PK로 사용하도록 변경
 */
@Entity
@Comment("장바구니(교육기관)")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@SuperBuilder
public class InstituteCart {
    /** 실제 PK 컬럼 */
    @Id
    @Column(name = "institute_id")
    private Long instituteId;

    /** PK를 공유하도록 MapsId */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ElementCollection
    @CollectionTable(
            name = "institute_cart_item",
            joinColumns = @JoinColumn(name = "institute_id", referencedColumnName = "institute_id")
    )
    @Comment("장바구니에 담긴 상품 목록")
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void addItem(Product product, Integer quantity) {
        items.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                        cartItem -> cartItem.changeQuantity(cartItem.getQuantity() + quantity),
                        () -> items.add(
                                CartItem.builder()
                                        .product(product)
                                        .quantity(quantity)
                                        .unitPrice(product.getPrice() * (100 - product.getDiscountPercentage()) / 100)
                                        .build()
                        )
                );
    }
}