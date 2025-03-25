package com.redutec.core.entity.key;

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
public class RdtReadingDiagnosisReportCommentKey implements Serializable {
    @Column(name = "diagnosis_apply_no", nullable = false)
    private Integer diagnosisApplyNo;

    @Column(name = "comment_no", nullable = false)
    private Integer commentNo;
}