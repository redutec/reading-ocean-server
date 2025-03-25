package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * SubscriptionOrder 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_order
 * - 테이블 코멘트: 구독 주문
 */
@Entity
@Table(name = "subscription_order", indexes = {
        @Index(name = "idx_subscription_order_account_no", columnList = "account_no"),
        @Index(name = "idx_subscription_order_subscription_no", columnList = "subscription_no"),
        @Index(name = "idx_subscription_order_subscription_product_no", columnList = "subscription_product_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_order_no", nullable = false, updatable = false)
    private Integer subscriptionOrderNo;

    @Column(name = "subscription_no")
    private Integer subscriptionNo;

    @Column(name = "subscription_product_no")
    private Integer subscriptionProductNo;

    @Column(name = "account_no")
    private Integer accountNo;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "status", length = 20)
    private String status;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}