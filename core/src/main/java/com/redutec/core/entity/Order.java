package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@SuperBuilder
@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("상품주문 고유번호")
    private Long id;

    @Comment("결제금액")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer purchaseAmount;

    @Comment("배송료")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer deliveryFee;

    @Comment("수취인")
    @Column(nullable = false)
    @Convert(converter = AesAttributeConverter.class)
    private String recipientName;

    @Comment("수취인 연락처")
    @Column
    @Convert(converter = AesAttributeConverter.class)
    private String recipientPhoneNumber;

    @Comment("배송지 우편번호")
    @Column
    @Convert(converter = AesAttributeConverter.class)
    private String postalCode;

    @Comment("배송지")
    @Column
    @Convert(converter = AesAttributeConverter.class)
    private String deliveryAddress;

    @Comment("배송업체")
    @Column(length = 10)
    private String deliveryCompanyName;

    @Comment("송장번호")
    @Column
    @Convert(converter = AesAttributeConverter.class)
    private String deliverySerialNumber;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDERED;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateOrder(
    ) {
    }
}