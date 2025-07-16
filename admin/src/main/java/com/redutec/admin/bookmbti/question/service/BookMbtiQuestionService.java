package com.redutec.admin.bookmbti.question.service;

import com.redutec.core.dto.BookMbtiQuestionDto;

public interface BookMbtiQuestionService {
    /**
     * 북BTI 설문 문항 등록
     * @param createBookMbtiQuestionRequest 북BTI 설문 문항 등록 정보를 담은 DTO
     * @return 등록된 북BTI 설문 문항 정보
     */
    BookMbtiQuestionDto.BookMbtiQuestionResponse create(BookMbtiQuestionDto.CreateBookMbtiQuestionRequest createBookMbtiQuestionRequest);

    /**
     * 조건에 맞는 북BTI 설문 문항 목록 조회
     * @param findBookMbtiQuestionRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 북BTI 설문 문항 목록 및 페이징 정보
     */
    BookMbtiQuestionDto.BookMbtiQuestionPageResponse find(BookMbtiQuestionDto.FindBookMbtiQuestionRequest findBookMbtiQuestionRequest);

    /**
     * 특정 북BTI 설문 문항 조회
     * @param bookMbtiQuestionId 북BTI 설문 문항 고유번호
     * @return 특정 북BTI 설문 문항 응답 객체
     */
    BookMbtiQuestionDto.BookMbtiQuestionResponse get(Long bookMbtiQuestionId);

    /**
     * 특정 북BTI 설문 문항 수정
     * @param bookMbtiQuestionId 수정할 북BTI 설문 문항의 ID
     * @param updateBookMbtiQuestionRequest 북BTI 설문 문항 수정 요청 객체
     */
    void update(Long bookMbtiQuestionId, BookMbtiQuestionDto.UpdateBookMbtiQuestionRequest updateBookMbtiQuestionRequest);

    /**
     * 특정 북BTI 설문 문항 삭제
     * @param bookMbtiQuestionId 삭제할 북BTI 설문 문항의 ID
     */
    void delete(Long bookMbtiQuestionId);
}