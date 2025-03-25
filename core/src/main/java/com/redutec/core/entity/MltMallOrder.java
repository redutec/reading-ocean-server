package com.redutec.core.entity;

import com.redutec.core.entity.key.MltMallOrderKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * MltMallOrder 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_mall_order
 * - 테이블 코멘트: 온라인몰 주문(데이터 처리 후, mlt_mall_order_history에 insert )
 */
@Entity
@Table(name = "mlt_mall_order", indexes = {
        @Index(name = "FK_mlt_mall_order_account_no_act_account_account_no", columnList = "account_no")
})
@IdClass(MltMallOrderKey.class)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltMallOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mall_order_no", nullable = false, updatable = false)
    private Integer mallOrderNo;

    @Id
    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "purchase_amount", nullable = false)
    private Integer purchaseAmount;

    @Column(name = "apply_delivery_fee", nullable = false)
    private Integer applyDeliveryFee;

    @Column(name = "orderer_name", length = 30)
    private String ordererName;

    @Column(name = "beneficiary_name", length = 30)
    private String beneficiaryName;

    @Column(name = "zipcode", columnDefinition = "char(5) charset latin1")
    @JdbcTypeCode(Types.CHAR)
    private String zipcode;

    @Column(name = "academy_address", length = 50)
    private String academyAddress;

    @Column(name = "academy_address_detail", length = 50)
    private String academyAddressDetail;

    @Column(name = "contact_no", length = 15)
    private String contactNo;

    @Column(name = "order_status", nullable = false)
    private Byte orderStatus;

    @Column(name = "delivery_company_name_value", length = 6)
    private String deliveryCompanyNameValue;

    @Column(name = "delivery_serial", length = 30)
    private String deliverySerial;

    @Column(name = "email", length = 100)
    private String email;

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