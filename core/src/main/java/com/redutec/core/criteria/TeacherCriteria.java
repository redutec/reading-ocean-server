package com.redutec.core.criteria;

import java.util.List;

public record TeacherCriteria(
    List<Long> teacherIds,
    String accountId,
    String name,
    String instituteName
) {}