package com.redutec.core.criteria;

import com.redutec.core.meta.OrderStatus;

import java.util.List;

public record OrderInstituteCriteria(
        List<Long> orderInstituteIds,
        Boolean hasDeliveryFee,
        List<OrderStatus> statuses,
        List<Long> instituteIds
) {}