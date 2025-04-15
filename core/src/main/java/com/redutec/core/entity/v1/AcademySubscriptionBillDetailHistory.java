package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 테이블 정보:
 * - 테이블 명: academy_subscription_bill_detail_history
 * - 테이블 코멘트: 학원별 일별 학생수 집계 내역 (mlt_subscription_bill_detail 기준)
 */
@Entity
@Table(name = "academy_subscription_bill_detail_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AcademySubscriptionBillDetailHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "academy_subscription_bill_detail_history_no", nullable = false)
    private Integer academySubscriptionBillDetailHistoryNo;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "academy_name", length = 100)
    private String academyName;

    @Column(name = "bill_date", length = 20)
    private String billDate;

    @Column(name = "total_student_count")
    private Integer totalStudentCount;

    @Column(name = "active_student_count")
    private Integer activeStudentCount;

    @Column(name = "inactive_student_count")
    private Integer inactiveStudentCount;

    @Column(name = "wait_student_count")
    private Integer waitStudentCount;

    @Column(name = "withdrawal_student_count")
    private Integer withdrawalStudentCount;

    @Column(name = "withdrawal_request_student_count")
    private Integer withdrawalRequestStudentCount;

    @Column(name = "dormant_student_count")
    private Integer dormantStudentCount;

    @Column(name = "test_student_count")
    private Integer testStudentCount;

    @Column(name = "subscription_product_no")
    private Integer subscriptionProductNo;

    @Column(name = "subscription_product_name", length = 30)
    private String subscriptionProductName;

    @Column(name = "subscription_product_price")
    private Integer subscriptionProductPrice;

    @Column(name = "total_subscription_product_price")
    private Integer totalSubscriptionProductPrice;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}