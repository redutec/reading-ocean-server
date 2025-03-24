package com.redutec.core.criteria;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BotGroupCriteria {
    List<Long> groupNoList;
    String groupName;
}