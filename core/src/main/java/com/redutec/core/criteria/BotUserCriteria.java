package com.redutec.core.criteria;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BotUserCriteria {
    private List<Long> userNoList;
    private String userId;
    private String userName;
}