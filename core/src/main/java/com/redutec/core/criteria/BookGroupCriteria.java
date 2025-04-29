package com.redutec.core.criteria;

import com.redutec.core.meta.BookGroupType;
import com.redutec.core.meta.SchoolGrade;

import java.time.YearMonth;
import java.util.List;

public record BookGroupCriteria(
        List<Long> groupIds,
        YearMonth yearMonth,
        List<BookGroupType> types,
        List<SchoolGrade> schoolGrades
) {}