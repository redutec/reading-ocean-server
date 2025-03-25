package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * SubscriptionProduct 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_product
 * - 테이블 코멘트: 구독 상품
 */
@Entity
@Table(name = "subscription_product")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_product_no", nullable = false, updatable = false)
    private Integer subscriptionProductNo;

    @Column(name = "subscription_type", length = 15)
    private String subscriptionType;

    @Column(name = "subscription_category", length = 15)
    private String subscriptionCategory;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discount_rate", columnDefinition = "int default 0")
    private Integer discountRate;

    @Column(name = "discount_type", length = 15)
    private String discountType;

    @Column(name = "payment_price")
    private Integer paymentPrice;

    @Column(name = "subscription_period")
    private Integer subscriptionPeriod;

    @Column(name = "payment_period")
    private Integer paymentPeriod;

    @Column(name = "applicable_coupon_status", length = 30)
    private String applicableCouponStatus;

    @Column(name = "product_status", length = 30)
    private String productStatus;

    @Column(name = "display_order")
    private Integer displayOrder;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "refund_type", length = 20, columnDefinition = "varchar(20) default 'NONE'")
    private String refundType;

    @Column(name = "display_status", length = 15, columnDefinition = "varchar(15) default 'HIDE'")
    private String displayStatus;

    @Column(name = "service_type", length = 20)
    private String serviceType;
}