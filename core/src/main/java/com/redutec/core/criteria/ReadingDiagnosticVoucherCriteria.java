package com.redutec.core.criteria;

import java.util.List;

public record ReadingDiagnosticVoucherCriteria(
    List<Long> readingDiagnoticVoucherIds,
    List<Long> instituteIds,
    String name,
    String code,
    String description
) {}