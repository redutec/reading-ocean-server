package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * PaymentOrder 엔티티
 * 테이블 정보:
 * - 테이블 명: payment_order
 * - 테이블 코멘트: 결제 정보
 */
@Entity
@Table(name = "payment_order", indexes = {
        @Index(name = "idx_payment_order_account_no", columnList = "account_no"),
        @Index(name = "idx_payment_order_coupon_no", columnList = "coupon_no"),
        @Index(name = "idx_payment_order_payment_date", columnList = "payment_date"),
        @Index(name = "idx_payment_order_payment_method_no", columnList = "payment_method_no"),
        @Index(name = "idx_payment_order_subscription_no", columnList = "subscription_no"),
        @Index(name = "idx_payment_order_subscription_product_no", columnList = "subscription_product_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_order_no", nullable = false, updatable = false)
    private Integer paymentOrderNo;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "type", length = 30)
    private String type;

    @Column(name = "payment_method_no")
    private Integer paymentMethodNo;

    @Column(name = "coupon_no")
    private Integer couponNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "subscription_product_no")
    private Integer subscriptionProductNo;

    @Column(name = "subscription_no")
    private Integer subscriptionNo;

    @Column(name = "coupon_discount_price")
    private Integer couponDiscountPrice;

    @Column(name = "order_price")
    private Integer orderPrice;

    @Column(name = "payment_price")
    private Integer paymentPrice;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "refund_price")
    private Integer refundPrice;

    @Column(name = "refund_date")
    private LocalDateTime refundDate;

    @Column(name = "tid", length = 400)
    private String tid;

    @Column(name = "subscription_round_count")
    private Integer subscriptionRoundCount;

    @Column(name = "result_code", length = 200)
    private String resultCode;

    @Column(name = "result_msg", length = 2000)
    private String resultMsg;

    @Column(name = "memo", length = 400)
    private String memo;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "receipt_url", length = 2000)
    private String receiptUrl;

    @Column(name = "subscription_pass_item_no")
    private Integer subscriptionPassItemNo;
}