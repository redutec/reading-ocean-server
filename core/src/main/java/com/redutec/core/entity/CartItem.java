package com.redutec.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Comment("장바구니에 담긴 상품")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("장바구니에 담긴 상품 엔티티의 고유번호")
    private Long id;

    @Comment("장바구니 엔티티")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Cart cart;

    @Comment("상품")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Product product;

    @Comment("수량")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;

    @Comment("단가")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer unitPrice;

    /**
     * 이 아이템의 수량을 변경합니다.
     * - quantity <= 0 이면 삭제 로직을 호출하도록 서비스 계층에 알릴 수도 있습니다.
     */
    public void changeQuantity(Integer newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("수량은 반드시 1 이상이어야 합니다.");
        }
        this.quantity = newQuantity;
    }

    /**
     * 이 아이템의 총 가격(수량 × 단가)을 계산해 반환합니다.
     */
    public Integer getTotalPrice() {
        return this.quantity * this.unitPrice;
    }
}