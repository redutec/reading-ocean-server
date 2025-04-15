package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * SubscriptionPass 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_pass
 * - 테이블 코멘트: 이용권
 */
@Entity
@Table(name = "subscription_pass")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionPass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_pass_no", nullable = false, updatable = false)
    private Integer subscriptionPassNo;

    @Column(name = "subscription_product_no")
    private Integer subscriptionProductNo;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "expired_date", length = 20, nullable = false)
    private String expiredDate;

    @Column(name = "distributor", length = 30, nullable = false)
    private String distributor;

    @Column(name = "distributor_product_id", length = 100)
    private String distributorProductId;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}