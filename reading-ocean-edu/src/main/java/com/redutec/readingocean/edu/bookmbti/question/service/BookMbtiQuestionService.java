package com.redutec.readingocean.edu.bookmbti.question.service;

import com.redutec.core.dto.BookMbtiQuestionDto;

import java.util.List;

public interface BookMbtiQuestionService {
    /**
     * 조건에 맞는 북BTI 설문 문항 목록 조회
     * @return 조건에 맞는 북BTI 설문 문항 목록 및 페이징 정보
     */
    List<BookMbtiQuestionDto.BookMbtiQuestionResponse> findAll();
}