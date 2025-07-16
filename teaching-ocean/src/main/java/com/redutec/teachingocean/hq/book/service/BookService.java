package com.redutec.teachingocean.hq.book.service;

import com.redutec.core.dto.BookDto;

public interface BookService {
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
}