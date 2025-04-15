package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * MltMallProduct 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_mall_product
 * - 테이블 코멘트: 온라인 상품
 */
@Entity
@Table(name = "mlt_mall_product")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltMallProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no", nullable = false, updatable = false)
    private Integer productNo;

    @Column(name = "product_category_value", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String productCategoryValue;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "product_amount", nullable = false)
    private Integer productAmount;

    @Column(name = "minimum_purchase_quantity", nullable = false, columnDefinition = "SMALLINT DEFAULT 1")
    private Short minimumPurchaseQuantity;

    @Column(name = "discount_rate", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal discountRate;

    @Column(name = "product_status", nullable = false)
    private Byte productStatus;

    @Lob
    @Column(name = "detail_description", nullable = false, columnDefinition = "longtext")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String detailDescription;

    @Lob
    @Column(name = "announce_content", nullable = false)
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String announceContent;

    @Column(name = "use_delivery", columnDefinition = "bit(1) DEFAULT 1")
    private Boolean useDelivery;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "property1", length = 40)
    private String property1;

    @Column(name = "property2", length = 40)
    private String property2;

    @Column(name = "property3", length = 40)
    private String property3;

    @Column(name = "property4", length = 40)
    private String property4;

    @Column(name = "property5", length = 40)
    private String property5;

    @Column(name = "property6", length = 40)
    private String property6;

    @Column(name = "property7", length = 40)
    private String property7;

    @Column(name = "property8", length = 40)
    private String property8;
}