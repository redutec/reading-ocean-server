package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * MltPaymentCancelDetail 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_payment_cancel_detail
 * - 테이블 코멘트: 결제 취소 상세
 */
@Entity
@Table(name = "mlt_payment_cancel_detail")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltPaymentCancelDetail {
    @Id
    @Column(name = "payment_no", nullable = false)
    private Integer paymentNo;

    @Column(name = "request_transaction_no", nullable = false)
    private Integer requestTransactionNo;

    @Column(name = "payment_type", nullable = false, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String paymentType;

    @Column(name = "payment_method_code", nullable = false, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String paymentMethodCode;

    @Column(name = "payment_amount", nullable = false)
    private Integer paymentAmount;

    @Column(name = "approval_ID", length = 30)
    private String approvalID;

    @Column(name = "approval_key", length = 30)
    private String approvalKey;

    @Column(name = "account_no")
    private Integer accountNo;

    @Column(name = "cancel_reason_value", columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String cancelReasonValue;

    @Column(name = "cancel_reason_description", length = 300)
    private String cancelReasonDescription;

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