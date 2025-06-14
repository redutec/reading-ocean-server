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
     * @return 생성된 Book 등록 엔티티
     */
    public Book toCreateEntity(
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
     * UpdateBookRequest DTO를 기반으로 Book 수정 엔티티를 생성합니다.
     *
     * @param updateBookRequest 배너 수정에 필요한 데이터를 담은 DTO
     * @return 생성된 Book 수정 엔티티
     */
    public Book toUpdateEntity(
            Book book,
            BookDto.UpdateBookRequest updateBookRequest,
            String coverImageFileName
    ) {
        return Book.builder()
                .id(book.getId())
                .isbn(Optional.ofNullable(updateBookRequest.isbn()).orElse(book.getIsbn()))
                .title(Optional.ofNullable(updateBookRequest.title()).orElse(book.getTitle()))
                .author(Optional.ofNullable(updateBookRequest.author()).orElse(book.getAuthor()))
                .publisher(Optional.ofNullable(updateBookRequest.publisher()).orElse(book.getPublisher()))
                .translator(Optional.ofNullable(updateBookRequest.translator()).orElse(book.getTranslator()))
                .illustrator(Optional.ofNullable(updateBookRequest.illustrator()).orElse(book.getIllustrator()))
                .publicationDate(Optional.ofNullable(updateBookRequest.publicationDate()).orElse(book.getPublicationDate()))
                .coverImageFileName(Optional.ofNullable(coverImageFileName).orElse(book.getCoverImageFileName()))
                .recommended(Optional.ofNullable(updateBookRequest.recommended()).orElse(book.getRecommended()))
                .ebookAvailable(Optional.ofNullable(updateBookRequest.ebookAvailable()).orElse(book.getEbookAvailable()))
                .audioBookAvailable(Optional.ofNullable(updateBookRequest.audioBookAvalable()).orElse(book.getAudioBookAvailable()))
                .visible(Optional.ofNullable(updateBookRequest.visible()).orElse(book.getVisible()))
                .enabled(Optional.ofNullable(updateBookRequest.enabled()).orElse(book.getEnabled()))
                .pageCount(Optional.ofNullable(updateBookRequest.pageCount()).orElse(book.getPageCount()))
                .schoolGrade(Optional.ofNullable(updateBookRequest.schoolGrade()).orElse(book.getSchoolGrade()))
                .genre(Optional.ofNullable(updateBookRequest.genre()).orElse(book.getGenre()))
                .subGenre(Optional.ofNullable(updateBookRequest.subGenre()).orElse(book.getSubGenre()))
                .bookPoints(Optional.ofNullable(updateBookRequest.bookPoints()).orElse(book.getBookPoints()))
                .raq(Optional.ofNullable(updateBookRequest.raq()).orElse(book.getRaq()))
                .readingLevel(Optional.ofNullable(updateBookRequest.readingLevel()).orElse(book.getReadingLevel()))
                .bookMbti(Optional.ofNullable(updateBookRequest.bookMbti()).orElse(book.getBookMbti()))
                .subject(Optional.ofNullable(updateBookRequest.subject()).orElse(book.getSubject()))
                .content(Optional.ofNullable(updateBookRequest.content()).orElse(book.getContent()))
                .awardHistory(Optional.ofNullable(updateBookRequest.awardHistory()).orElse(book.getAwardHistory()))
                .includedBookName(Optional.ofNullable(updateBookRequest.includedBookName()).orElse(book.getIncludedBookName()))
                .institutionRecommendations(Optional.ofNullable(updateBookRequest.institutionRecommendations()).orElse(book.getInstitutionRecommendations()))
                .educationOfficeRecommendations(Optional.ofNullable(updateBookRequest.educationOfficeRecommendations()).orElse(book.getEducationOfficeRecommendations()))
                .tags(Optional.ofNullable(updateBookRequest.tags()).orElse(book.getTags()))
                .build();
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