package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * CouponHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: coupon_history
 * - 테이블 코멘트: 쿠폰 이력
 */
@Entity
@Table(name = "coupon_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CouponHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_history_no", nullable = false, updatable = false)
    private Integer couponHistoryNo;

    @Column(name = "coupon_no")
    private Integer couponNo;

    @Column(name = "account_id", length = 16)
    private String accountId;

    @Column(name = "account_name", length = 10)
    private String accountName;

    @Column(name = "issue_reason", length = 500)
    private String issueReason;

    @Column(name = "discount_type", length = 15)
    private String discountType;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "discount_price")
    private Integer discountPrice;

    @Column(name = "coupon_number", length = 255)
    private String couponNumber;

    @Column(name = "usage_datetime")
    private LocalDateTime usageDatetime;

    @Column(name = "coupon_status", length = 20)
    private String couponStatus;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;
}