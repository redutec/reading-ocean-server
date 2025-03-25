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
 * SubscriptionPassItem 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_pass_item
 * - 테이블 코멘트: 이용권 아이템
 */
@Entity
@Table(name = "subscription_pass_item", uniqueConstraints = {
        @UniqueConstraint(name = "subscription_pass_item_pk", columnNames = "code")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionPassItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_pass_item_no", nullable = false, updatable = false)
    private Integer subscriptionPassItemNo;

    @Column(name = "subscription_pass_group_no")
    private Integer subscriptionPassGroupNo;

    @Column(name = "account_no")
    private Integer accountNo;

    @Column(name = "code", length = 100, nullable = false)
    private String code;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "product_order_id", length = 100)
    private String productOrderId;

    @Column(name = "mobile_no", length = 20)
    private String mobileNo;

    @Column(name = "email", length = 30)
    private String email;

    @Lob
    @Column(name = "memo", columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String memo;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}