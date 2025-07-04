package com.redutec.core.criteria;

import com.redutec.core.meta.*;

import java.util.List;

public record BookMbtiQuestionCriteria(
    List<Long> bookMbtiQuestionIds,
    List<BookMbtiQuestionType> types,
    String question
) {}