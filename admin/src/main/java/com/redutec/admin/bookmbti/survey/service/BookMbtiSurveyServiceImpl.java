package com.redutec.admin.bookmbti.survey.service;

import com.redutec.core.dto.BookMbtiSurveyDto;
import com.redutec.core.entity.BookMbtiSurvey;
import com.redutec.core.mapper.BookMbtiSurveyMapper;
import com.redutec.core.repository.BookMbtiSurveyRepository;
import com.redutec.core.specification.BookMbtiSurveySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BookMbtiSurveyServiceImpl implements BookMbtiSurveyService {
    private final BookMbtiSurveyMapper bookMbtiSurveyMapper;
    private final BookMbtiSurveyRepository bookMbtiSurveyRepository;

    /**
     * 조건에 맞는 북BTI 설문 응답 목록 조회
     * @param findBookMbtiSurveyRequest 조회 조건을 담은 DTO
     * @return 조회된 북BTI 설문 응답 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BookMbtiSurveyDto.BookMbtiSurveyPageResponse find(
            BookMbtiSurveyDto.FindBookMbtiSurveyRequest findBookMbtiSurveyRequest
    ) {
        Page<BookMbtiSurvey> page = bookMbtiSurveyRepository.findAll(
                BookMbtiSurveySpecification.findWith(bookMbtiSurveyMapper.toCriteria(findBookMbtiSurveyRequest)),
                (findBookMbtiSurveyRequest.page() != null && findBookMbtiSurveyRequest.size() != null)
                        ? PageRequest.of(findBookMbtiSurveyRequest.page(), findBookMbtiSurveyRequest.size())
                        : Pageable.unpaged()
        );
        List<BookMbtiSurveyDto.BookMbtiSurveyResponse> bookMbtiSurveyResponses = page.getContent().stream()
                .map(bookMbtiSurveyMapper::toResponseDto)
                .collect(Collectors.toList());
        return new BookMbtiSurveyDto.BookMbtiSurveyPageResponse(
                bookMbtiSurveyResponses,
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    /**
     * 특정 북BTI 설문 응답 조회
     * @param bookMbtiSurveyId 북BTI 설문 응답 고유번호
     * @return 특정 북BTI 설문 응답 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BookMbtiSurveyDto.BookMbtiSurveyResponse get(Long bookMbtiSurveyId) {
        return bookMbtiSurveyMapper.toResponseDto(getBookMbtiSurvey(bookMbtiSurveyId));
    }

    /**
     * 특정 북BTI 설문 응답 삭제
     * @param bookMbtiSurveyId 삭제할 북BTI 설문 응답의 ID
     */
    @Override
    @Transactional
    public void delete(Long bookMbtiSurveyId) {
        bookMbtiSurveyRepository.delete(getBookMbtiSurvey(bookMbtiSurveyId));
    }

    /**
     * 특정 북BTI 설문 응답 엔티티 조회
     * @param bookMbtiSurveyId 북BTI 설문 응답 고유번호
     * @return 특정 북BTI 설문 응답 엔티티 객체
     */
    @Transactional(readOnly = true)
    public BookMbtiSurvey getBookMbtiSurvey(Long bookMbtiSurveyId) {
        return bookMbtiSurveyRepository.findById(bookMbtiSurveyId)
                .orElseThrow(() -> new EntityNotFoundException("북BTI 설문 응답이 존재하지 않습니다. bookMbtiSurveyId: " + bookMbtiSurveyId));
    }
}