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
public class BotGroupPermissionKey implements Serializable {
    @Column(name = "group_no", nullable = false)
    private Integer groupNo;

    @Column(name = "menu_no", nullable = false)
    private Integer menuNo;
}