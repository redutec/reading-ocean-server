package com.redutec.readingoceanedu.bookmbti.survey.service;

import com.redutec.core.dto.BookMbtiQuestionDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BookMbtiSurveyServiceImpl implements BookMbtiSurveyService {
    /**
     * 현재 로그인한 학생의 설문 응답 조회
     * @return 현재 로그인한 학생의 설문 응답 목록 및 페이징 정보
     */
    @Override
    public List<BookMbtiQuestionDto.BookMbtiQuestionResponse> find() {
        return List.of();
    }
}