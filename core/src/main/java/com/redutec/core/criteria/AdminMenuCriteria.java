package com.redutec.core.criteria;

import com.redutec.core.meta.AdminUserRole;

import java.util.List;

public record AdminMenuCriteria(
    List<Long> adminMenuIds,
    String name,
    String url,
    Boolean available,
    List<AdminUserRole> accessibleRoles
) {}