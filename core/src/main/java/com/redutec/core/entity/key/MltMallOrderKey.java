package com.redutec.core.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MltMallOrderKey implements Serializable {
    @Column(name = "mall_order_no", nullable = false)
    private Integer mallOrderNo;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;
}