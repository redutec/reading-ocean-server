package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * MltSubscriptionBillDetail 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_subscription_bill_detail
 * - 테이블 코멘트: 구독료 청구서 상세 (일별 집계로 데이터 생성)
 */
@Entity
@Table(name = "mlt_subscription_bill_detail", indexes = {
        @Index(name = "idx_mlt_subscription_bill_detail_bill_date", columnList = "bill_date")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltSubscriptionBillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_no", nullable = false, updatable = false)
    private Integer billDetailNo;

    @Column(name = "bill_date", nullable = false)
    private LocalDate billDate;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "subscription_student_count", nullable = false)
    private Integer subscriptionStudentCount;

    @Column(name = "subscription_product_no", nullable = false)
    private Integer subscriptionProductNo;

    @Column(name = "subscription_product_price", nullable = false)
    private Integer subscriptionProductPrice;

    @Column(name = "bill_amount", nullable = false)
    private Integer billAmount;

    @Column(name = "subscription_bill_no")
    private Integer subscriptionBillNo;

    @Column(name = "is_minimum_charge", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isMinimumCharge;

    @Column(name = "is_manual_bill", columnDefinition = "tinyint default 0")
    private Boolean isManualBill;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}