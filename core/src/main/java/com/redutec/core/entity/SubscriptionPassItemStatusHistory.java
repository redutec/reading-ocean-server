package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * SubscriptionPassItemStatusHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_pass_item_status_history
 * - 테이블 코멘트: 이용권 코드 상태 변경 내역
 */
@Entity
@Table(name = "subscription_pass_item_status_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionPassItemStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_pass_item_status_history_no", nullable = false, updatable = false)
    private Integer subscriptionPassItemStatusHistoryNo;

    @Column(name = "subscription_pass_item_no", nullable = false)
    private Integer subscriptionPassItemNo;

    @Column(name = "account_no")
    private Integer accountNo;

    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}