package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
import java.util.Optional;

@Entity
@Comment("장바구니(공통)")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "cart_type", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("장바구니 고유번호")
    private Long id;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Comment("장바구니 상품 목록")
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 장바구니에 상품을 추가합니다.
     * - 이미 담긴 상품이면 수량을 합산하고, 없으면 새 상품을 생성합니다.
     */
    public void addItem(Product product, Integer quantity) {
        Integer validQuantity = Optional.ofNullable(quantity)
                .filter(q -> q > 0)
                .orElseThrow(() -> new IllegalArgumentException("수량은 1 이상이어야 합니다."));
        this.items.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.changeQuantity(item.getQuantity() + validQuantity),
                        () -> this.items.add(
                                CartItem.builder()
                                        .cart(this)
                                        .product(product)
                                        .quantity(validQuantity)
                                        .unitPrice(product.getPrice())
                                        .build()
                        )
                );
    }

    /**
     * 장바구니에서 특정 상품을 제거합니다.
     * - 상품이 존재하지 않으면 무시하거나 예외를 던집니다.
     */
    public void removeItem(Long productId) {
        this.items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    /**
     * 장바구니에 담긴 특정 상품의 수량을 변경합니다.
     * - quantity <= 0 이면 해당 상품을 제거합니다.
     */
    public void updateItemQuantity(Long productId, Integer quantity) {
        Optional.ofNullable(quantity)
                // 1) null이거나 0 이하인 경우 filter에서 걸러져 else로 넘어갑니다.
                .filter(q -> q > 0)
                .ifPresentOrElse(
                        validQuantity ->
                                // 2) 유효 수량이면 해당 아이템 찾아 수량 변경
                                this.items.stream()
                                        .filter(item -> item.getProduct().getId().equals(productId))
                                        .findFirst()
                                        .ifPresent(item -> item.changeQuantity(validQuantity)),
                        // 3) null이거나 0 이하인 경우 removeItem 호출
                        () -> removeItem(productId)
                );
    }

    /**
     * 장바구니를 완전히 비웁니다.
     * - 체크아웃 후 장바구니 초기화 등에 사용합니다.
     */
    public void clear() {
        this.items.clear();
    }

    /**
     * 장바구니에 담긴 모든 상품의 총 합계 금액(수량×단가)을 계산해 반환합니다.
     */
    public Integer calculateTotalAmount() {
        return this.items.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
    }

    /**
     * 장바구니에 담긴 전체 상품 개수를 반환합니다.
     * - quantity 합산이 될 수도 있고, 상품 개수 자체를 셀 수도 있습니다.
     */
    public Integer countTotalItems() {
        return this.items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}