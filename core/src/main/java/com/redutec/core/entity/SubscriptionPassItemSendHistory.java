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
 * SubscriptionPassItemSendHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_pass_item_send_history
 * - 테이블 코멘트: 이용권 발송 내역
 */
@Entity
@Table(name = "subscription_pass_item_send_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionPassItemSendHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_pass_item_send_history_no", nullable = false, updatable = false)
    private Integer subscriptionPassItemSendHistoryNo;

    @Column(name = "subscription_pass_no")
    private Integer subscriptionPassNo;

    @Column(name = "subscription_pass_group_no")
    private Integer subscriptionPassGroupNo;

    @Column(name = "subscription_pass_item_no")
    private Integer subscriptionPassItemNo;

    @Column(name = "type", length = 20, nullable = false)
    private String type;

    @Column(name = "mobile_no", length = 20)
    private String mobileNo;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "status", length = 10, nullable = false)
    private String status;

    @Column(name = "code", length = 20)
    private String code;

    @Lob
    @Column(name = "message", columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String message;

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