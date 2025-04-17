package com.redutec.core.criteria.v1;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BotUserCriteria {
    private List<Integer> userNoList;
    private String userId;
    private String userName;
}