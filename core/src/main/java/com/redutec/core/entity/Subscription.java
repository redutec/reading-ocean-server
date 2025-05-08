package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@SuperBuilder
@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("구독 고유번호")
    private Long id;

    @Comment("구독 상품")
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn
    private SubscriptionPlan subscriptionPlan;

    @Comment("시작일")
    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Comment("종료일")
    @Column
    private LocalDateTime endedAt;

    @Comment("다음 결제일")
    @Column
    private LocalDateTime nextPaymentAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    protected void updateSubscription(
            SubscriptionPlan subscriptionPlan,
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            LocalDateTime nextPaymentAt
    ) {
        this.subscriptionPlan = Optional.ofNullable(subscriptionPlan).orElse(this.subscriptionPlan);
        this.startedAt = Optional.ofNullable(startedAt).orElse(this.startedAt);
        this.endedAt = Optional.ofNullable(endedAt).orElse(this.endedAt);
        this.nextPaymentAt = Optional.ofNullable(nextPaymentAt).orElse(this.nextPaymentAt);
    }
}