package com.redutec.core.criteria;

import com.redutec.core.meta.ProductCategory;
import com.redutec.core.meta.ProductStatus;

import java.util.List;

public record ProductCriteria(
        List<Long> productIds,
        String name,
        String details,
        Integer minimumPrice,
        Integer maximumPrice,
        Integer minimumDiscountPercentage,
        Integer maximumDiscountPercentage,
        List<ProductCategory> categories,
        List<ProductStatus> statuses
) {}