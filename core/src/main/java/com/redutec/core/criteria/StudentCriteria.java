package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;

import java.util.List;

public record StudentCriteria(
    List<Long> studentIds,
    String accountId,
    String name,
    String instituteName,
    List<Domain> domains
) {}