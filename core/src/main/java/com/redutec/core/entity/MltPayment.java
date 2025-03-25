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
 * MltPayment 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_payment
 * - 테이블 코멘트: 결제(결제상태 변경시, 주문상태도 함께 변경 되어야 함. 취소 발생 시, 취소 관려 정보에 대해서 mlt_payment_cancel_detail에 등록처리 되어야 함)
 */
@Entity
@Table(name = "mlt_payment",
        indexes = {
                @Index(name = "FK_payment_type", columnList = "payment_type"),
                @Index(name = "FK_request_transaction_no", columnList = "request_transaction_no")
        }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_no", nullable = false, updatable = false)
    private Integer paymentNo;

    @Column(name = "request_transaction_no", nullable = false)
    private Integer requestTransactionNo;

    @Column(name = "payment_type", nullable = false, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String paymentType;

    @Column(name = "payment_method_code", length = 20, nullable = false)
    private String paymentMethodCode;

    @Column(name = "payment_amount", nullable = false)
    private Integer paymentAmount;

    @Column(name = "payment_datetime", nullable = false)
    private LocalDateTime paymentDatetime;

    @Column(name = "payment_status", nullable = false)
    private Byte paymentStatus;

    @Column(name = "payment_transaction_ID", length = 30, nullable = false)
    private String paymentTransactionID;

    @Column(name = "approval_ID", length = 40)
    private String approvalID;

    @Column(name = "approval_key", length = 40)
    private String approvalKey;

    @Column(name = "account_no")
    private Integer accountNo;

    @Column(name = "cancel_account_no")
    private Integer cancelAccountNo;

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