package com.redutec.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.Optional;

@Comment("상품주문에 담길 상품 정보")
@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItem {
    @Comment("주문한 상품")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Product product;

    @Comment("수량")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;

    @Comment("단가(주문 시점)")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer unitPrice;

    /**
     * 이 항목의 수량을 변경합니다.
     * - 수량은 반드시 1 이상이어야 합니다.
     */
    public void changeQuantity(Integer quantity) {
        this.quantity = Optional.ofNullable(quantity)
                .filter(q -> q > 0)
                .orElseThrow(() -> new IllegalArgumentException("수량은 반드시 1 이상이어야 합니다."));
    }

    /**
     * 이 항목의 총 가격(수량 × 단가)을 계산해 반환합니다.
     */
    public Integer getTotalPrice() {
        return this.quantity * this.unitPrice;
    }
}