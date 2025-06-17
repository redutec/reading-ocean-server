package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.ShippingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Comment("배송")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("배송 고유번호")
    private Long id;

    @Comment("연결된 주문 엔티티")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private Order order;

    @Comment("수취인 이름")
    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 512)
    @Convert(converter = AesAttributeConverter.class)
    private String recipientName;

    @Comment("수취인 연락처")
    @NotBlank
    @Pattern(regexp = "^[0-9]{10,11}$", message = "전화번호는 10 ~ 11자리 숫자여야 합니다.")
    @Column(nullable = false, length = 512)
    @Convert(converter = AesAttributeConverter.class)
    private String recipientPhoneNumber;

    @Comment("배송지 우편번호")
    @NotBlank
    @Pattern(regexp = "^[0-9]{5}$", message = "우편번호는 5자리 숫자이어야 합니다.")
    @Column(nullable = false, length = 512)
    @Convert(converter = AesAttributeConverter.class)
    private String postalCode;

    @Comment("배송지")
    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 1024)
    @Convert(converter = AesAttributeConverter.class)
    private String deliveryAddress;

    @Comment("배송 업체명")
    @Size(max = 20)
    @Column(length = 20)
    private String deliveryCompanyName;

    @Comment("송장번호")
    @Pattern(regexp = "^[0-9]{10,14}$", message = "송장번호는 10 ~ 14자리 숫자이어야 합니다.")
    @Column(nullable = false, length = 512)
    @Convert(converter = AesAttributeConverter.class)
    private String trackingNumber;

    @Comment("배송 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ShippingStatus status = ShippingStatus.READY;

    @Comment("연결된 배송 로그")
    @OneToMany(
            mappedBy = "shipping",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("createdAt ASC")
    private List<ShippingLog> shippingLogs = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /** 배송 상태 변경 + 이력 기록 */
    public void changeStatus(ShippingStatus newStatus, String note) {
        this.status = newStatus;
        this.shippingLogs.add(
                ShippingLog.builder()
                        .shipping(this)
                        .status(newStatus)
                        .note(note)
                        .build()
        );
    }
}