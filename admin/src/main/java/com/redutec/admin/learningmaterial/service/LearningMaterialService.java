package com.redutec.admin.learningmaterial.service;

import com.redutec.core.dto.LearningMaterialDto;

public interface LearningMaterialService {
    /**
     * 학습 자료 게시물 등록
     * @param createLearningMaterialRequest 학습 자료 게시물 등록 정보를 담은 DTO
     * @return 등록된 학습 자료 게시물 정보
     */
    LearningMaterialDto.LearningMaterialResponse create(LearningMaterialDto.CreateLearningMaterialRequest createLearningMaterialRequest);

    /**
     * 조건에 맞는 학습 자료 게시물 목록 조회
     * @param findLearningMaterialRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 학습 자료 게시물 목록 및 페이징 정보
     */
    LearningMaterialDto.LearningMaterialPageResponse find(LearningMaterialDto.FindLearningMaterialRequest findLearningMaterialRequest);

    /**
     * 특정 학습 자료 게시물 조회
     * @param learningMaterialId 학습 자료 게시물 고유번호
     * @return 특정 학습 자료 게시물 응답 객체
     */
    LearningMaterialDto.LearningMaterialResponse get(Long learningMaterialId);

    /**
     * 특정 학습 자료 게시물 수정
     * @param learningMaterialId 수정할 학습 자료 게시물의 ID
     * @param updateLearningMaterialRequest 학습 자료 게시물 수정 요청 객체
     */
    void update(Long learningMaterialId, LearningMaterialDto.UpdateLearningMaterialRequest updateLearningMaterialRequest);

    /**
     * 특정 학습 자료 게시물 삭제
     * @param learningMaterialId 삭제할 학습 자료 게시물의 ID
     */
    void delete(Long learningMaterialId);
}