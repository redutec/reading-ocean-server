package com.redutec.core.entity;

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
        name = "metered_billing_record",
        uniqueConstraints = @UniqueConstraint(
                name = "uc_mbr_institute_date",
                columnNames = {"institute_id", "billing_date"}
        ),
        indexes = {
                @Index(name = "idx_mbr_institute", columnList = "institute_id"),
                @Index(name = "idx_mbr_billing_date", columnList = "billing_date")
        }
)
@Comment("교육기관의 일별 사용료 내역")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MeteredBillingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("교육기관의 일별 사용료 내역 고유번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "institute_id")
    @Comment("사용료가 발생한 교육기관")
    private Institute institute;

    @Column(nullable = false)
    @Comment("사용료 기준일")
    private LocalDate billingDate;

    @Column(nullable = false)
    @Comment("학생 1명당 이용료")
    @PositiveOrZero
    private Integer unitPrice;

    @Column(nullable = false)
    @Comment("활성화된 학생 수")
    @PositiveOrZero
    private Integer activeStudents;

    @Column(nullable = false)
    @Comment("일일 총 사용료")
    @PositiveOrZero
    private Long dailyAmount;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}