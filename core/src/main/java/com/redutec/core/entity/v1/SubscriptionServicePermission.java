package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * SubscriptionServicePermission 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_service_permission
 * - 테이블 코멘트: 구독 서비스 권한
 */
@Entity
@Table(name = "subscription_service_permission")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionServicePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_service_permission_no", nullable = false, updatable = false)
    private Integer subscriptionServicePermissionNo;

    @Column(name = "subscription_product_no")
    private Integer subscriptionProductNo;

    @Column(name = "service_type", length = 30)
    private String serviceType;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}