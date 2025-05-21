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
}