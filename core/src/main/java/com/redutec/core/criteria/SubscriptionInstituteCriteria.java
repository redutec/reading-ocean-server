package com.redutec.core.criteria;

import java.util.List;

public record SubscriptionInstituteCriteria(
        List<Long> subscriptionInstituteIds,
        List<Long> subscriptionPlanIds,
        List<Long> instituteIds
) {}