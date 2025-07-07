package com.redutec.admin.bookmbti.survey.service;

import com.redutec.core.dto.BookMbtiSurveyDto;

public interface BookMbtiSurveyService {
    /**
     * 조건에 맞는 북BTI 설문 응답 목록 조회
     * @param findBookMbtiSurveyRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 북BTI 설문 응답 목록 및 페이징 정보
     */
    BookMbtiSurveyDto.BookMbtiSurveyPageResponse find(BookMbtiSurveyDto.FindBookMbtiSurveyRequest findBookMbtiSurveyRequest);

    /**
     * 특정 북BTI 설문 응답 조회
     * @param bookMbtiSurveyId 북BTI 설문 응답 고유번호
     * @return 특정 북BTI 설문 응답 응답 객체
     */
    BookMbtiSurveyDto.BookMbtiSurveyResponse findById(Long bookMbtiSurveyId);

    /**
     * 특정 북BTI 설문 응답 삭제
     * @param bookMbtiSurveyId 삭제할 북BTI 설문 응답의 ID
     */
    void delete(Long bookMbtiSurveyId);
}