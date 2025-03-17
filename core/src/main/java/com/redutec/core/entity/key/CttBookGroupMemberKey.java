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
public class CttBookGroupMemberKey implements Serializable {
    @Column(name = "book_group_no", nullable = false)
    private Integer bookGroupNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;
}