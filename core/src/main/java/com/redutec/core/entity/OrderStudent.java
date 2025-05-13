package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Comment("상품주문(학생)")
@DiscriminatorValue("STUDENT")
@DynamicUpdate
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class OrderStudent extends Order {
    @Comment("상품주문자(학생)")
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Student student;
}