package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * MltSubscriptionAcademyProduct 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_subscription_academy_product
 * - 테이블 코멘트: 학원구독상품
 */
@Entity
@Table(name = "mlt_subscription_academy_product",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_mlt_subscription_academy_product_academy_no", columnNames = "academy_no")
        }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltSubscriptionAcademyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "academy_product_no", nullable = false, updatable = false)
    private Integer academyProductNo;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "subscription_product_no", nullable = false)
    private Integer subscriptionProductNo;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}