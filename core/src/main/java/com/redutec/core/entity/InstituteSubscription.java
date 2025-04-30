package com.redutec.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Comment("구독(교육기관)")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class InstituteSubscription extends Subscription {
    @Comment("구독자(교육기관)")
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn
    private Institute institute;

    public void updateInstituteSubscription(
            SubscriptionPlan subscriptionPlan,
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            Institute institute
    ) {
        super.updateSubscription(subscriptionPlan, startedAt, endedAt);
        this.institute = Optional.ofNullable(institute).orElse(this.institute);
    }
}