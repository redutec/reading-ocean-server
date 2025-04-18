package com.redutec.core.criteria;

import java.util.List;

public record InstituteClassCriteria(
    List<Long> instituteClassIds,
    String name,
    List<Long> instituteIds
) {}