package com.redutec.core.criteria;

import java.util.List;

public record InstituteSubscriptionCriteria(
        List<Long> instituteSubscriptionIds,
        List<Long> subscriptionPlanIds,
        List<Long> instituteIds
) {}