package com.redutec.admin.instituteclass.service;

import com.redutec.admin.institute.service.InstituteService;
import com.redutec.admin.instituteclass.dto.InstituteClassDto;
import com.redutec.admin.instituteclass.mapper.InstituteClassMapper;
import com.redutec.core.entity.InstituteClass;
import com.redutec.core.repository.InstituteClassRepository;
import com.redutec.core.specification.InstituteClassSpecification;
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
public class InstituteClassServiceImpl implements InstituteClassService {
    private final InstituteClassMapper instituteClassMapper;
    private final InstituteClassRepository instituteClassRepository;
    private final InstituteService instituteService;

    /**
     * 학급 등록
     * @param createInstituteClassRequest 학급 등록 정보를 담은 DTO
     * @return 등록된 학급 정보
     */
    @Override
    @Transactional
    public InstituteClassDto.InstituteClassResponse create(
            InstituteClassDto.CreateInstituteClassRequest createInstituteClassRequest
    ) {
        return instituteClassMapper.toResponseDto(
                instituteClassRepository.save(
                        instituteClassMapper.toEntity(
                                createInstituteClassRequest,
                                instituteService.getInstitute(createInstituteClassRequest.instituteId())
                        )
                )
        );
    }

    /**
     * 조건에 맞는 학급 목록 조회
     * @param findInstituteClassRequest 조회 조건을 담은 DTO
     * @return 조회된 학급 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteClassDto.InstituteClassPageResponse find(
            InstituteClassDto.FindInstituteClassRequest findInstituteClassRequest
    ) {
        return instituteClassMapper.toPageResponseDto(instituteClassRepository.findAll(
                InstituteClassSpecification.findWith(instituteClassMapper.toCriteria(findInstituteClassRequest)),
                (findInstituteClassRequest.page() != null && findInstituteClassRequest.size() != null)
                        ? PageRequest.of(findInstituteClassRequest.page(), findInstituteClassRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 학급 조회
     * @param instituteClassId 학급 고유번호
     * @return 특정 학급 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteClassDto.InstituteClassResponse findById(
            Long instituteClassId
    ) {
        return instituteClassMapper.toResponseDto(getInstituteClass(instituteClassId));
    }

    /**
     * 특정 학급 엔티티 조회
     * @param instituteClassId 학급 고유번호
     * @return 특정 학급 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteClass getInstituteClass(
            Long instituteClassId
    ) {
        return instituteClassRepository.findById(instituteClassId)
                .orElseThrow(() -> new EntityNotFoundException("No such institute class"));
    }

    /**
     * 특정 학급 수정
     * @param instituteClassId 수정할 학급의 ID
     * @param updateInstituteClassRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long instituteClassId,
            InstituteClassDto.UpdateInstituteClassRequest updateInstituteClassRequest
    ) {
        // 수정할 학급 엔티티 조회
        InstituteClass instituteClass = getInstituteClass(instituteClassId);
        // UPDATE 도메인 메서드로 변환
        instituteClass.updateInstituteClass(
                updateInstituteClassRequest.name(),
                Optional.ofNullable(updateInstituteClassRequest.instituteId())
                        .map(instituteService::getInstitute)
                        .orElse(null),
                updateInstituteClassRequest.description()
        );
        // 학급 엔티티 UPDATE
        instituteClassRepository.save(instituteClass);
    }

    /**
     * 특정 학급 삭제
     * @param instituteClassId 삭제할 학급의 ID
     */
    @Override
    @Transactional
    public void delete(
            Long instituteClassId
    ) {
        instituteClassRepository.delete(getInstituteClass(instituteClassId));
    }
}