package com.redutec.teachingocean.homeroom.service;

import com.redutec.core.dto.HomeroomDto;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Student;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.HomeroomMapper;
import com.redutec.core.repository.HomeroomRepository;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.StudentRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.core.specification.HomeroomSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class HomeroomServiceImpl implements HomeroomService {
    private final HomeroomMapper homeroomMapper;
    private final HomeroomRepository homeroomRepository;
    private final InstituteRepository instituteRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    /**
     * 학급 등록
     * @param createHomeroomRequest 학급 등록 정보를 담은 DTO
     * @return 등록된 학급 정보
     */
    @Override
    @Transactional
    public HomeroomDto.HomeroomResponse create(HomeroomDto.CreateHomeroomRequest createHomeroomRequest) {
        // 등록 요청 객체에 교육기관이 존재한다면 교육기관 엔티티를 조회
        Institute institute = instituteRepository.findById(createHomeroomRequest.instituteId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교육기관입니다. instituteId = " + createHomeroomRequest.instituteId()));
        // 등록 요청 객체에 학급에 소속될 교사 목록이 있다면 교사 엔티티 리스트 조회
        List<Teacher> teachers = Optional.ofNullable(createHomeroomRequest.teacherIds())
                .map(teacherIds -> teacherIds.stream()
                        .map(teacherId -> teacherRepository.findById(teacherId)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교사입니다. teacherId = " + teacherId)))
                        .toList())
                .orElse(null);
        // 등록 요청 객체에 학급에 소속될 학생 목록이 있다면 학생 엔티티 리스트 조회
        List<Student> students = Optional.ofNullable(createHomeroomRequest.studentIds())
                .map(studentIds -> studentIds.stream()
                        .map(studentId -> studentRepository.findById(studentId)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학생입니다. studentId = " + studentId)))
                        .toList())
                .orElse(null);
        // 학급 등록 및 등록한 정보를 응답 객체로 리턴
        return homeroomMapper.toResponseDto(homeroomRepository.save(homeroomMapper.toCreateEntity(
                createHomeroomRequest,
                institute,
                teachers,
                students
        )));
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
        return homeroomMapper.toResponseDto(homeroomRepository.findById(homeroomId).orElse(null));
    }

    /**
     * 특정 학급 수정
     * @param homeroomId 수정할 학급의 ID
     * @param updateHomeroomRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long homeroomId, HomeroomDto.UpdateHomeroomRequest updateHomeroomRequest) {
        // 수정할 학급 조회
        Homeroom homeroom = homeroomRepository.findById(homeroomId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학급입니다. homeroomId = " + homeroomId));
        // 수정 요청 객체에 교육기관 고유번호가 있다면 교육기관 엔티티 조회
        Institute institute = Optional.ofNullable(updateHomeroomRequest.instituteId())
                .map(instituteId -> instituteRepository.findById(instituteId)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교육기관입니다. instituteId = " + instituteId)))
                .orElse(null);
        // 수정 요청 객체에 학급에 소속될 교사 목록이 있다면 교사 엔티티 리스트 조회
        List<Teacher> teachers = Optional.ofNullable(updateHomeroomRequest.teacherIds())
                .map(teacherIds -> teacherIds.stream()
                        .map(teacherId -> teacherRepository.findById(teacherId)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 교사입니다. teacherId = " + teacherId)))
                        .toList())
                .orElse(null);
        // 수정 요청 객체에 학급에 소속될 학생 목록이 있다면 학생 엔티티 리스트 조회
        List<Student> students = Optional.ofNullable(updateHomeroomRequest.studentIds())
                .map(studentIds -> studentIds.stream()
                        .map(studentId -> studentRepository.findById(studentId)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 학생입니다. studentId = " + studentId)))
                        .toList())
                .orElse(null);
        // 학급 수정
        homeroomRepository.save(homeroomMapper.toUpdateEntity(
                homeroom,
                updateHomeroomRequest,
                institute,
                teachers,
                students
        ));
    }

    /**
     * 특정 학급 삭제
     * @param homeroomId 삭제할 학급의 ID
     */
    @Override
    @Transactional
    public void delete(Long homeroomId) {
        homeroomRepository.deleteById(homeroomId);
    }
}