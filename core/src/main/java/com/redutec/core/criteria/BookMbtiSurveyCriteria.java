package com.redutec.core.criteria;

import com.redutec.core.meta.BookMbtiResult;

import java.util.List;

public record BookMbtiSurveyCriteria(
    List<Long> bookMbtiSurveyIds,
    List<Long> studentIds,
    List<BookMbtiResult> bookMbtiResults
) {}