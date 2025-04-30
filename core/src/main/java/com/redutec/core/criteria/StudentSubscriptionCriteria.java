package com.redutec.core.criteria;

import java.util.List;

public record StudentSubscriptionCriteria(
        List<Long> studentSubscriptionIds,
        List<Long> subscriptionPlanIds,
        List<Long> studentIds
) {}