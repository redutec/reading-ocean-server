package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * SubscriptionStatistics 엔티티
 * 테이블 정보:
 * - 테이블 명: subscription_statistics
 * - 테이블 코멘트: 구독 통계
 */
@Entity
@Table(name = "subscription_statistics", uniqueConstraints = {
        @UniqueConstraint(name = "idx_subscription_statistics_reference_date_product_no", columnNames = {"reference_date", "subscription_product_no"})
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_statistics_no", nullable = false, updatable = false)
    private Integer subscriptionStatisticsNo;

    @Column(name = "reference_date", nullable = false)
    private LocalDate referenceDate;

    @Column(name = "subscription_product_no", nullable = false)
    private Integer subscriptionProductNo;

    @Column(name = "subscription_product_name", length = 300, nullable = false)
    private String subscriptionProductName;

    @Column(name = "subscription_product_type", length = 15, nullable = false)
    private String subscriptionProductType;

    @Column(name = "subscription_product_category", length = 15, nullable = false)
    private String subscriptionProductCategory;

    @Column(name = "subscription_product_service_type", length = 15, nullable = false)
    private String subscriptionProductServiceType;

    @Column(name = "subscriber_count", nullable = false)
    private Integer subscriberCount;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}