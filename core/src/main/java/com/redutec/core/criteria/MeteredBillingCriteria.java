package com.redutec.core.criteria;

import com.redutec.core.meta.BillingStatus;

import java.time.YearMonth;
import java.util.List;

public record MeteredBillingCriteria(
        List<Long> meteredBillingIds,
        List<Long> instituteIds,
        List<YearMonth> billingPeriodMonths,
        List<BillingStatus> billingStatuses
) {}