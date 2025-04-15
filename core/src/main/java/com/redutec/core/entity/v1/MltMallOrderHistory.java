package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * MltMallOrderHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_mall_order_history
 * - 테이블 코멘트: 온라인 상품 주문 이력
 */
@Entity
@Table(name = "mlt_mall_order_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltMallOrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_no", nullable = false, updatable = false)
    private Integer historyNo;

    @Column(name = "mall_order_no", nullable = false)
    private Integer mallOrderNo;

    @Column(name = "order_status", nullable = false)
    private Byte orderStatus;

    @Column(name = "product_amount", nullable = false)
    private Integer productAmount;

    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;

    @Column(name = "apply_delivery_fee", nullable = false)
    private Integer applyDeliveryFee;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}