package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Payment 엔티티
 * 테이블 정보:
 * - 테이블 명: payment
 * - 테이블 코멘트: 결제 정보
 */
@Entity
@Table(name = "payment")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_no", nullable = false, updatable = false)
    private Integer paymentNo;

    @Column(name = "payment_order_no", nullable = false)
    private Integer paymentOrderNo;

    @Column(name = "status", length = 50)
    private String status;

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

    @Column(name = "result_code", length = 200)
    private String resultCode;

    @Column(name = "result_msg", length = 2000)
    private String resultMsg;

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
}