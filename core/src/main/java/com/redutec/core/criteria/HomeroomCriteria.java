package com.redutec.core.criteria;

import java.util.List;

public record HomeroomCriteria(
    List<Long> homeroomIds,
    String name,
    String description,
    List<Long> instituteIds
) {}