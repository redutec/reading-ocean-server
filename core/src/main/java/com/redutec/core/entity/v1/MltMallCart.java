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
 * MltMallCart 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_mall_cart
 * - 테이블 코멘트: 온라인 상품  장바구니
 */
@Entity
@Table(name = "mlt_mall_cart")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltMallCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_no", nullable = false, updatable = false)
    private Integer cartProductNo;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "product_no", nullable = false)
    private Integer productNo;

    @Column(name = "purchase_quantity", length = 45, nullable = false)
    private String purchaseQuantity;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "payment_yn", nullable = false, columnDefinition = "char(1) default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String paymentYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}