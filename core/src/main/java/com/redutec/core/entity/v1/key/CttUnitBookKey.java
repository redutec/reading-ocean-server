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
public class CttUnitBookKey implements Serializable {
    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "link_unit_no", nullable = false)
    private Integer linkUnitNo;
}