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
 * MltPaymentReceipt 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_payment_receipt
 * - 테이블 코멘트: 결제 영수증
 */
@Entity
@Table(name = "mlt_payment_receipt")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltPaymentReceipt {
    @Id
    @Column(name = "payment_no", nullable = false)
    private Integer paymentNo;

    @Column(name = "payment_amount", nullable = false)
    private Integer paymentAmount;

    @Column(name = "payment_datetime", nullable = false)
    private LocalDateTime paymentDatetime;

    @Column(name = "payment_status", nullable = false)
    private Byte paymentStatus;

    @Column(name = "customer_name", length = 30, nullable = false)
    private String customerName;

    @Column(name = "mobilephone_no", length = 15, nullable = false)
    private String mobilephoneNo;

    @Column(name = "supply_amount", nullable = false)
    private Integer supplyAmount;

    @Column(name = "vat_amount", nullable = false)
    private Integer vatAmount;

    @Column(name = "installment_period")
    private Byte installmentPeriod;

    @Column(name = "card_company_name", length = 30)
    private String cardCompanyName;

    @Column(name = "card_no", length = 20)
    private String cardNo;

    @Column(name = "payment_type", columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String paymentType;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}