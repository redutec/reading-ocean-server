package com.redutec.core.entity;

import jakarta.persistence.*;
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
public class CartInstitute {
    /**
     * Institute를 PK로 사용.
     */
    @Id
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ElementCollection
    @CollectionTable(
        name = "cart_item",
        joinColumns = @JoinColumn(name = "institute_id", referencedColumnName = "institute_id")
    )
    @Comment("장바구니에 담긴 상품 목록")
    private List<CartItem> items = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void addItem(Product product, Integer quantity) {
        items.stream()
                .filter(ci -> ci.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                    ci -> ci.changeQuantity(ci.getQuantity() + quantity),
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
