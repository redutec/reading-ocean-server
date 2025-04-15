package com.redutec.core.entity.v1;

import com.redutec.core.entity.v1.key.AcademySubscriptionBillDetailUserHistoryKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * AcademySubscriptionBillDetailUserHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: academy_subscription_bill_detail_user_history
 * - 테이블 코멘트: 학원별 일별 학생수 집계 상세 내역 (mlt_subscription_bill_detail 기준)
 */
@Entity
@Table(name = "academy_subscription_bill_detail_user_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AcademySubscriptionBillDetailUserHistory {
    @EmbeddedId
    private AcademySubscriptionBillDetailUserHistoryKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accountNo")
    @JoinColumn(name = "account_no", nullable = false)
    private ActAccount actAccount;

    @Column(name = "account_id", length = 16)
    private String accountId;

    @Column(name = "account_name", length = 30)
    private String accountName;

    @Column(name = "account_status", length = 20)
    private String accountStatus;

    @Column(name = "is_test_account")
    private Integer isTestAccount;

    @Column(name = "school_name", length = 30)
    private String schoolName;

    @Column(name = "school_grade", length = 30)
    private String schoolGrade;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}