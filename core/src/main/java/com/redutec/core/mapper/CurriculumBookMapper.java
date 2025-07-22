package com.redutec.core.mapper;

import com.redutec.core.criteria.CurriculumBookCriteria;
import com.redutec.core.dto.CurriculumBookDto;
import com.redutec.core.entity.Book;
import com.redutec.core.entity.Curriculum;
import com.redutec.core.entity.CurriculumBook;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CurriculumBookMapper {
    private final BookMapper bookMapper;

    /**
     * CreateCurriculumBookRequest DTO를 기반으로 CurriculumBook 등록 엔티티를 생성합니다.
     * @param createCurriculumBookRequest 커리큘럼 도서 배정 요청 DTO
     * @param curriculum 소속될 Curriculum 엔티티
     * @param book 읽어야 할 Book 엔티티
     * @return 등록할 CurriculumBook 엔티티
     */
    public CurriculumBook createEntity(
            CurriculumBookDto.CreateCurriculumBookRequest createCurriculumBookRequest,
            Curriculum curriculum,
            Book book
    ) {
        CurriculumBook curriculumBook = CurriculumBook.builder()
                .book(book)
                .readingOrder(createCurriculumBookRequest.readingOrder())
                .plannedReadingDate(createCurriculumBookRequest.plannedReadingDate())
                .readingStatus(createCurriculumBookRequest.readingStatus())
                .build();
        curriculumBook.setCurriculum(curriculum);
        return curriculumBook;
    }

    /**
     * UpdateCurriculumBookRequest DTO를 기반으로 CurriculumBook 엔티티를 수정합니다.
     * @param curriculumBook 수정할 CurriculumBook 엔티티
     * @param updateCurriculumBookRequest 커리큘럼 도서 배정 수정에 필요한 데이터를 담은 DTO
     * @param book 읽어야 할 도서의 엔티티
     */
    public void updateEntity(
            CurriculumBook curriculumBook,
            CurriculumBookDto.UpdateCurriculumBookRequest updateCurriculumBookRequest,
            Book book
    ) {
        Optional.ofNullable(book)
                .ifPresent(curriculumBook::setBook);
        Optional.ofNullable(updateCurriculumBookRequest.readingOrder())
                .ifPresent(curriculumBook::setReadingOrder);
        Optional.ofNullable(updateCurriculumBookRequest.plannedReadingDate())
                .ifPresent(curriculumBook::setPlannedReadingDate);
        Optional.ofNullable(updateCurriculumBookRequest.readingStatus())
                .ifPresent(curriculumBook::setReadingStatus);
    }

    /**
     * 이 메서드는 현재 FindCurriculumBookRequest 객체를 기반으로
     * CurriculumBookCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 커리큘럼 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findCurriculumBookRequest 커리큘럼 조회 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 CurriculumBookCriteria 객체
     */
    public CurriculumBookCriteria toCriteria(
            CurriculumBookDto.FindCurriculumBookRequest findCurriculumBookRequest
    ) {
        return new CurriculumBookCriteria(
                findCurriculumBookRequest.curriculumBookIds(),
                findCurriculumBookRequest.curriculumIds(),
                StringUtils.stripToNull(findCurriculumBookRequest.title()),
                findCurriculumBookRequest.readingStatuses()
        );
    }

    /**
     * Curriculum 엔티티를 기반으로 응답용 CurriculumResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param curriculumBook 변환할 CurriculumBook 엔티티
     * @return Curriculum 엔티티의 데이터를 담은 CurriculumResponse DTO, curriculum가 null이면 null 반환
     */
    public CurriculumBookDto.CurriculumBookResponse toResponseDto(CurriculumBook curriculumBook) {
        return new CurriculumBookDto.CurriculumBookResponse(
                curriculumBook.getId(),
                curriculumBook.getCurriculum().getId(),
                bookMapper.toResponseDto(curriculumBook.getBook()),
                curriculumBook.getReadingOrder(),
                curriculumBook.getPlannedReadingDate(),
                curriculumBook.getReadingStatus(),
                curriculumBook.getCreatedAt(),
                curriculumBook.getUpdatedAt()
        );
    }

    /**
     * Page 형식의 CurriculumBook 엔티티 목록을 CurriculumBookPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param curriculumBookPage Page 형태로 조회된 CurriculumBook 엔티티 목록
     * @return CurriculumBook 엔티티 리스트와 페이지 정보를 담은 CurriculumBookPageResponse DTO, curriculumBookPage가 null이면 null 반환
     */
    public CurriculumBookDto.CurriculumBookPageResponse toPageResponseDto(Page<CurriculumBook> curriculumBookPage) {
        return Optional.ofNullable(curriculumBookPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new CurriculumBookDto.CurriculumBookPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}