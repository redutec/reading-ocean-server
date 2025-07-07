package com.redutec.readingocean.edu.bookmbti.survey.service;

import com.redutec.core.dto.BookMbtiSurveyDto;

public interface BookMbtiSurveyService {
    /**
     * 현재 로그인한 학생의 북BTI 설문 응답 등록
     * @param createBookMbtiSurveyRequest 북BTI 설문 응답 등록 정보를 담은 DTO
     * @return 등록된 북BTI 설문 응답 정보
     */
    BookMbtiSurveyDto.BookMbtiSurveyResponse create(BookMbtiSurveyDto.CreateBookMbtiSurveyRequest createBookMbtiSurveyRequest);

    /**
     * 현재 로그인한 학생의 가장 최근 설문 응답 조회
     * @return 현재 로그인한 학생의 가장 최근 설문 응답 목록 및 페이징 정보
     */
    BookMbtiSurveyDto.BookMbtiSurveyResponse find();
}