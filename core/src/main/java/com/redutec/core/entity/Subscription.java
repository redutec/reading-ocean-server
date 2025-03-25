package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Subscription 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription
 * - 테이블 코멘트: 구독
 */
@Entity
@Table(name = "subscription", indexes = {
        @Index(name = "idx_subscription_account_no", columnList = "account_no"),
        @Index(name = "idx_subscription_next_payment_date", columnList = "next_payment_date"),
        @Index(name = "idx_subscription_subscription_product_no", columnList = "subscription_product_no"),
        @Index(name = "idx_subscription_subscription_start_date_subscription_end_date", columnList = "subscription_start_date, subscription_end_date")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_no", nullable = false, updatable = false)
    private Integer subscriptionNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "subscription_product_no", nullable = false)
    private Integer subscriptionProductNo;

    @Column(name = "subscription_start_date", nullable = false)
    private LocalDate subscriptionStartDate;

    @Column(name = "subscription_end_date", nullable = false)
    private LocalDate subscriptionEndDate;

    @Column(name = "next_payment_date")
    private LocalDate nextPaymentDate;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}