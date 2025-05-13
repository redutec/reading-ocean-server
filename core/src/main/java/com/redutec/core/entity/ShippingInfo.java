package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.ShippingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Comment;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShippingInfo {
    @Comment("수취인 이름")
    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 512)
    @Convert(converter = AesAttributeConverter.class)
    private String recipientName;

    @Comment("수취인 연락처")
    @NotBlank
    @Pattern(regexp = "^[0-9]{10,11}$", message = "전화번호는 10~11자리 숫자여야 합니다.")
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

    @Comment("배송업체명")
    @Size(max = 20)
    @Column(length = 20)
    private String deliveryCompanyName;

    @Comment("배송 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ShippingStatus shippingStatus = ShippingStatus.READY;
}