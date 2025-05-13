package com.redutec.core.criteria;

import com.redutec.core.meta.TeacherRole;

import java.util.List;

public record TeachingOceanMenuCriteria(
    List<Long> teachingOceanMenuIds,
    String name,
    String url,
    Boolean available,
    List<TeacherRole> accessibleRoles
) {}