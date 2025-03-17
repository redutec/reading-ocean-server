package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CouponManage 엔티티
 * 테이블 정보:
 * - 테이블 명: coupon_manage
 * - 테이블 코멘트: 쿠폰 관리
 */
@Entity
@Table(name = "coupon_manage")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CouponManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_manage_no", nullable = false, updatable = false)
    private Integer couponManageNo;

    @Column(name = "reference_number")
    private Integer referenceNumber;

    @Lob
    @Column(name = "issue_reason", columnDefinition = "text")
    private String issueReason;

    @Column(name = "discount_type", length = 15)
    private String discountType;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "issue_count")
    private Integer issueCount;

    @Lob
    @Column(name = "memo", columnDefinition = "longtext")
    private String memo;

    @Column(name = "use_yn", columnDefinition = "char(1)")
    private Character useYn;

    @Column(name = "admin_id", length = 100)
    private String adminId;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}