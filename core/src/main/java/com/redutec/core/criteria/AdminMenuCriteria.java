package com.redutec.core.criteria;

import com.redutec.core.meta.AdministratorRole;

import java.util.List;

public record AdminMenuCriteria(
    List<Long> adminMenuIds,
    String name,
    String url,
    Boolean available,
    List<AdministratorRole> accessibleRoles
) {}