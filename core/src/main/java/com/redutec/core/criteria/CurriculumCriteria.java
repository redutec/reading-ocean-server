package com.redutec.core.criteria;

import com.redutec.core.meta.CurriculumStatus;

import java.util.List;

public record CurriculumCriteria(
        List<Long> curriculumIds,
        List<Long> studentIds,
        String name,
        String description,
        List<CurriculumStatus> statuses,
        String title
) {}