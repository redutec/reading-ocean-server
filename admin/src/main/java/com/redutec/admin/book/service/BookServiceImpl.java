package com.redutec.admin.book.service;

import com.redutec.admin.book.dto.BookDto;
import com.redutec.admin.book.mapper.BookMapper;
import com.redutec.admin.branch.service.BranchService;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Book;
import com.redutec.core.entity.Teacher;
import com.redutec.core.meta.TeacherRole;
import com.redutec.core.repository.BookRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.BookSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BranchService branchService;
    private final TeacherRepository teacherRepository;

    /**
     * 도서 등록
     * @param createBookRequest 도서 등록 정보를 담은 DTO
     * @return 등록된 도서 정보
     */
    @Override
    @Transactional
    public BookDto.BookResponse create(
            BookDto.CreateBookRequest createBookRequest
    ) {
        // 도서 등록
        Book book = bookRepository.save(
                bookMapper.toEntity(
                        createBookRequest,
                        Optional.ofNullable(createBookRequest.branchId())
                                .map(branchService::getBranch)
                                .orElse(null)
                )
        );
        // 지사가 존재하면 조회, 없으면 null
        Branch branch = Optional.ofNullable(book.getBranch())
                .map(Branch::getId)
                .map(branchService::getBranch)
                .orElse(null);
        // 응답 객체에 담아 리턴
        return bookMapper.toResponseDto(book, null, branch);
    }

    /**
     * 조건에 맞는 도서 목록 조회
     * @param findBookRequest 조회 조건을 담은 DTO
     * @return 조회된 도서 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public BookDto.BookPageResponse find(
            BookDto.FindBookRequest findBookRequest
    ) {
        Page<Book> page = bookRepository.findAll(
                BookSpecification.findWith(bookMapper.toCriteria(findBookRequest)),
                (findBookRequest.page() != null && findBookRequest.size() != null)
                        ? PageRequest.of(findBookRequest.page(), findBookRequest.size())
                        : Pageable.unpaged()
        );
        List<BookDto.BookResponse> bookResponses = page.getContent().stream()
                .map(book -> {
                    // Chief 교사 조회
                    Teacher chiefTeacher = teacherRepository.findByBookAndRole(book, TeacherRole.CHIEF)
                            .orElse(null);
                    // Branch가 있을 때만 조회, 없으면 null
                    Branch branch = Optional.ofNullable(book.getBranch())
                            .map(Branch::getId)
                            .map(branchService::getBranch)
                            .orElse(null);
                    return bookMapper.toResponseDto(book, chiefTeacher, branch);
                })
                .collect(Collectors.toList());
        return new BookDto.BookPageResponse(
                bookResponses,
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    /**
     * 특정 도서 조회
     * @param bookId 도서 고유번호
     * @return 특정 도서 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BookDto.BookResponse findById(
            Long bookId
    ) {
        // 도서과 원장 교사, 지사 조회
        Book book = getBook(bookId);
        Teacher chiefTeacher = teacherRepository.findByBookAndRole(book, TeacherRole.CHIEF)
                .orElseThrow(() -> new EntityNotFoundException("No such chief teacher"));
        Branch branch = branchService.getBranch(book.getBranch().getId());
        return bookMapper.toResponseDto(
                book,
                chiefTeacher,
                branch);
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
                .orElseThrow(() -> new EntityNotFoundException("No such book"));
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
            BookDto.UpdateBookRequest updateBookRequest
    ) {
        // 수정할 도서 엔티티 조회
        Book book = getBook(bookId);
        // UPDATE 도메인 메서드로 변환
        book.updateBook(
                updateBookRequest.name(),
                updateBookRequest.businessRegistrationName(),
                updateBookRequest.address(),
                updateBookRequest.zipCode(),
                updateBookRequest.phoneNumber(),
                updateBookRequest.url(),
                updateBookRequest.naverPlaceUrl(),
                updateBookRequest.type(),
                updateBookRequest.managementType(),
                updateBookRequest.status(),
                updateBookRequest.operationStatus(),
                Optional.ofNullable(updateBookRequest.branchId())
                        .map(branchService::getBranch)
                        .orElse(null)
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
    public void delete(
            Long bookId
    ) {
        bookRepository.delete(getBook(bookId));
    }
}