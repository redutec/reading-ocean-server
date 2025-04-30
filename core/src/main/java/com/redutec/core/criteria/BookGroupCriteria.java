package com.redutec.core.criteria;

import com.redutec.core.meta.BookGroupType;
import com.redutec.core.meta.SchoolGrade;

import java.util.List;

public record BookGroupCriteria(
        List<Long> bookGroupIds,
        String name,
        String yearMonth,
        List<BookGroupType> types,
        List<SchoolGrade> schoolGrades
) {}