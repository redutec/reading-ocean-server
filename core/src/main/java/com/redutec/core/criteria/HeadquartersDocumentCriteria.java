package com.redutec.core.criteria;

import com.redutec.core.meta.LearningMaterialAuthor;
import com.redutec.core.meta.LearningMaterialCategory;

import java.util.List;

public record HeadquartersDocumentCriteria(
        List<Long> headquartersDocumentIds,
        List<LearningMaterialCategory> categories,
        String title,
        String content,
        List<LearningMaterialAuthor> authors,
        Boolean available
) {}