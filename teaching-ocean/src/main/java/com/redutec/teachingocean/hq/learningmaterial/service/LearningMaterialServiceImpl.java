package com.redutec.teachingocean.hq.learningmaterial.service;

import com.redutec.core.dto.LearningMaterialDto;
import com.redutec.core.entity.LearningMaterial;
import com.redutec.core.mapper.LearningMaterialMapper;
import com.redutec.core.repository.LearningMaterialRepository;
import com.redutec.core.specification.LearningMaterialSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class LearningMaterialServiceImpl implements LearningMaterialService {
    private final LearningMaterialMapper learningMaterialMapper;
    private final LearningMaterialRepository learningMaterialRepository;

    /**
     * 조건에 맞는 학습자료 목록 조회
     * @param findLearningMaterialRequest 조회 조건을 담은 DTO
     * @return 조회된 학습자료 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public LearningMaterialDto.LearningMaterialPageResponse find(
            LearningMaterialDto.FindLearningMaterialRequest findLearningMaterialRequest
    ) {
        return learningMaterialMapper.toPageResponseDto(learningMaterialRepository.findAll(
                LearningMaterialSpecification.findWith(learningMaterialMapper.toCriteria(findLearningMaterialRequest)),
                (findLearningMaterialRequest.page() != null && findLearningMaterialRequest.size() != null)
                        ? PageRequest.of(findLearningMaterialRequest.page(), findLearningMaterialRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 학습자료 조회
     * @param learningMaterialId 학습자료 고유번호
     * @return 특정 학습자료 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public LearningMaterialDto.LearningMaterialResponse get(Long learningMaterialId) {
        return learningMaterialMapper.toResponseDto(getLearningMaterial(learningMaterialId));
    }

    /**
     * 특정 학습자료 엔티티 조회
     * @param learningMaterialId 학습자료 고유번호
     * @return 특정 학습자료 엔티티 객체
     */
    @Transactional(readOnly = true)
    public LearningMaterial getLearningMaterial(Long learningMaterialId) {
        return learningMaterialRepository.findById(learningMaterialId)
                .orElseThrow(() -> new EntityNotFoundException("학습자료 게시물을 찾을 수 없습니다. learningMaterialId: " + learningMaterialId));
    }
}