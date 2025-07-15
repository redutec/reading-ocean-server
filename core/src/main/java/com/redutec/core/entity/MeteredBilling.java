package com.redutec.core.entity;

import com.redutec.core.meta.BillingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "metered_billing",
        uniqueConstraints = @UniqueConstraint(
                name = "uc_mb_institute_period",
                columnNames = {"institute_id", "billing_period_start", "billing_period_end"}
        ),
        indexes = {
                @Index(name = "idx_mb_institute_status", columnList = "institute_id, billing_status"),
                @Index(name = "idx_mb_period_start", columnList = "billing_period_start")
        }
)
@Comment("교육기관의 월별 사용료 청구서(교육기관의 일별 사용료 내역을 월별로 합친 데이터)")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MeteredBilling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("월간 과금 집계 고유번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institute_id")
    @Comment("사용료를 결제할 교육기관")
    private Institute institute;

    @Column(nullable = false)
    @Comment("사용료 청구 시작일")
    private LocalDate billingPeriodStart;

    @Column(nullable = false)
    @Comment("사용료 청구 종료일")
    private LocalDate billingPeriodEnd;

    @Column(nullable = false)
    @Comment("청구 금액")
    @PositiveOrZero
    private Long invoiceAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("청구 상태: 결제 대기/PENDING, 결제 완료/PAID, 미납/UNPAID, 환불/REFUNDED")
    private BillingStatus billingStatus;

    @Column(nullable = false)
    @Comment("결제 만기일")
    private LocalDate paymentDueDate;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}