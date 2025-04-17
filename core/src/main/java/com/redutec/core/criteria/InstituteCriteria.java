package com.redutec.core.criteria;

import com.redutec.core.meta.InstituteManagementType;
import com.redutec.core.meta.InstituteOperationStatus;
import com.redutec.core.meta.InstituteStatus;
import com.redutec.core.meta.InstituteType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InstituteCriteria {
    private List<Long> instituteIds;
    private String name;
    private String businessRegistrationName;
    private List<InstituteType> types;
    private List<InstituteManagementType> managementTypes;
    private List<InstituteStatus> statuses;
    private List<InstituteOperationStatus> operationStatuses;
    private List<Long> branchIds;
}