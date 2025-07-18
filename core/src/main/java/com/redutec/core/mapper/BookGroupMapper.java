package com.redutec.core.mapper;

import com.redutec.core.criteria.BookGroupCriteria;
import com.redutec.core.dto.BookGroupDto;
import com.redutec.core.entity.Book;
import com.redutec.core.entity.BookGroup;
import com.redutec.core.meta.SchoolGrade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BookGroupMapper {
    private final BookMapper bookMapper;

    /**
     * CreateBookGroupRequest DTO를 기반으로 BookGroup 등록 엔티티를 생성합니다.
     *
     * @param createBookGroupRequest 도서 그룹 등록에 필요한 데이터를 담은 DTO
     * @param books 등록할 도서 그룹에 포함할 도서 목록
     * @return 등록할 BookGroup 엔티티
     */
    public BookGroup createEntity(
            BookGroupDto.CreateBookGroupRequest createBookGroupRequest,
            List<Book> books
    ) {
        return BookGroup.builder()
                .name(StringUtils.stripToNull(createBookGroupRequest.name()))
                .yearMonth(StringUtils.stripToNull(createBookGroupRequest.yearMonth()))
                .type(createBookGroupRequest.type())
                .schoolGrade(createBookGroupRequest.schoolGrade())
                .books(books)
                .build();
    }

    /**
     * UpdateBookGroupRequest DTO를 기반으로 BookGroup 엔티티를 수정합니다.
     *
     * @param bookGroup 수정할 도서 그룹 엔티티
     * @param updateBookGroupRequest 도서 그룹 수정에 필요한 데이터를 담은 DTO
     * @param books 수정할 도서 그룹에 포함할 도서 목록
     */
    public void updateEntity(
            BookGroup bookGroup,
            BookGroupDto.UpdateBookGroupRequest updateBookGroupRequest,
            List<Book> books
    ) {
        Optional.ofNullable(StringUtils.stripToNull(updateBookGroupRequest.name()))
                .ifPresent(bookGroup::setName);
        Optional.ofNullable(StringUtils.stripToNull(updateBookGroupRequest.yearMonth()))
                .ifPresent(bookGroup::setYearMonth);
        Optional.ofNullable(updateBookGroupRequest.type()).ifPresent(bookGroup::setType);
        Optional.ofNullable(updateBookGroupRequest.schoolGrade()).ifPresent(bookGroup::setSchoolGrade);
        Optional.ofNullable(books).ifPresent(bookGroup::setBooks);
    }
    
    /**
     * 이 메서드는 현재 FindBookGroupRequest 객체를 기반으로
     * BookGroupCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 도서 그룹 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 BookGroupCriteria 객체
     */
    public BookGroupCriteria toCriteria(BookGroupDto.FindBookGroupRequest findBookGroupRequest) {
        return new BookGroupCriteria(
                findBookGroupRequest.bookGroupIds(),
                StringUtils.stripToNull(findBookGroupRequest.name()),
                StringUtils.stripToNull(findBookGroupRequest.yearMonth()),
                findBookGroupRequest.types(),
                findBookGroupRequest.schoolGrades()
        );
    }

    /**
     * BookGroup 엔티티를 기반으로 응답용 BookGroupResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param bookGroup 변환할 BookGroup 엔티티
     * @return BookGroup 엔티티의 데이터를 담은 BookGroupResponse DTO, bookGroup가 null이면 null 반환
     */
    public BookGroupDto.BookGroupResponse toResponseDto(BookGroup bookGroup) {
        return Optional.ofNullable(bookGroup)
                .map(bg -> new BookGroupDto.BookGroupResponse(
                        bg.getId(),
                        bg.getName(),
                        bg.getYearMonth(),
                        bg.getType(),
                        Optional.ofNullable(bg.getSchoolGrade())
                                .map(SchoolGrade::getDisplayName)
                                .orElse(null),
                        bg.getBooks().stream()
                                .map(bookMapper::toResponseDto)
                                .collect(Collectors.toList()),
                        bg.getCreatedAt(),
                        bg.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 BookGroup 엔티티 목록을 BookGroupPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param bookGroupPage Page 형태로 조회된 BookGroup 엔티티 목록
     * @return BookGroup 엔티티 리스트와 페이지 정보를 담은 BookGroupPageResponse DTO, bookGroupPage가 null이면 null 반환
     */
    public BookGroupDto.BookGroupPageResponse toPageResponseDto(Page<BookGroup> bookGroupPage) {
        return Optional.ofNullable(bookGroupPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new BookGroupDto.BookGroupPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}