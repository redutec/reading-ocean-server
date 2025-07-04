package com.redutec.readingoceanedu.bookmbti.survey.service;

import com.redutec.core.dto.BookMbtiQuestionDto;

import java.util.List;

public interface BookMbtiSurveyService {
    /**
     * 현재 로그인한 학생의 설문 응답 조회
     * @return 현재 로그인한 학생의 설문 응답 목록 및 페이징 정보
     */
    List<BookMbtiQuestionDto.BookMbtiQuestionResponse> find();
}