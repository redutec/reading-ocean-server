package com.redutec.teachingocean.curriculum.service;

import com.redutec.core.dto.CurriculumDto;

public interface CurriculumService {
    /**
     * 커리큘럼 등록
     * @param createCurriculumRequest 커리큘럼 등록 정보를 담은 DTO
     * @return 등록된 커리큘럼 정보
     */
    CurriculumDto.CurriculumResponse create(CurriculumDto.CreateCurriculumRequest createCurriculumRequest);
    
    /**
     * 조건에 맞는 커리큘럼 목록 조회
     * @param findCurriculumRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 커리큘럼 목록 및 페이징 정보
     */
    CurriculumDto.CurriculumPageResponse find(CurriculumDto.FindCurriculumRequest findCurriculumRequest);

    /**
     * 특정 커리큘럼 조회
     * @param curriculumId 커리큘럼 고유번호
     * @return 특정 커리큘럼 응답 객체
     */
    CurriculumDto.CurriculumResponse get(Long curriculumId);

    /**
     * 특정 커리큘럼 수정
     * @param curriculumId 수정할 커리큘럼의 ID
     * @param updateCurriculumRequest 커리큘럼 수정 요청 객체
     */
    void update(Long curriculumId, CurriculumDto.UpdateCurriculumRequest updateCurriculumRequest);

    /**
     * 특정 커리큘럼 삭제
     * @param curriculumId 삭제할 커리큘럼의 ID
     */
    void delete(Long curriculumId);
}