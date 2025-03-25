package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * MltMallOrderDetail 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_mall_order_detail
 * - 테이블 코멘트: 온라인 상품 주문 상세
 */
@Entity
@Table(name = "mlt_mall_order_detail", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_mlt_mall_order_detail_cart_product_no", columnNames = {"mall_order_no", "cart_product_no"})
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltMallOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mall_order_detail_no", nullable = false, updatable = false)
    private Integer mallOrderDetailNo;

    @Column(name = "mall_order_no", nullable = false)
    private Integer mallOrderNo;

    @Column(name = "cart_product_no", nullable = false, columnDefinition = "int default (0)")
    private Integer cartProductNo;

    @Column(name = "product_no", nullable = false)
    private Integer productNo;

    @Column(name = "order_quantity", nullable = false)
    private Short orderQuantity;

    @Column(name = "product_amount", nullable = false)
    private Integer productAmount;

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;

    @Column(name = "purchase_amount", nullable = false)
    private Integer purchaseAmount;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}