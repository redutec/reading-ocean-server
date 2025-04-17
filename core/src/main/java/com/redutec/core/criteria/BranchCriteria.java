package com.redutec.core.criteria;

import com.redutec.core.meta.BranchStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BranchCriteria {
    private List<Long> branchIds;
    private String accountId;
    private String name;
    private List<BranchStatus> statuses;
    private String managerName;
}