package com.redutec.core.criteria.v1;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CmtConfigurationGeneralCriteria {
    private List<String> configurationKeyList;
    private String configurationCategoryKey;
    private String configurationCategoryName;
    private String configurationName;
    private String configurationContent;
    private String useYn;
    private String description;
}