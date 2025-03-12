package com.redutec.core.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AgtReadingStatisticsKey implements Serializable {
    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "aggregation_date", nullable = false)
    private LocalDate aggregationDate;

    @Column(name = "daily_yn", nullable = false, columnDefinition = "CHAR(1)")
    private Character dailyYn;

    @Column(name = "book_section_type", nullable = false, length = 6, columnDefinition = "char(6)")
    private String bookSectionType;
}