package com.redutec.core.criteria;

import com.redutec.core.meta.BookGenre;
import com.redutec.core.meta.BookMbtiResult;
import com.redutec.core.meta.BookSubGenre;

import java.util.List;

public record BookCriteria(
        List<Long> bookIds,
        String isbn,
        String title,
        String author,
        String publisher,
        Boolean recommended,
        Boolean ebookAvailable,
        Boolean audiobookAvailable,
        Boolean visible,
        Boolean enabled,
        Integer minimumPageCount,
        Integer maximumPageCount,
        Integer minimumSchoolGrade,
        Integer maximumSchoolGrade,
        List<BookGenre> genres,
        List<BookSubGenre> subGenres,
        Integer minimumBookPoints,
        Integer maximumBookPoints,
        Integer minimumRaq,
        Integer maximumRaq,
        List<BookMbtiResult> bookMbtiResultList,
        String subject,
        String content,
        List<String> tags
) {}