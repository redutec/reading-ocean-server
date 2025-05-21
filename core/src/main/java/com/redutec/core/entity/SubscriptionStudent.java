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
}