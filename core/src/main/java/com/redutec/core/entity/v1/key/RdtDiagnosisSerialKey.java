package com.redutec.core.entity.v1.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RdtDiagnosisSerialKey implements Serializable {
    @Column(name = "diagnosis_serial_no", nullable = false)
    private Integer diagnosisSerialNo;

    @Column(name = "diagnosis_serial", nullable = false, length = 16)
    private String diagnosisSerial;
}