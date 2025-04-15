package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * MltTransactionPoint 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_transaction_point
 * - 테이블 코멘트: 포인트 거래내역(잔액(act_account_information.current_book_point)과 함께 트랜잭션 처리)-insert 만 발생
 */
@Entity
@Table(name = "mlt_transaction_point", indexes = {
        @Index(name = "FK_mlt_transaction_point_act_account_information_account_no", columnList = "account_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltTransactionPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_no", nullable = false, updatable = false)
    private Integer transactionNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "transaction_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String transactionType;

    @Column(name = "transaction_product_no", nullable = false)
    private Integer transactionProductNo;

    @Column(name = "transaction_product_name", nullable = false, length = 50)
    private String transactionProductName;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "after_balance_amount", nullable = false)
    private Integer afterBalanceAmount;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;
}