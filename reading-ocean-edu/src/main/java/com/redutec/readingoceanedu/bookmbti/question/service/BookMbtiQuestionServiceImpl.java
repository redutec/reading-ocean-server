package com.redutec.readingoceanedu.bookmbti.question.service;

import com.redutec.core.dto.BookMbtiQuestionDto;
import com.redutec.core.entity.BookMbtiQuestion;
import com.redutec.core.mapper.BookMbtiQuestionMapper;
import com.redutec.core.repository.BookMbtiQuestionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BookMbtiQuestionServiceImpl implements BookMbtiQuestionService {
    private final BookMbtiQuestionMapper bookMbtiQuestionMapper;
    private final BookMbtiQuestionRepository bookMbtiQuestionRepository;

    /**
     * 조건에 맞는 북BTI 설문 문항 목록 조회
     * @return 조회된 북BTI 설문 문항 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public List<BookMbtiQuestionDto.BookMbtiQuestionResponse> findAll() {
        List<BookMbtiQuestion> bookMbtiQuestionResponses = bookMbtiQuestionRepository.findAll();
        return bookMbtiQuestionResponses.stream()
                .map(bookMbtiQuestionMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}