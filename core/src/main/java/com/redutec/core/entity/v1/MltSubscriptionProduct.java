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
 * MltSubscriptionProduct 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_subscription_product
 * - 테이블 코멘트: 구독상품
 */
@Entity
@Table(name = "mlt_subscription_product")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltSubscriptionProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_product_no", nullable = false, updatable = false)
    private Integer subscriptionProductNo;

    @Column(name = "subscription_product_name", length = 30, nullable = false)
    private String subscriptionProductName;

    @Column(name = "subscription_product_price", nullable = false)
    private Integer subscriptionProductPrice;

    @Lob
    @Column(name = "subscription_product_memo", columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String subscriptionProductMemo;

    @Column(name = "is_minimum_charge", nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean isMinimumCharge;

    @Column(name = "minimum_charge_amount", nullable = false, columnDefinition = "int default 0")
    private Integer minimumChargeAmount;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}