package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;

import java.util.List;

public record FaqCriteria(
        List<Long> faqIds,
        List<Domain> domains,
        String title,
        String content,
        Boolean visible
) {}