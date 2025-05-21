package com.redutec.core.criteria;

import java.util.List;

public record CartInstituteCriteria(
        List<Long> instituteIds,
        List<Long> productIds
) {}