package com.redutec.core.criteria;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StudentCriteria {
    private List<Long> studentIds;
    private String accountId;
    private String name;
    private String instituteName;
}