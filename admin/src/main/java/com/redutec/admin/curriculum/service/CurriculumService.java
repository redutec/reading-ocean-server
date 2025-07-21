package com.redutec.admin.curriculum.service;

import com.redutec.core.dto.CurriculumDto;

public interface CurriculumService {
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
}