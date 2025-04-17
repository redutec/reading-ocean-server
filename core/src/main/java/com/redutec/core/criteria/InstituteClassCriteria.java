package com.redutec.core.criteria;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InstituteClassCriteria {
    private List<Long> instituteClassIds;
    private String name;
    private List<Long> instituteIds;
}