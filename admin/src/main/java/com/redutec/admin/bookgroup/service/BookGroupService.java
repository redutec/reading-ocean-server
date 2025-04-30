package com.redutec.admin.bookgroup.service;

import com.redutec.admin.bookgroup.dto.BookGroupDto;
import com.redutec.core.entity.BookGroup;

public interface BookGroupService {
    /**
     * 도서 그룹 등록
     * @param createBookGroupRequest 도서 그룹 등록 정보를 담은 DTO
     * @return 등록된 도서 그룹 정보
     */
    BookGroupDto.BookGroupResponse create(BookGroupDto.CreateBookGroupRequest createBookGroupRequest);

    /**
     * 조건에 맞는 도서 그룹 목록 조회
     * @param findBookGroupRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 도서 그룹 목록 및 페이징 정보
     */
    BookGroupDto.BookGroupPageResponse find(BookGroupDto.FindBookGroupRequest findBookGroupRequest);

    /**
     * 특정 도서 그룹 조회
     * @param bookGroupId 도서 그룹 고유번호
     * @return 특정 도서 그룹 응답 객체
     */
    BookGroupDto.BookGroupResponse findById(Long bookGroupId);

    /**
     * 특정 도서 그룹 엔티티 조회
     * @param bookGroupId 도서 그룹 고유번호
     * @return 특정 도서 그룹 엔티티 객체
     */
    BookGroup getBookGroup(Long bookGroupId);

    /**
     * 특정 도서 그룹 수정
     * @param bookGroupId 수정할 도서 그룹의 ID
     * @param updateBookGroupRequest 도서 그룹 수정 요청 객체
     */
    void update(Long bookGroupId, BookGroupDto.UpdateBookGroupRequest updateBookGroupRequest);

    /**
     * 특정 도서 그룹 삭제
     * @param bookGroupId 삭제할 도서 그룹의 ID
     */
    void delete(Long bookGroupId);
}