package com.redutec.core.criteria;

import com.redutec.core.meta.SubscriptionPlanStatus;

import java.util.List;

public record SubscriptionPlanCriteria(
        List<Long> subscriptionPlanIds,
        String name,
        String details,
        Integer minimumPrice,
        Integer maximumPrice,
        Integer minimumDiscountPercentage,
        Integer maximumDiscountPercentage,
        Integer minumumDurationDays,
        Integer maximumDurationDays,
        List<SubscriptionPlanStatus> statuses,
        Boolean autoRenew
) {}