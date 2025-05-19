package com.redutec.teachingocean.homeroom.service;

import com.redutec.core.dto.HomeroomDto;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.mapper.HomeroomMapper;
import com.redutec.core.repository.HomeroomRepository;
import com.redutec.core.specification.HomeroomSpecification;
import com.redutec.teachingocean.institute.service.InstituteService;
import com.redutec.teachingocean.student.service.StudentService;
import com.redutec.teachingocean.teacher.service.TeacherService;
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
public class HomeroomServiceImpl implements HomeroomService {
    private final HomeroomMapper homeroomMapper;
    private final HomeroomRepository homeroomRepository;
    private final InstituteService instituteService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    /**
     * 학급 등록
     * @param createHomeroomRequest 학급 등록 정보를 담은 DTO
     * @return 등록된 학급 정보
     */
    @Override
    @Transactional
    public HomeroomDto.HomeroomResponse create(HomeroomDto.CreateHomeroomRequest createHomeroomRequest) {
        return homeroomMapper.toResponseDto(homeroomRepository.save(homeroomMapper.toCreateEntity(
                createHomeroomRequest,
                instituteService.getInstitute(createHomeroomRequest.instituteId()),
                        Optional.ofNullable(createHomeroomRequest.studentIds())
                                .map(studentIds -> studentIds.stream()
                                        .map(studentService::getStudent)
                                        .toList())
                                .orElse(null),
                        Optional.ofNullable(createHomeroomRequest.teacherIds())
                                .map(teacherIds -> teacherIds.stream()
                                        .map(teacherService::getTeacher)
                                        .toList())
                                .orElse(null)
                        )
                )
        );
    }

    /**
     * 조건에 맞는 학급 목록 조회
     * @param findHomeroomRequest 조회 조건을 담은 DTO
     * @return 조회된 학급 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public HomeroomDto.HomeroomPageResponse find(HomeroomDto.FindHomeroomRequest findHomeroomRequest) {
        return homeroomMapper.toPageResponseDto(homeroomRepository.findAll(
                HomeroomSpecification.findWith(homeroomMapper.toCriteria(findHomeroomRequest)),
                (findHomeroomRequest.page() != null && findHomeroomRequest.size() != null)
                        ? PageRequest.of(findHomeroomRequest.page(), findHomeroomRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 학급 조회
     * @param homeroomId 학급 고유번호
     * @return 특정 학급 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public HomeroomDto.HomeroomResponse findById(Long homeroomId) {
        return homeroomMapper.toResponseDto(getHomeroom(homeroomId));
    }

    /**
     * 특정 학급 엔티티 조회
     * @param homeroomId 학급 고유번호
     * @return 특정 학급 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Homeroom getHomeroom(Long homeroomId) {
        return homeroomRepository.findById(homeroomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학급입니다. homeroomId = " + homeroomId));
    }

    /**
     * 특정 학급 수정
     * @param homeroomId 수정할 학급의 ID
     * @param updateHomeroomRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long homeroomId, HomeroomDto.UpdateHomeroomRequest updateHomeroomRequest) {
        homeroomRepository.save(homeroomMapper.toUpdateEntity(
                getHomeroom(homeroomId),
                updateHomeroomRequest,
                Optional.ofNullable(updateHomeroomRequest.instituteId())
                        .map(instituteService::getInstitute)
                        .orElse(null),
                Optional.ofNullable(updateHomeroomRequest.studentIds())
                        .map(studentIds -> studentIds.stream()
                                .map(studentService::getStudent)
                                .toList())
                        .orElse(null),
                Optional.ofNullable(updateHomeroomRequest.teacherIds())
                        .map(teacherIds -> teacherIds.stream()
                                .map(teacherService::getTeacher)
                                .toList())
                        .orElse(null)
        ));
    }

    /**
     * 특정 학급 삭제
     * @param homeroomId 삭제할 학급의 ID
     */
    @Override
    @Transactional
    public void delete(Long homeroomId) {
        homeroomRepository.delete(getHomeroom(homeroomId));
    }
}