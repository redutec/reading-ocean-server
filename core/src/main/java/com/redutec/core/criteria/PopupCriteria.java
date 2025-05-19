package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;

import java.util.List;

public record PopupCriteria(
        List<Long> popupIds,
        List<Domain> domains,
        String title,
        String content,
        Boolean visible
) {}