package com.redutec.admin.bookgroup.service;

import com.redutec.admin.bookgroup.dto.BookGroupDto;
import com.redutec.admin.bookgroup.mapper.BookGroupMapper;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.Book;
import com.redutec.core.repository.BookRepository;
import com.redutec.core.specification.BookSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BookGroupServiceImpl implements BookGroupService {
    private final BookGroupMapper bookGroupMapper;
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;

    /**
     * 도서 등록
     * @param createBookRequest 도서 등록 정보를 담은 DTO
     * @return 등록된 도서 정보
     */
    @Override
    @Transactional
    public BookGroupDto.BookResponse create(
            BookGroupDto.CreateBookRequest createBookRequest
    ) {
        return bookGroupMapper.toResponseDto(bookRepository.save(bookGroupMapper.toEntity(createBookRequest)));
    }

    /**
     * 조건에 맞는 도서 목록 조회
     * @param findBookRequest 조회 조건을 담은 DTO
     * @return 조회된 도서 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BookGroupDto.BookPageResponse find(
            BookGroupDto.FindBookRequest findBookRequest
    ) {
        return bookGroupMapper.toPageResponseDto(bookRepository.findAll(
                BookSpecification.findWith(bookGroupMapper.toCriteria(findBookRequest)),
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
    public BookGroupDto.BookResponse findById(
            Long bookId
    ) {
        return bookGroupMapper.toResponseDto(getBook(bookId));
    }

    /**
     * 특정 도서 엔티티 조회
     * @param bookId 도서 고유번호
     * @return 특정 도서 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Book getBook(
            Long bookId
    ) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("도서를 찾을 수 없습니다. id = " + bookId));
    }

    /**
     * 특정 도서 수정
     * @param bookId 수정할 도서의 ID
     * @param updateBookRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long bookId,
            BookGroupDto.UpdateBookRequest updateBookRequest
    ) {
        // 수정할 도서 엔티티 조회
        Book book = getBook(bookId);
        // 업로드할 커버 이미지 파일이 있는 경우 업로드하고 파일명을 생성
        String coverImageFileName = Optional.ofNullable(updateBookRequest.coverImageFile())
                .filter(coverImageFile -> !coverImageFile.isEmpty())
                .map(coverImageFile -> {
                    FileUploadResult result = fileUtil.uploadFile(coverImageFile, "/branch");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // UPDATE 도메인 메서드로 변환
        book.updateBook(
                updateBookRequest.isbn(),
                updateBookRequest.title(),
                updateBookRequest.author(),
                updateBookRequest.publisher(),
                updateBookRequest.translator(),
                updateBookRequest.illustrator(),
                updateBookRequest.publicationDate(),
                coverImageFileName,
                updateBookRequest.recommended(),
                updateBookRequest.ebookAvailable(),
                updateBookRequest.audiobookAvalable(),
                updateBookRequest.visible(),
                updateBookRequest.enabled(),
                updateBookRequest.pageCount(),
                updateBookRequest.schoolGrade(),
                updateBookRequest.genre(),
                updateBookRequest.subGenre(),
                updateBookRequest.bookPoints(),
                updateBookRequest.raq(),
                updateBookRequest.readingLevel(),
                updateBookRequest.bookMbti(),
                updateBookRequest.subject(),
                updateBookRequest.content(),
                updateBookRequest.awardHistory(),
                updateBookRequest.includedBookName(),
                updateBookRequest.institutionRecommendations(),
                updateBookRequest.educationOfficeRecommendations(),
                updateBookRequest.tags()
        );
        // 도서 엔티티 UPDATE
        bookRepository.save(book);
    }

    /**
     * 특정 도서 삭제
     * @param bookId 삭제할 도서의 ID
     */
    @Override
    @Transactional
    public void delete(Long bookId) {
        bookRepository.delete(getBook(bookId));
    }
}