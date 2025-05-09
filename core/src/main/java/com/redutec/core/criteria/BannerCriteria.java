package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;

import java.util.List;

public record BannerCriteria(
        List<Long> bannerIds,
        List<Domain> domains,
        String title,
        String content,
        Boolean visible
) {}