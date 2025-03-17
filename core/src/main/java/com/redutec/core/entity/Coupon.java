package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Coupon 엔티티
 * 테이블 정보:
 * - 테이블 명: coupon
 * - 테이블 코멘트: 쿠폰
 */
@Entity
@Table(name = "coupon", uniqueConstraints = {
        @UniqueConstraint(name = "coupon_number", columnNames = "coupon_number")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_no", nullable = false, updatable = false)
    private Integer couponNo;

    @Column(name = "coupon_manage_no")
    private Integer couponManageNo;

    @Column(name = "account_no")
    private Integer accountNo;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "coupon_number", length = 255, unique = true)
    private String couponNumber;

    @Column(name = "usage_datetime")
    private LocalDateTime usageDatetime;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}