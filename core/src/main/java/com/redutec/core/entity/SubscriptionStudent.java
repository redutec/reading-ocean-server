package com.redutec.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Optional;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Comment("구독(학생)")
@DynamicUpdate
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionStudent extends Subscription {
    @Comment("구독자(학생)")
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn
    private Student student;

    public void updateSubscriptionStudent(
            SubscriptionPlan subscriptionPlan,
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            LocalDateTime nextPaymentAt,
            Student student
    ) {
        super.updateSubscription(subscriptionPlan, startedAt, endedAt, nextPaymentAt);
        this.student = Optional.ofNullable(student).orElse(this.student);
    }
}