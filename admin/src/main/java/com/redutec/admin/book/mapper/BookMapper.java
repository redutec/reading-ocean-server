package com.redutec.admin.book.mapper;

import com.redutec.admin.book.dto.BookDto;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.BookCriteria;
import com.redutec.core.entity.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BookMapper {
    private final FileUtil fileUtil;

    /**
     * CreateBookRequest DTO를 기반으로 Book 엔티티를 생성합니다.
     *
     * @param createBookRequest 도서 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Book 엔티티
     */
    public Book toEntity(
            BookDto.CreateBookRequest createBookRequest
    ) {
        // 커버 디자인 파일이 존재하는 경우 파일을 업로드하고 파일명을 가져오기(파일이 없으면 파일명은 null)
        String coverImageFIleName = Optional.ofNullable(createBookRequest.coverImageFile())
                .filter(coverImageFile -> !coverImageFile.isEmpty())
                .map(coverImageFile -> {
                    FileUploadResult result = fileUtil.uploadFile(coverImageFile, "/book");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // Book 엔티티 Build
        return Book.builder()
                .isbn(createBookRequest.isbn())
                .title(createBookRequest.title())
                .author(createBookRequest.author())
                .publisher(createBookRequest.publisher())
                .translator(createBookRequest.translator())
                .illustrator(createBookRequest.illustrator())
                .publicationDate(createBookRequest.publicationDate())
                .coverImageFileName(coverImageFIleName)
                .recommended(createBookRequest.recommended())
                .ebookAvailable(createBookRequest.ebookAvailable())
                .audiobookAvailable(createBookRequest.audiobookAvalable())
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
     * 이 메서드는 현재 FindBookRequest 객체를 기반으로
     * BookCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 도서 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 BookCriteria 객체
     */
    public BookCriteria toCriteria(
            BookDto.FindBookRequest findBookRequest
    ) {
        return new BookCriteria(
                findBookRequest.bookIds(),
                findBookRequest.isbn(),
                findBookRequest.title(),
                findBookRequest.author(),
                findBookRequest.publisher(),
                findBookRequest.recommended(),
                findBookRequest.ebookAvailable(),
                findBookRequest.audiobookAvailable(),
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
    public BookDto.BookResponse toResponseDto(
            Book book
    ) {
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
                        b.getAudiobookAvailable(),
                        b.getVisible(),
                        b.getEnabled(),
                        b.getPageCount(),
                        b.getSchoolGrade().getDisplayName(),
                        b.getGenre().getDisplayName(),
                        b.getSubGenre().getDisplayName(),
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