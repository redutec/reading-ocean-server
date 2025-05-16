package com.redutec.admin.bookgroup.service;

import com.redutec.admin.book.service.BookService;
import com.redutec.core.dto.BookGroupDto;
import com.redutec.core.entity.BookGroup;
import com.redutec.core.mapper.BookGroupMapper;
import com.redutec.core.repository.BookGroupRepository;
import com.redutec.core.specification.BookGroupSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BookGroupServiceImpl implements BookGroupService {
    private final BookGroupMapper bookGroupMapper;
    private final BookGroupRepository bookGroupRepository;
    private final BookService bookService;

    /**
     * 도서 그룹 등록
     * @param createBookGroupRequest 도서 그룹 등록 정보를 담은 DTO
     * @return 등록된 도서 그룹 정보
     */
    @Override
    @Transactional
    public BookGroupDto.BookGroupResponse create(BookGroupDto.CreateBookGroupRequest createBookGroupRequest) {
        return bookGroupMapper.toResponseDto(bookGroupRepository.save(bookGroupMapper.toCreateEntity(
                createBookGroupRequest,
                Optional.ofNullable(createBookGroupRequest.bookIds())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(bookService::getBook)
                        .collect(Collectors.toList()))
        ));
    }

    /**
     * 조건에 맞는 도서 그룹 목록 조회
     * @param findBookGroupRequest 조회 조건을 담은 DTO
     * @return 조회된 도서 그룹 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BookGroupDto.BookGroupPageResponse find(BookGroupDto.FindBookGroupRequest findBookGroupRequest) {
        return bookGroupMapper.toPageResponseDto(bookGroupRepository.findAll(
                BookGroupSpecification.findWith(bookGroupMapper.toCriteria(findBookGroupRequest)),
                (findBookGroupRequest.page() != null && findBookGroupRequest.size() != null)
                        ? PageRequest.of(findBookGroupRequest.page(), findBookGroupRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 도서 그룹 조회
     * @param bookGroupId 도서 그룹 고유번호
     * @return 특정 도서 그룹 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BookGroupDto.BookGroupResponse findById(Long bookGroupId) {
        return bookGroupMapper.toResponseDto(getBookGroup(bookGroupId));
    }

    /**
     * 특정 도서 그룹 엔티티 조회
     * @param bookGroupId 도서 그룹 고유번호
     * @return 특정 도서 그룹 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BookGroup getBookGroup(Long bookGroupId) {
        return bookGroupRepository.findById(bookGroupId)
                .orElseThrow(() -> new EntityNotFoundException("도서 그룹을 찾을 수 없습니다. bookGroupId = " + bookGroupId));
    }

    /**
     * 특정 도서 그룹 수정
     * @param bookGroupId 수정할 도서 그룹의 ID
     * @param updateBookGroupRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long bookGroupId, BookGroupDto.UpdateBookGroupRequest updateBookGroupRequest) {
        bookGroupRepository.save(bookGroupMapper.toUpdateEntity(
                getBookGroup(bookGroupId),
                updateBookGroupRequest,
                Optional.ofNullable(updateBookGroupRequest.bookIds())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(bookService::getBook)
                        .collect(Collectors.toList())
        ));
    }

    /**
     * 특정 도서 그룹 삭제
     * @param bookGroupId 삭제할 도서 그룹의 ID
     */
    @Override
    @Transactional
    public void delete(Long bookGroupId) {
        bookGroupRepository.delete(getBookGroup(bookGroupId));
    }
}