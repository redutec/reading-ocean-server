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
@Comment("주문(교육기관)")
@DynamicUpdate
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderInstitute extends Order {
    @Comment("주문자(교육기관)")
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn
    private Institute institute;

    public void updateOrderInstitute(
            Institute institute
    ) {
        super.updateOrder(subscriptionPlan, startedAt, endedAt, nextPaymentAt);
        this.institute = Optional.ofNullable(institute).orElse(this.institute);
    }
}