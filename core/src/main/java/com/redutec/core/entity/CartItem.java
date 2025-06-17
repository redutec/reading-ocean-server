package com.redutec.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.Optional;

@Comment("장바구니에 담긴 상품 정보")
@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartItem {
    @EqualsAndHashCode.Include
    @Comment("상품 엔티티")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Product product;

    @Comment("수량")
    @Column(nullable = false)
    @Positive
    private Integer quantity;

    @Comment("단가(할인율 적용)")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer unitPrice;

    /**
     * 수량 변경 시 검증
     */
    public void changeQuantity(Integer newQuantity) {
        this.quantity = Optional.ofNullable(newQuantity)
                .filter(q -> q > 0)
                .orElseThrow(() -> new IllegalArgumentException("수량은 1 이상이어야 합니다."));
    }

    /**
     * 총 가격 계산
     */
    public Integer getTotalPrice() {
        return this.quantity * this.unitPrice;
    }
}