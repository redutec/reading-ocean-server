package com.redutec.admin.book.service;

import com.redutec.core.config.FileUtil;
import com.redutec.core.dto.BookDto;
import com.redutec.core.entity.Book;
import com.redutec.core.mapper.BookMapper;
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
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final FileUtil fileUtil;

    /**
     * 도서 등록
     * @param createBookRequest 도서 등록 정보를 담은 DTO
     * @return 등록된 도서 정보
     */
    @Override
    @Transactional
    public BookDto.BookResponse create(BookDto.CreateBookRequest createBookRequest) {
        // 도서 등록 요청 객체에 커버 이미지 파일이 존재한다면 업로드 후 파일명을 변수에 담기
        String coverImageFileName = Optional.ofNullable(createBookRequest.coverImageFile())
                .filter(coverImageFile -> !coverImageFile.isEmpty())
                .map(coverImageFile -> Paths.get(fileUtil.uploadFile(coverImageFile, "/book").filePath()).getFileName().toString()
        )
                .orElse(null);
        // 도서 등록
        return bookMapper.toResponseDto(bookRepository.save(bookMapper.createEntity(
                createBookRequest,
                coverImageFileName
        )));
    }

    /**
     * 조건에 맞는 도서 목록 조회
     * @param findBookRequest 조회 조건을 담은 DTO
     * @return 조회된 도서 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BookDto.BookPageResponse find(BookDto.FindBookRequest findBookRequest) {
        return bookMapper.toPageResponseDto(bookRepository.findAll(
                BookSpecification.findWith(bookMapper.toCriteria(findBookRequest)),
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
    public BookDto.BookResponse findById(Long bookId) {
        return bookMapper.toResponseDto(getBook(bookId));
    }

    /**
     * 특정 도서 수정
     * @param bookId 수정할 도서의 ID
     * @param updateBookRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long bookId, BookDto.UpdateBookRequest updateBookRequest) {
        // 수정할 도서 엔티티 조회
        Book book = getBook(bookId);
        // 도서 수정 요청 객체에 커버 이미지 파일이 있다면 업로드하고 파일명을 가져오기
        String coverImageFileName = Optional.ofNullable(updateBookRequest.coverImageFile())
                .filter(coverImageFile -> !coverImageFile.isEmpty())
                .map(coverImageFile -> Paths.get(fileUtil.uploadFile(coverImageFile, "/book").filePath()).getFileName().toString())
                .orElseGet(book::getCoverImageFileName);
        // 도서 수정
        bookMapper.updateEntity(book, updateBookRequest, coverImageFileName);
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

    /**
     * 특정 도서 엔티티 조회
     * @param bookId 도서 고유번호
     * @return 특정 도서 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("도서를 찾을 수 없습니다. bookId: " + bookId));
    }
}