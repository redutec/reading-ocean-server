package com.redutec.core.criteria;

import com.redutec.core.meta.DocumentAuthor;
import com.redutec.core.meta.DocumentCategory;

import java.util.List;

public record HeadquartersDocumentCriteria(
        List<Long> headquartersDocumentIds,
        List<DocumentCategory> categories,
        String title,
        String content,
        List<DocumentAuthor> authors,
        Boolean visible
) {}