package com.redutec.core.entity;

import com.redutec.core.meta.ShippingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Immutable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Comment("배송 로그")
@Immutable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ShippingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("배송 로그 고유번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    @Comment("연결된 배송 엔티티")
    private Shipping shipping;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("배송 상태")
    private ShippingStatus status;

    @Column
    @Comment("변경 사유/메모")
    private String note;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}