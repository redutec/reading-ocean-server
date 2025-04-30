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
import java.util.Optional;

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

    public void updateSubscriptionPlan(
            String name,
            String details,
            Integer price,
            Integer discountPercentage,
            Integer durationDays,
            SubscriptionPlanStatus status,
            Boolean autoRenew
    ) {
        this.name = Optional.ofNullable(name).orElse(this.name);
        this.details = Optional.ofNullable(details).orElse(this.details);
        this.price = Optional.ofNullable(price).orElse(this.price);
        this.discountPercentage = Optional.ofNullable(discountPercentage).orElse(this.discountPercentage);
        this.durationDays = Optional.ofNullable(durationDays).orElse(this.durationDays);
        this.status = Optional.ofNullable(status).orElse(this.status);
        this.autoRenew = Optional.ofNullable(autoRenew).orElse(this.autoRenew);
    }
}