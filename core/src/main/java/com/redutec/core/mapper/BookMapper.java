package com.redutec.core.mapper;

import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.BookCriteria;
import com.redutec.core.dto.BookDto;
import com.redutec.core.entity.Book;
import com.redutec.core.meta.BookGenre;
import com.redutec.core.meta.BookSubGenre;
import com.redutec.core.meta.SchoolGrade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BookMapper {
    private final FileUtil fileUtil;

    /**
     * CreateBookRequest DTO를 기반으로 Book 등록 엔티티를 생성합니다.
     *
     * @param createBookRequest 도서 생성에 필요한 데이터를 담은 DTO
     * @param coverImageFileName 커버 이미지 파일명
     * @return 등록할 Book 엔티티
     */
    public Book createEntity(
            BookDto.CreateBookRequest createBookRequest,
            String coverImageFileName
    ) {
        return Book.builder()
                .isbn(createBookRequest.isbn())
                .title(createBookRequest.title())
                .author(createBookRequest.author())
                .publisher(createBookRequest.publisher())
                .translator(createBookRequest.translator())
                .illustrator(createBookRequest.illustrator())
                .publicationDate(createBookRequest.publicationDate())
                .coverImageFileName(coverImageFileName)
                .recommended(createBookRequest.recommended())
                .ebookAvailable(createBookRequest.ebookAvailable())
                .audioBookAvailable(createBookRequest.audioBookAvalable())
                .visible(createBookRequest.visible())
                .enabled(createBookRequest.enabled())
                .pageCount(createBookRequest.pageCount())
                .schoolGrade(createBookRequest.schoolGrade())
                .genre(createBookRequest.genre())
                .subGenre(createBookRequest.subGenre())
                .bookPoints(createBookRequest.bookPoints())
                .raq(createBookRequest.raq())
                .readingLevel(createBookRequest.readingLevel())
                .bookMbti(createBookRequest.bookMbti())
                .subject(createBookRequest.subject())
                .content(createBookRequest.content())
                .awardHistory(createBookRequest.awardHistory())
                .includedBookName(createBookRequest.includedBookName())
                .institutionRecommendations(createBookRequest.institutionRecommendations())
                .educationOfficeRecommendations(createBookRequest.educationOfficeRecommendations())
                .tags(createBookRequest.tags())
                .build();
    }

    /**
     * UpdateBookRequest DTO를 기반으로 Book 엔티티를 수정합니다.
     * @param updateBookRequest 배너 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(
            Book book,
            BookDto.UpdateBookRequest updateBookRequest,
            String coverImageFileName
    ) {
        Optional.ofNullable(updateBookRequest.isbn()).ifPresent(book::setIsbn);
        Optional.ofNullable(updateBookRequest.title()).ifPresent(book::setTitle);
        Optional.ofNullable(updateBookRequest.author()).ifPresent(book::setAuthor);
        Optional.ofNullable(updateBookRequest.publisher()).ifPresent(book::setPublisher);
        Optional.ofNullable(updateBookRequest.translator()).ifPresent(book::setTranslator);
        Optional.ofNullable(updateBookRequest.illustrator()).ifPresent(book::setIllustrator);
        Optional.ofNullable(updateBookRequest.publicationDate()).ifPresent(book::setPublicationDate);
        Optional.ofNullable(coverImageFileName).ifPresent(book::setCoverImageFileName);
        Optional.ofNullable(updateBookRequest.recommended()).ifPresent(book::setRecommended);
        Optional.ofNullable(updateBookRequest.ebookAvailable()).ifPresent(book::setEbookAvailable);
        Optional.ofNullable(updateBookRequest.audioBookAvalable()).ifPresent(book::setAudioBookAvailable);
        Optional.ofNullable(updateBookRequest.visible()).ifPresent(book::setVisible);
        Optional.ofNullable(updateBookRequest.enabled()).ifPresent(book::setEnabled);
        Optional.ofNullable(updateBookRequest.pageCount()).ifPresent(book::setPageCount);
        Optional.ofNullable(updateBookRequest.schoolGrade()).ifPresent(book::setSchoolGrade);
        Optional.ofNullable(updateBookRequest.genre()).ifPresent(book::setGenre);
        Optional.ofNullable(updateBookRequest.subGenre()).ifPresent(book::setSubGenre);
        Optional.ofNullable(updateBookRequest.bookPoints()).ifPresent(book::setBookPoints);
        Optional.ofNullable(updateBookRequest.raq()).ifPresent(book::setRaq);
        Optional.ofNullable(updateBookRequest.readingLevel()).ifPresent(book::setReadingLevel);
        Optional.ofNullable(updateBookRequest.bookMbti()).ifPresent(book::setBookMbti);
        Optional.ofNullable(updateBookRequest.subject()).ifPresent(book::setSubject);
        Optional.ofNullable(updateBookRequest.content()).ifPresent(book::setContent);
        Optional.ofNullable(updateBookRequest.awardHistory()).ifPresent(book::setAwardHistory);
        Optional.ofNullable(updateBookRequest.includedBookName()).ifPresent(book::setIncludedBookName);
        Optional.ofNullable(updateBookRequest.institutionRecommendations()).ifPresent(book::setInstitutionRecommendations);
        Optional.ofNullable(updateBookRequest.educationOfficeRecommendations()).ifPresent(book::setEducationOfficeRecommendations);
        Optional.ofNullable(updateBookRequest.tags()).ifPresent(book::setTags);
    }
    
    /**
     * 이 메서드는 현재 FindBookRequest 객체를 기반으로
     * BookCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 도서 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 BookCriteria 객체
     */
    public BookCriteria toCriteria(BookDto.FindBookRequest findBookRequest) {
        return new BookCriteria(
                findBookRequest.bookIds(),
                findBookRequest.isbn(),
                findBookRequest.title(),
                findBookRequest.author(),
                findBookRequest.publisher(),
                findBookRequest.recommended(),
                findBookRequest.ebookAvailable(),
                findBookRequest.audioBookAvailable(),
                findBookRequest.visible(),
                findBookRequest.enabled(),
                findBookRequest.minimumPageCount(),
                findBookRequest.maximumPageCount(),
                findBookRequest.minimumSchoolGrade(),
                findBookRequest.maximumSchoolGrade(),
                findBookRequest.genres(),
                findBookRequest.subGenres(),
                findBookRequest.minimumBookPoints(),
                findBookRequest.maximumBookPoints(),
                findBookRequest.minimumRaq(),
                findBookRequest.maximumRaq(),
                findBookRequest.bookMbtiList(),
                findBookRequest.subject(),
                findBookRequest.content(),
                findBookRequest.tags()
        );
    }

    /**
     * Book 엔티티를 기반으로 응답용 BookResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param book 변환할 Book 엔티티 (null 가능)
     * @return Book 엔티티의 데이터를 담은 BookResponse DTO, book가 null이면 null 반환
     */
    public BookDto.BookResponse toResponseDto(Book book) {
        return Optional.ofNullable(book)
                .map(b -> new BookDto.BookResponse(
                        b.getId(),
                        b.getIsbn(),
                        b.getTitle(),
                        b.getAuthor(),
                        b.getPublisher(),
                        b.getTranslator(),
                        b.getIllustrator(),
                        b.getPublicationDate(),
                        b.getCoverImageFileName(),
                        b.getRecommended(),
                        b.getEbookAvailable(),
                        b.getAudioBookAvailable(),
                        b.getVisible(),
                        b.getEnabled(),
                        b.getPageCount(),
                        Optional.ofNullable(b.getSchoolGrade())
                                .map(SchoolGrade::getDisplayName)
                                .orElse(null),
                        Optional.ofNullable(b.getGenre())
                                .map(BookGenre::getDisplayName)
                                .orElse(null),
                        Optional.ofNullable(b.getSubGenre())
                                .map(BookSubGenre::getDisplayName)
                                .orElse(null),
                        b.getBookPoints(),
                        b.getRaq(),
                        b.getReadingLevel(),
                        b.getBookMbti(),
                        b.getSubject(),
                        b.getContent(),
                        b.getAwardHistory(),
                        b.getIncludedBookName(),
                        b.getInstitutionRecommendations(),
                        b.getEducationOfficeRecommendations(),
                        b.getTags(),
                        b.getCreatedAt(),
                        b.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Book 엔티티 목록을 BookPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param bookPage Page 형태로 조회된 Book 엔티티 목록 (null 가능)
     * @return Book 엔티티 리스트와 페이지 정보를 담은 BookPageResponse DTO, bookPage가 null이면 null 반환
     */
    public BookDto.BookPageResponse toPageResponseDto(Page<Book> bookPage) {
        return Optional.ofNullable(bookPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new BookDto.BookPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}