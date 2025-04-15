package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * SubscriptionPassGroup 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_pass_group
 * - 테이블 코멘트: 이용권 그룹
 */
@Entity
@Table(name = "subscription_pass_group")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionPassGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_pass_group_no", nullable = false, updatable = false)
    private Integer subscriptionPassGroupNo;

    @Column(name = "subscription_pass_no")
    private Integer subscriptionPassNo;

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "descripton", length = 300)
    private String descripton;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}