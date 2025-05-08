package com.redutec.core.criteria;

import java.util.List;

public record SubscriptionStudentCriteria(
        List<Long> subscriptionStudentIds,
        List<Long> subscriptionPlanIds,
        List<Long> studentIds
) {}