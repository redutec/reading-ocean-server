package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * MltSubscriptionBill 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_subscription_bill
 * - 테이블 코멘트: 구독료 청구서(매월 1일 집계로 생성)
 */
@Entity
@Table(name = "mlt_subscription_bill")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltSubscriptionBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_bill_no", nullable = false, updatable = false)
    private Integer subscriptionBillNo;

    @Column(name = "bill_month", nullable = false, length = 7, columnDefinition = "char(7)")
    @JdbcTypeCode(Types.CHAR)
    private String billMonth;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "account_no", nullable = false, columnDefinition = "int default 0")
    private Integer accountNo;

    @Column(name = "payment_no", nullable = false, columnDefinition = "int default 0")
    private Integer paymentNo;

    @Column(name = "is_manual", nullable = false, columnDefinition = "char default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String isManual;

    @Column(name = "bill_amount", nullable = false)
    private Integer billAmount;

    @Column(name = "use_period", nullable = false, length = 30)
    private String usePeriod;

    @Column(name = "bill_status", nullable = false)
    private Byte billStatus;

    @Column(name = "bill_datetime", nullable = false)
    private LocalDateTime billDatetime;

    @Column(name = "manual_payment_datetime")
    private LocalDateTime manualPaymentDatetime;

    @Column(name = "manual_cancel_datetime")
    private LocalDateTime manualCancelDatetime;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}