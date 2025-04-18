package com.redutec.core.criteria;

import java.util.List;

public record StudentCriteria(
    List<Long> studentIds,
    String accountId,
    String name,
    String instituteName
) {}