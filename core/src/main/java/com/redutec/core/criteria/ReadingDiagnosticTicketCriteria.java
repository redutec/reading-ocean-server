package com.redutec.core.criteria;

import java.util.List;

public record ReadingDiagnosticTicketCriteria(
    List<Long> readingDiagnoticTicketIds,
    List<Long> readingDiagnoticVoucherIds,
    List<Long> instituteIds,
    String serial,
    Boolean used
) {}