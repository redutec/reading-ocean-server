package com.redutec.teachingocean.curriculum.book.service;

import com.redutec.core.dto.CurriculumBookDto;
import com.redutec.core.entity.Book;
import com.redutec.core.entity.Curriculum;
import com.redutec.core.entity.CurriculumBook;
import com.redutec.core.mapper.CurriculumBookMapper;
import com.redutec.core.repository.BookRepository;
import com.redutec.core.repository.CurriculumBookRepository;
import com.redutec.core.repository.CurriculumRepository;
import com.redutec.core.specification.CurriculumBookSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CurriculumBookServiceImpl implements CurriculumBookService {
    private final CurriculumBookMapper curriculumBookMapper;
    private final CurriculumBookRepository curriculumBookRepository;
    private final CurriculumRepository curriculumRepository;
    private final BookRepository bookRepository;

    /**
     * 커리큘럼에 소속할 도서 등록
     *
     * @param createCurriculumBookRequest 커리큘럼에 소속할 도서 등록 정보를 담은 DTO
     * @return 등록된 커리큘럼에 소속한 도서 정보
     */
    @Override
    @Transactional
    public CurriculumBookDto.CurriculumBookResponse create(
            CurriculumBookDto.CreateCurriculumBookRequest createCurriculumBookRequest
    ) {
        // 등록 요청 객체의 커리큘럼 ID에 해당하는 커리큘럼 엔티티 조회
        Curriculum curriculum = Optional.ofNullable(createCurriculumBookRequest.curriculumId())
                .flatMap(curriculumRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("커리큘럼이 존재하지 않습니다. curriculumId: " + createCurriculumBookRequest.curriculumId()));
        // 등록 요청 객체의 도서 ID에 해당하는 도서 엔티티 조회
        Book book = Optional.ofNullable(createCurriculumBookRequest.bookId())
                .flatMap(bookRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("도서가 존재하지 않습니다. bookId: " + createCurriculumBookRequest.bookId()));
        // 신규 커리큘럼에 등록할 도서를 INSERT 후 응답 객체에 담아 리턴
        return curriculumBookMapper.toResponseDto(curriculumBookRepository.save(curriculumBookMapper.createEntity(
                createCurriculumBookRequest,
                curriculum,
                book
        )));
    }

    /**
     * 조건에 맞는 커리큘럼에 소속한 도서 목록 조회
     *
     * @param findCurriculumBookRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 커리큘럼에 소속한 도서 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public CurriculumBookDto.CurriculumBookPageResponse find(CurriculumBookDto.FindCurriculumBookRequest findCurriculumBookRequest) {
        return curriculumBookMapper.toPageResponseDto(curriculumBookRepository.findAll(
                CurriculumBookSpecification.findWith(curriculumBookMapper.toCriteria(findCurriculumBookRequest)),
                (findCurriculumBookRequest.page() != null && findCurriculumBookRequest.size() != null)
                        ? PageRequest.of(findCurriculumBookRequest.page(), findCurriculumBookRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 커리큘럼에 소속한 도서 조회
     *
     * @param curriculumBookId 커리큘럼에 소속할 도서 고유번호
     * @return 특정 커리큘럼에 소속한 도서 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public CurriculumBookDto.CurriculumBookResponse get(Long curriculumBookId) {
        return curriculumBookMapper.toResponseDto(getCurriculumBook(curriculumBookId));
    }

    /**
     * 특정 커리큘럼에 소속한 도서 수정
     *
     * @param curriculumBookId 수정할 커리큘럼에 소속한 도서의 ID
     * @param updateCurriculumBookRequest 커리큘럼에 소속한 도서 수정 요청 객체
     */
    @Override
    @Transactional
    public void update(
            Long curriculumBookId,
            CurriculumBookDto.UpdateCurriculumBookRequest updateCurriculumBookRequest
    ) {
        // 수정할 커리큘럼에 소속한 도서 엔티티 조회
        CurriculumBook curriculumBook = getCurriculumBook(curriculumBookId);
        // 수정 요청 객체의 도서 ID에 해당하는 도서 엔티티 조회
        Book book = Optional.ofNullable(updateCurriculumBookRequest.bookId())
                .flatMap(bookRepository::findById)
                .orElseThrow(() -> new EntityNotFoundException("도서가 존재하지 않습니다. bookId: " + updateCurriculumBookRequest.bookId()));
        // 커리큘럼에 소속한 도서 정보 수정
        curriculumBookMapper.updateEntity(curriculumBook, updateCurriculumBookRequest, book);
    }

    /**
     * 특정 커리큘럼에 소속한 도서 삭제
     *
     * @param curriculumBookId 삭제할 커리큘럼에 소속한 도서의 ID
     */
    @Override
    @Transactional
    public void delete(Long curriculumBookId) {
        curriculumBookRepository.deleteById(curriculumBookId);
    }

    /**
     * 특정 커리큘럼에 소속한 도서 엔티티 조회
     * @param curriculumBookId 특정 커리큘럼에 소속한 도서 고유번호
     * @return 특정 커리큘럼에 소속한 도서 엔티티
     */
    @Transactional(readOnly = true)
    public CurriculumBook getCurriculumBook(Long curriculumBookId) {
        return curriculumBookRepository.findById(curriculumBookId)
                .orElseThrow(() -> new EntityNotFoundException("커리큘럼에 소속한 도서를 찾을 수 없습니다. curriculumBookId: " + curriculumBookId));
    }
}