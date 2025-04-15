package com.redutec.core.entity.v1.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RdtReadingDiagnosisReportDetailKey implements Serializable {
    @Column(name = "diagnosis_apply_no", nullable = false)
    private Integer diagnosisApplyNo;

    @Column(name = "diagnosis_subcategory_value", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisSubcategoryValue;
}