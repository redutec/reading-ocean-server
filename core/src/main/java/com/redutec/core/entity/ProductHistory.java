package com.redutec.core.entity;

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
 * ProductHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: product_history
 * - 테이블 코멘트: 상품 변경 이력
 */
@Entity
@Table(name = "product_history", indexes = {
        @Index(name = "idx_product_history", columnList = "product_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_history_no", nullable = false, updatable = false)
    private Integer productHistoryNo;

    @Column(name = "product_no")
    private Integer productNo;

    @Column(name = "product_category_value", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String productCategoryValue;

    @Column(name = "product_name", length = 30, nullable = false)
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
    @Column(name = "announce_content", nullable = false, columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String announceContent;

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

    @Column(name = "change_status", length = 10)
    private String changeStatus;

    @Column(name = "change_field", length = 200)
    private String changeField;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}