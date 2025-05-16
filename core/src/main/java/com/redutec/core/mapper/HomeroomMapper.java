package com.redutec.core.mapper;

import com.redutec.core.criteria.HomeroomCriteria;
import com.redutec.core.dto.HomeroomDto;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Student;
import com.redutec.core.entity.Teacher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class HomeroomMapper {
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    /**
     * CreateHomeroomRequest DTO를 기반으로 Homeroom 등록 엔티티를 생성합니다.
     *
     * @param createHomeroomRequest 학급 등록에 필요한 데이터를 담은 DTO
     * @param institute 등록할 학급이 속할 교육기관 엔티티
     * @param students 등록할 학급에 속할 학생 엔티티 목록
     * @param teachers 등록할 학급에 속할 교사 엔티티 목록
     * @return 생성된 Homeroom 등록 엔티티
     */
    public Homeroom toCreateEntity(
            HomeroomDto.CreateHomeroomRequest createHomeroomRequest,
            Institute institute,
            List<Student> students,
            List<Teacher> teachers
    ) {
        return Homeroom.builder()
                .name(createHomeroomRequest.name())
                .institute(institute)
                .description(createHomeroomRequest.description())
                .students(students)
                .teachers(teachers)
                .build();
    }

    /**
     * UpdateHomeroomRequest DTO를 기반으로 Homeroom 수정 엔티티를 생성합니다.
     *
     * @param updateHomeroomRequest 학급 수정에 필요한 데이터를 담은 DTO
     * @param institute 등록할 학급이 속할 교육기관 엔티티
     * @param students 등록할 학급에 속할 학생 엔티티 목록
     * @param teachers 등록할 학급에 속할 교사 엔티티 목록
     * @return 생성된 Homeroom 수정 엔티티
     */
    public Homeroom toUpdateEntity(
            Homeroom homeroom,
            HomeroomDto.UpdateHomeroomRequest updateHomeroomRequest,
            Institute institute,
            List<Student> students,
            List<Teacher> teachers
    ) {
        return Homeroom.builder()
                .id(homeroom.getId())
                .name(Optional.ofNullable(updateHomeroomRequest.name()).orElse(homeroom.getName()))
                .institute(Optional.ofNullable(institute).orElse(homeroom.getInstitute()))
                .description(Optional.ofNullable(homeroom.getDescription()).orElse(homeroom.getDescription()))
                .students(Optional.ofNullable(students).orElse(homeroom.getStudents()))
                .teachers(Optional.ofNullable(teachers).orElse(homeroom.getTeachers()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindHomeroomRequest 객체를 기반으로
     * HomeroomCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 교육기관 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 HomeroomCriteria 객체
     */
    public HomeroomCriteria toCriteria(HomeroomDto.FindHomeroomRequest findHomeroomRequest) {
        return new HomeroomCriteria(
                findHomeroomRequest.homeroomIds(),
                findHomeroomRequest.name(),
                findHomeroomRequest.description(),
                findHomeroomRequest.instituteIds()
        );
    }

    /**
     * Homeroom 엔티티를 기반으로 응답용 HomeroomResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param homeroom 변환할 Homeroom 엔티티 (null 가능)
     * @return Homeroom 엔티티의 데이터를 담은 HomeroomResponse DTO, homeroom가 null이면 null 반환
     */
    public HomeroomDto.HomeroomResponse toResponseDto(Homeroom homeroom) {
        return Optional.ofNullable(homeroom)
                .map(hr -> {
                    var students = hr.getStudents().stream()
                            .map(studentMapper::toResponseDto)
                            .collect(Collectors.toList());
                    var teachers = hr.getTeachers().stream()
                            .map(teacherMapper::toResponseDto)
                            .collect(Collectors.toList());
                    return new HomeroomDto.HomeroomResponse(
                            hr.getId(),
                            hr.getName(),
                            hr.getDescription(),
                            hr.getInstitute().getId(),
                            hr.getInstitute().getName(),
                            students,
                            teachers,
                            hr.getCreatedAt(),
                            hr.getUpdatedAt()
                    );
                })
                .orElse(null);
    }

    /**
     * Page 형식의 Homeroom 엔티티 목록을 HomeroomPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param homeroomPage Page 형태로 조회된 Homeroom 엔티티 목록 (null 가능)
     * @return Homeroom 엔티티 리스트와 페이지 정보를 담은 HomeroomPageResponse DTO, homeroomPage가 null이면 null 반환
     */
    public HomeroomDto.HomeroomPageResponse toPageResponseDto(Page<Homeroom> homeroomPage) {
        return Optional.ofNullable(homeroomPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new HomeroomDto.HomeroomPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}