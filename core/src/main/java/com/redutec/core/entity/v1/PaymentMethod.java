package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * PaymentMethod 엔티티
 * 테이블 정보:
 * - 테이블 명: payment_method
 * - 테이블 코멘트: 결제 방법
 */
@Entity
@Table(name = "payment_method", indexes = {
        @Index(name = "idx_payment_method_account_no", columnList = "account_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_no", nullable = false, updatable = false)
    private Integer paymentMethodNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "method", length = 20, nullable = false)
    private String method;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "bill_key", length = 400)
    private String billKey;

    @Column(name = "credit_card_company", length = 20, nullable = false)
    private String creditCardCompany;

    @Column(name = "credit_card_number", length = 20, nullable = false)
    private String creditCardNumber;

    @Column(name = "credit_card_corp_flag", length = 10)
    private String creditCardCorpFlag;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "payment_gateway_type", length = 100)
    private String paymentGatewayType;
}