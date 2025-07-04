package com.redutec.admin.bookmbti.question.service;

import com.redutec.core.dto.BookMbtiQuestionDto;
import com.redutec.core.entity.BookMbtiQuestion;
import com.redutec.core.mapper.BookMbtiQuestionMapper;
import com.redutec.core.repository.BookMbtiQuestionRepository;
import com.redutec.core.specification.BookMbtiQuestionSpecification;
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
public class BookMbtiQuestionServiceImpl implements BookMbtiQuestionService {
    private final BookMbtiQuestionMapper bookMbtiQuestionMapper;
    private final BookMbtiQuestionRepository bookMbtiQuestionRepository;

    /**
     * 북BTI 설문 문항 등록
     * @param createBookMbtiQuestionRequest 북BTI 설문 문항 등록 정보를 담은 DTO
     * @return 등록한 북BTI 설문 문항 정보
     */
    @Override
    @Transactional
    public BookMbtiQuestionDto.BookMbtiQuestionResponse create(
            BookMbtiQuestionDto.CreateBookMbtiQuestionRequest createBookMbtiQuestionRequest
    ) {
        return bookMbtiQuestionMapper.toResponseDto(
                bookMbtiQuestionRepository.save(bookMbtiQuestionMapper.createEntity(createBookMbtiQuestionRequest))
        );
    }

    /**
     * 조건에 맞는 북BTI 설문 문항 목록 조회
     * @param findBookMbtiQuestionRequest 조회 조건을 담은 DTO
     * @return 조회된 북BTI 설문 문항 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BookMbtiQuestionDto.BookMbtiQuestionPageResponse find(
            BookMbtiQuestionDto.FindBookMbtiQuestionRequest findBookMbtiQuestionRequest
    ) {
        Page<BookMbtiQuestion> page = bookMbtiQuestionRepository.findAll(
                BookMbtiQuestionSpecification.findWith(bookMbtiQuestionMapper.toCriteria(findBookMbtiQuestionRequest)),
                (findBookMbtiQuestionRequest.page() != null && findBookMbtiQuestionRequest.size() != null)
                        ? PageRequest.of(findBookMbtiQuestionRequest.page(), findBookMbtiQuestionRequest.size())
                        : Pageable.unpaged()
        );
        List<BookMbtiQuestionDto.BookMbtiQuestionResponse> bookMbtiQuestionResponses = page.getContent().stream()
                .map(bookMbtiQuestionMapper::toResponseDto)
                .collect(Collectors.toList());
        return new BookMbtiQuestionDto.BookMbtiQuestionPageResponse(
                bookMbtiQuestionResponses,
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    /**
     * 특정 북BTI 설문 문항 조회
     * @param bookMbtiQuestionId 북BTI 설문 문항 고유번호
     * @return 특정 북BTI 설문 문항 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BookMbtiQuestionDto.BookMbtiQuestionResponse findById(Long bookMbtiQuestionId) {
        return bookMbtiQuestionMapper.toResponseDto(getBookMbtiQuestion(bookMbtiQuestionId));
    }

    /**
     * 특정 북BTI 설문 문항 수정
     * @param bookMbtiQuestionId 수정할 북BTI 설문 문항의 ID
     * @param updateBookMbtiQuestionRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long bookMbtiQuestionId,
            BookMbtiQuestionDto.UpdateBookMbtiQuestionRequest updateBookMbtiQuestionRequest
    ) {
        bookMbtiQuestionMapper.updateEntity(
                getBookMbtiQuestion(bookMbtiQuestionId),
                updateBookMbtiQuestionRequest
        );
    }

    /**
     * 특정 북BTI 설문 문항 삭제
     * @param bookMbtiQuestionId 삭제할 북BTI 설문 문항의 ID
     */
    @Override
    @Transactional
    public void delete(Long bookMbtiQuestionId) {
        bookMbtiQuestionRepository.delete(getBookMbtiQuestion(bookMbtiQuestionId));
    }

    /**
     * 특정 북BTI 설문 문항 엔티티 조회
     * @param bookMbtiQuestionId 북BTI 설문 문항 고유번호
     * @return 특정 북BTI 설문 문항 엔티티 객체
     */
    @Transactional(readOnly = true)
    public BookMbtiQuestion getBookMbtiQuestion(Long bookMbtiQuestionId) {
        return bookMbtiQuestionRepository.findById(bookMbtiQuestionId)
                .orElseThrow(() -> new EntityNotFoundException("북BTI 설문 문항이 존재하지 않습니다. bookMbtiQuestionId: " + bookMbtiQuestionId));
    }
}