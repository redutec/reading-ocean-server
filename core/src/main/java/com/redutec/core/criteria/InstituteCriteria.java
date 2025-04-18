package com.redutec.core.criteria;

import com.redutec.core.meta.InstituteManagementType;
import com.redutec.core.meta.InstituteOperationStatus;
import com.redutec.core.meta.InstituteStatus;
import com.redutec.core.meta.InstituteType;

import java.util.List;

public record InstituteCriteria(
    List<Long> instituteIds,
    String name,
    String businessRegistrationName,
    List<InstituteType> types,
    List<InstituteManagementType> managementTypes,
    List<InstituteStatus> statuses,
    List<InstituteOperationStatus> operationStatuses,
    List<Long> branchIds
) {}