package com.redutec.core.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BotUserGroupKey implements Serializable {
    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "group_no")
    private Integer groupNo;
}