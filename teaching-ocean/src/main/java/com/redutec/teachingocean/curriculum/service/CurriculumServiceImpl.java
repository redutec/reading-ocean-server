package com.redutec.teachingocean.curriculum.service;

import com.redutec.core.dto.CurriculumDto;
import com.redutec.core.entity.Curriculum;
import com.redutec.core.entity.Student;
import com.redutec.core.mapper.CurriculumMapper;
import com.redutec.core.repository.CurriculumRepository;
import com.redutec.core.repository.StudentRepository;
import com.redutec.core.specification.CurriculumSpecification;
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
public class CurriculumServiceImpl implements CurriculumService {
    private final CurriculumMapper curriculumMapper;
    private final CurriculumRepository curriculumRepository;
    private final StudentRepository studentRepository;

    /**
     * 커리큘럼 등록
     * @param createCurriculumRequest 커리큘럼 등록 정보를 담은 DTO
     * @return 등록된 커리큘럼 정보
     */
    @Override
    @Transactional
    public CurriculumDto.CurriculumResponse create(CurriculumDto.CreateCurriculumRequest createCurriculumRequest) {
        // 등록 요청 객체의 학생 ID로 학생 엔티티 조회
        Student student = Optional.ofNullable(createCurriculumRequest.studentId())
                .flatMap(studentRepository::findById)
                .orElseThrow(()-> new EntityNotFoundException("학생이 존재하지 않습니다. studentId: " + createCurriculumRequest.studentId()));
        // 신규 커리큘럼을 INSERT 후 등록한 커리큘럼 정보를 응답 개체에 담아 리턴
        return curriculumMapper.toResponseDto(curriculumRepository.save(curriculumMapper.createEntity(
                createCurriculumRequest,
                student
        )));
    }

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
     * 특정 커리큘럼 수정
     * @param curriculumId 수정할 커리큘럼의 ID
     * @param updateCurriculumRequest 커리큘럼 수정 요청 객체
     */
    @Override
    @Transactional
    public void update(Long curriculumId, CurriculumDto.UpdateCurriculumRequest updateCurriculumRequest) {
        // 수정할 커리큘럼 엔티티 조회
        Curriculum curriculum = getCurriculum(curriculumId);
        // 커리큘럼 정보 수정
        curriculumMapper.updateEntity(curriculum, updateCurriculumRequest);
    }

    /**
     * 특정 커리큘럼 삭제
     * @param curriculumId 삭제할 커리큘럼의 ID
     */
    @Override
    @Transactional
    public void delete(Long curriculumId) {
        curriculumRepository.delete(getCurriculum(curriculumId));
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