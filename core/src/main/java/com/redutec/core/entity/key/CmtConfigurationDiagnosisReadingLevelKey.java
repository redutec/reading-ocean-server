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
public class CmtConfigurationDiagnosisReadingLevelKey implements Serializable {
    @Column(name = "school_grade", nullable = false, length = 15)
    private String schoolGrade;

    @Column(name = "reading_level", nullable = false, length = 10)
    private String readingLevel;
}