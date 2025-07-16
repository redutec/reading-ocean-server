package com.redutec.admin.book.service;

import com.redutec.core.dto.BookDto;

public interface BookService {
    /**
     * 도서 등록
     * @param createBookRequest 도서 등록 정보를 담은 DTO
     * @return 등록된 도서 정보
     */
    BookDto.BookResponse create(BookDto.CreateBookRequest createBookRequest);

    /**
     * 조건에 맞는 도서 목록 조회
     * @param findBookRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 도서 목록 및 페이징 정보
     */
    BookDto.BookPageResponse find(BookDto.FindBookRequest findBookRequest);

    /**
     * 특정 도서 조회
     * @param bookId 도서 고유번호
     * @return 특정 도서 응답 객체
     */
    BookDto.BookResponse get(Long bookId);

    /**
     * 특정 도서 수정
     * @param bookId 수정할 도서의 ID
     * @param updateBookRequest 도서 수정 요청 객체
     */
    void update(Long bookId, BookDto.UpdateBookRequest updateBookRequest);

    /**
     * 특정 도서 삭제
     * @param bookId 삭제할 도서의 ID
     */
    void delete(Long bookId);
}