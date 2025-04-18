package com.redutec.core.criteria;

import com.redutec.core.meta.BranchStatus;

import java.util.List;

public record BranchCriteria(
    List<Long> branchIds,
    String accountId,
    String name,
    List<BranchStatus> statuses,
    String managerName
) {}