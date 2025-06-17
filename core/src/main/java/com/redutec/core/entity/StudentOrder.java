package com.redutec.core.entity;

import com.redutec.core.meta.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Comment("상품주문(학생)")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class StudentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품주문(학생) 고유번호")
    private Long id;

    @Comment("상품을 주문한 학생")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Student student;

    @ElementCollection
    @CollectionTable(
            name = "student_order_item",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    )
    @Comment("주문 상품 목록")
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    @Comment("배송료")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer deliveryFee;

    @Comment("주문 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.ORDERED;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 주문에 상품을 추가합니다.
     * - 이미 존재하는 상품이면 수량을 합산합니다.
     * - 유효 수량(1 이상)이 아니면 예외를 발생시킵니다.
     *
     * @param product 추가할 상품
     * @param quantity 추가 수량
     */
    public void addItem(Product product, Integer quantity) {
        Integer validQuantity = Optional.ofNullable(quantity)
                .filter(q -> q > 0)
                .orElseThrow(() -> new IllegalArgumentException("수량은 1 이상이어야 합니다."));
        this.items.stream()
                .filter(orderItem -> orderItem.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                        orderItem -> orderItem.changeQuantity(orderItem.getQuantity() + validQuantity),
                        () -> items.add(
                                OrderItem.builder()
                                        .product(product)
                                        .quantity(validQuantity)
                                        .unitPrice(product.getPrice() * (100 - product.getDiscountPercentage()) / 100)
                                        .build()
                        )
                );
    }

    /**
     * 주문 목록에서 특정 상품을 제거합니다.
     *
     * @param productId 제거할 상품 ID
     */
    public void removeItem(Long productId) {
        this.items.removeIf(orderItem -> orderItem.getProduct().getId().equals(productId));
    }

    /**
     * 특정 상품의 주문 수량을 변경합니다.
     * - 수량이 null이거나 1 미만이면 해당 상품을 제거합니다.
     *
     * @param productId 수량을 변경할 상품 ID
     * @param quantity  변경할 수량
     */
    public void updateItemQuantity(Long productId, Integer quantity) {
        Optional.ofNullable(quantity)
                .filter(q -> q > 0)
                .ifPresentOrElse(
                        validQty -> this.items.stream()
                                .filter(i -> i.getProduct().getId().equals(productId))
                                .findFirst()
                                .ifPresent(i -> i.changeQuantity(validQty)),
                        () -> removeItem(productId)
                );
    }

    /**
     * 모든 주문 상품을 제거합니다.
     */
    public void clear() {
        this.items.clear();
    }

    /**
     * 주문 총액을 계산해 반환합니다.
     * - items 목록의 수량×단가 합계입니다.
     *
     * @return 총 주문 금액
     */
    @Transient
    public Integer getPurchaseAmount() {
        return this.items.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    /**
     * 주어진 학생용 장바구니(StudentCart)를 기반으로
     * 새로운 학생 주문(StudentOrder) 엔티티를 생성합니다.
     * - 장바구니에 담긴 각 CartItem을 OrderItem으로 변환하여 주문에 추가합니다.
     * - 주문 생성 시점의 배송정보와 배송료를 설정합니다.
     *
     * @param studentCart 주문 생성의 기준이 될 학생 장바구니
     * @param deliveryFee   이 주문에 부과할 배송비
     * @return 생성된 StudentOrder 엔티티 (아직 저장 전 상태)
     */
    public static StudentOrder fromCart(
            StudentCart studentCart,
            Integer deliveryFee
    ) {
        StudentOrder order = StudentOrder.builder()
                .student(studentCart.getStudent())
                .deliveryFee(deliveryFee)
                .build();
        // 장바구니의 아이템들을 모두 주문으로 복사
        studentCart.getItems().forEach(cartItem ->
                order.addItem(cartItem.getProduct(), cartItem.getQuantity())
        );
        return order;
    }
}