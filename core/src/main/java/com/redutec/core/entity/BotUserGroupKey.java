package com.redutec.core.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BotUserGroupKey implements Serializable {
    private Integer userNo;
    private Integer groupNo;
}