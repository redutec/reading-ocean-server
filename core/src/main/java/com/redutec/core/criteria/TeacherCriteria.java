package com.redutec.core.criteria;

import com.redutec.core.meta.TeacherRole;
import com.redutec.core.meta.TeacherStatus;

import java.util.List;

public record TeacherCriteria(
    List<Long> teacherIds,
    String accountId,
    String name,
    String instituteName,
    List<TeacherStatus> statuses,
    List<TeacherRole> roles
) {}