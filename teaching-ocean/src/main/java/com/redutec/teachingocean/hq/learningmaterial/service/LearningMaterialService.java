package com.redutec.teachingocean.hq.learningmaterial.service;

import com.redutec.core.dto.LearningMaterialDto;

public interface LearningMaterialService {
    /**
     * 조건에 맞는 학습자료 게시물 목록 조회
     * @param findLearningMaterialRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 학습자료 게시물 목록 및 페이징 정보
     */
    LearningMaterialDto.LearningMaterialPageResponse find(LearningMaterialDto.FindLearningMaterialRequest findLearningMaterialRequest);

    /**
     * 특정 학습자료 게시물 조회
     * @param learningMaterialId 학습자료 게시물 고유번호
     * @return 특정 학습자료 게시물 응답 객체
     */
    LearningMaterialDto.LearningMaterialResponse get(Long learningMaterialId);
}