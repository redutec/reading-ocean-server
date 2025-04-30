package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StudentSubscription extends Subscription {
    @Comment("구독자(학생)")
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn
    private Student student;

    public void updateStudentSubscription(
            SubscriptionPlan subscriptionPlan,
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            Student student
    ) {
        super.updateSubscription(subscriptionPlan, startedAt, endedAt);
        this.student = Optional.ofNullable(student).orElse(this.student);
    }
}