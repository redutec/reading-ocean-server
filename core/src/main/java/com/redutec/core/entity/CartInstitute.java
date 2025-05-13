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
@Comment("장바구니(교육기관)")
@DiscriminatorValue("INSTITUTE")
@DynamicUpdate
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CartInstitute extends Cart {
    @Comment("장바구니 소유자(교육기관)")
    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Institute institute;
}