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
public class RatBookCaseKey implements Serializable {
    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;
}