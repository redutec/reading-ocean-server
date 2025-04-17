package com.redutec.core.criteria;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TeacherCriteria {
    private List<Long> teacherIds;
    private String accountId;
    private String name;
    private String instituteName;
}