package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_home_statistics",
        uniqueConstraints = {@UniqueConstraint(name = "idx_account_home_statistics_reference_date", columnNames = "reference_date")})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AccountHomeStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_home_statistics_no", nullable = false, updatable = false)
    private Integer accountHomeStatisticsNo;

    @Column(name = "join_count", nullable = false)
    private Integer joinCount;

    @Column(name = "withdrawal_count", nullable = false)
    private Integer withdrawalCount;

    @Column(name = "login_count", nullable = false)
    private Integer loginCount;

    @Column(name = "active_count", nullable = false)
    private Integer activeCount;

    @Column(name = "total_count", nullable = false)
    private Integer totalCount;

    @Column(name = "reference_date", nullable = false)
    private LocalDate referenceDate;

    @Column(name = "subscriber_count", nullable = false)
    private Integer subscriberCount;

    @Column(name = "paid_subscriber_count", nullable = false)
    private Integer paidSubscriberCount;

    @Column(name = "free_subscriber_count", nullable = false)
    private Integer freeSubscriberCount;

    @Column(name = "paid_subscriber_rate", nullable = false, columnDefinition = "FLOAT")
    private Float paidSubscriberRate;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}