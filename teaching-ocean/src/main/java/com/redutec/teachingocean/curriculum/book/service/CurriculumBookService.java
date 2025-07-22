package com.redutec.teachingocean.curriculum.book.service;

import com.redutec.core.dto.CurriculumBookDto;

public interface CurriculumBookService {
    /**
     * 커리큘럼에 소속할 도서 등록
     * @param createCurriculumBookRequest 커리큘럼에 소속할 도서 등록 정보를 담은 DTO
     * @return 등록된 커리큘럼에 소속한 도서 정보
     */
    CurriculumBookDto.CurriculumBookResponse create(CurriculumBookDto.CreateCurriculumBookRequest createCurriculumBookRequest);
    
    /**
     * 조건에 맞는 커리큘럼에 소속한 도서 목록 조회
     * @param findCurriculumBookRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 커리큘럼에 소속한 도서 목록 및 페이징 정보
     */
    CurriculumBookDto.CurriculumBookPageResponse find(CurriculumBookDto.FindCurriculumBookRequest findCurriculumBookRequest);

    /**
     * 특정 커리큘럼에 소속한 도서 조회
     * @param curriculumBookId 커리큘럼에 소속할 도서 고유번호
     * @return 특정 커리큘럼에 소속한 도서 응답 객체
     */
    CurriculumBookDto.CurriculumBookResponse get(Long curriculumBookId);

    /**
     * 특정 커리큘럼에 소속한 도서 수정
     * @param curriculumBookId 수정할 커리큘럼에 소속한 도서의 ID
     * @param updateCurriculumBookRequest 커리큘럼에 소속한 도서 수정 요청 객체
     */
    void update(Long curriculumBookId, CurriculumBookDto.UpdateCurriculumBookRequest updateCurriculumBookRequest);

    /**
     * 특정 커리큘럼에 소속한 도서 삭제
     * @param curriculumBookId 삭제할 커리큘럼에 소속한 도서의 ID
     */
    void delete(Long curriculumBookId);
}