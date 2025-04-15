package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * PaymentStatistics 엔티티
 * 테이블 정보:
 * - 테이블 명: payment_statistics
 * - 테이블 코멘트: 결제 통계
 */
@Entity
@Table(name = "payment_statistics", uniqueConstraints = {
        @UniqueConstraint(name = "idx_payment_statistics_unique", columnNames = {"subscription_product_no", "coupon_manage_no", "reference_date"})
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class PaymentStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_statistics_no", nullable = false, updatable = false)
    private Integer paymentStatisticsNo;

    @Column(name = "distributor", length = 30)
    private String distributor;

    @Column(name = "subscription_product_no")
    private Integer subscriptionProductNo;

    @Column(name = "subscription_product_type", length = 300)
    private String subscriptionProductType;

    @Column(name = "subscription_product_title", length = 300)
    private String subscriptionProductTitle;

    @Column(name = "coupon_manage_no")
    private Integer couponManageNo;

    @Column(name = "coupon_name", length = 300)
    private String couponName;

    @Column(name = "coupon_use_count")
    private Integer couponUseCount;

    @Column(name = "coupon_discount_price")
    private Integer couponDiscountPrice;

    @Column(name = "payment_count")
    private Integer paymentCount;

    @Column(name = "payment_price")
    private Integer paymentPrice;

    @Column(name = "refund_count")
    private Integer refundCount;

    @Column(name = "refund_price")
    private Integer refundPrice;

    @Column(name = "reference_date", nullable = false)
    private LocalDate referenceDate;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}