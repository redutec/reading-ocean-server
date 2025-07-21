package com.redutec.admin.curriculum.service;

import com.redutec.core.dto.CurriculumDto;
import com.redutec.core.entity.Curriculum;
import com.redutec.core.mapper.CurriculumMapper;
import com.redutec.core.repository.CurriculumRepository;
import com.redutec.core.specification.CurriculumSpecification;
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
public class CurriculumServiceImpl implements CurriculumService {
    private final CurriculumMapper curriculumMapper;
    private final CurriculumRepository curriculumRepository;

    /**
     * 조건에 맞는 커리큘럼 목록 조회
     * @param findCurriculumRequest 조회 조건을 담은 DTO
     * @return 조회된 커리큘럼 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public CurriculumDto.CurriculumPageResponse find(CurriculumDto.FindCurriculumRequest findCurriculumRequest) {
        return curriculumMapper.toPageResponseDto(curriculumRepository.findAll(
                CurriculumSpecification.findWith(curriculumMapper.toCriteria(findCurriculumRequest)),
                (findCurriculumRequest.page() != null && findCurriculumRequest.size() != null)
                        ? PageRequest.of(findCurriculumRequest.page(), findCurriculumRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 커리큘럼 조회
     * @param curriculumId 커리큘럼 고유번호
     * @return 특정 커리큘럼 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public CurriculumDto.CurriculumResponse get(Long curriculumId) {
        return curriculumMapper.toResponseDto(getCurriculum(curriculumId));
    }

    /**
     * 특정 커리큘럼 엔티티 조회
     * @param curriculumId 커리큘럼 고유번호
     * @return 특정 커리큘럼 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Curriculum getCurriculum(Long curriculumId) {
        return curriculumRepository.findById(curriculumId)
                .orElseThrow(() -> new EntityNotFoundException("커리큘럼를 찾을 수 없습니다. curriculumId: " + curriculumId));
    }
}