package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.PolicyType;

import java.util.List;

public record PolicyCriteria(
        List<Long> policyIds,
        List<Domain> domains,
        List<PolicyType> types,
        Boolean available
) {}