package com.redutec.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Optional;

@Entity
@Comment("상품주문 항목")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품주문 항목의 고유번호")
    private Long id;

    @Comment("소속된 상품주문 엔티티")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Order order;

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
     * 새로운 주문 항목을 생성합니다.
     * - 단가(unitPrice)는 상품의 현재 가격을 기준으로 설정됩니다.
     * - 수량은 반드시 1 이상이어야 합니다.
     */
    public static OrderItem createOrderItem(Order order, Product product, Integer quantity) {
        int validQuantity = Optional.ofNullable(quantity)
                .filter(q -> q > 0)
                .orElseThrow(() -> new IllegalArgumentException("수량은 반드시 1 이상이어야 합니다."));
        return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(validQuantity)
                .unitPrice(product.getPrice())
                .build();
    }

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