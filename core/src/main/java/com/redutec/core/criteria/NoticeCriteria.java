package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;

import java.util.List;

public record NoticeCriteria(
        List<Long> noticeIds,
        List<Domain> domains,
        String title,
        String content,
        Boolean visible
) {}