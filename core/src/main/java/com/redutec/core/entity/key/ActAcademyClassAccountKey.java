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
public class ActAcademyClassAccountKey implements Serializable {
    @Column(name = "class_no", nullable = false)
    private Integer classNo;

    @Column(name = "student_account_no", nullable = false)
    private Integer studentAccountNo;
}