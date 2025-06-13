package com.redutec.teachingocean.headquarters.book.service;

import com.redutec.core.config.FileUtil;
import com.redutec.core.dto.BookDto;
import com.redutec.core.entity.Book;
import com.redutec.core.mapper.BookMapper;
import com.redutec.core.repository.BookRepository;
import com.redutec.core.specification.BookSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;

    /**
     * 조건에 맞는 도서 목록 조회
     * @param findBookRequest 조회 조건을 담은 DTO
     * @return 조회된 도서 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BookDto.BookPageResponse find(BookDto.FindBookRequest findBookRequest) {
        return bookMapper.toPageResponseDto(bookRepository.findAll(
                BookSpecification.findWith(bookMapper.toCriteria(findBookRequest)),
                (findBookRequest.page() != null && findBookRequest.size() != null)
                        ? PageRequest.of(findBookRequest.page(), findBookRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 도서 조회
     * @param bookId 도서 고유번호
     * @return 특정 도서 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BookDto.BookResponse findById(Long bookId) {
        return bookMapper.toResponseDto(getBook(bookId));
    }

    /**
     * 특정 도서 엔티티 조회
     * @param bookId 도서 고유번호
     * @return 특정 도서 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("도서를 찾을 수 없습니다. bookId: " + bookId));
    }
}