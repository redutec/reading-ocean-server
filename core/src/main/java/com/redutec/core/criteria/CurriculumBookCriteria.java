package com.redutec.core.criteria;

import com.redutec.core.meta.ReadingStatus;

import java.util.List;

public record CurriculumBookCriteria(
        List<Long> curriculumBookIds,
        List<Long> curriculumIds,
        String title,
        List<ReadingStatus> readingStatuses
) {}