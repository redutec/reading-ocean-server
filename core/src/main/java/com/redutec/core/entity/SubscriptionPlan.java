package com.redutec.core.entity;

import com.redutec.core.meta.SubscriptionPlanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Comment("구독 상품")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("구독 상품 고유번호")
    private Long id;

    @Comment("상품명")
    @Column(nullable = false, length = 100)
    private String name;

    @Comment("설명")
    @Column(length = 1000)
    private String details;

    @Comment("가격")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer price;

    @Comment("할인율")
    @Column
    @PositiveOrZero
    @Max(100)
    private Integer discountPercentage;

    @Comment("구독일수")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer durationDays;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionPlanStatus status = SubscriptionPlanStatus.ACTIVE;

    @Comment("자동 갱신 여부")
    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean autoRenew = true;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}