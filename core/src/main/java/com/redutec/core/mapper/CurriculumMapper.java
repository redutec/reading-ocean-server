package com.redutec.core.mapper;

import com.redutec.core.criteria.CurriculumCriteria;
import com.redutec.core.dto.CurriculumDto;
import com.redutec.core.entity.Curriculum;
import com.redutec.core.entity.Student;
import com.redutec.core.meta.CurriculumStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class CurriculumMapper {
    private final StudentMapper studentMapper;
    private final CurriculumBookMapper curriculumBookMapper;

    /**
     * CreateCurriculumRequest DTO를 기반으로 Curriculum 등록 엔티티를 생성합니다.
     * @param createCurriculumRequest Curriculum 등록에 필요한 데이터를 담은 DTO
     * @param student 커리큘럼을 진행할 학생의 엔티티
     * @return 등록할 Curriculum 엔티티
     */
    public Curriculum createEntity(
            CurriculumDto.CreateCurriculumRequest createCurriculumRequest,
            Student student
    ) {
        return Curriculum.builder()
                .student(student)
                .name(StringUtils.stripToNull(createCurriculumRequest.name()))
                .description(StringUtils.stripToNull(createCurriculumRequest.description()))
                .startDate(createCurriculumRequest.startDate())
                .endDate(createCurriculumRequest.endDate())
                .status(Optional.ofNullable(createCurriculumRequest.status())
                        .orElse(CurriculumStatus.PENDING))
                .build();
    }

    /**
     * UpdateCurriculumRequest DTO를 기반으로 Curriculum 엔티티를 수정합니다.
     * @param curriculum 수정할 Curriculum 엔티티
     * @param updateCurriculumRequest Curriculum 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(
            Curriculum curriculum,
            CurriculumDto.UpdateCurriculumRequest updateCurriculumRequest
    ) {
        Optional.ofNullable(StringUtils.stripToNull(updateCurriculumRequest.name()))
                .ifPresent(curriculum::setName);
        Optional.ofNullable(StringUtils.stripToNull(updateCurriculumRequest.description()))
                .ifPresent(curriculum::setDescription);
        Optional.ofNullable(updateCurriculumRequest.startDate())
                .ifPresent(curriculum::setStartDate);
        Optional.ofNullable(updateCurriculumRequest.endDate())
                .ifPresent(curriculum::setEndDate);
        Optional.ofNullable(updateCurriculumRequest.status())
                .ifPresent(curriculum::setStatus);
    }
    
    /**
     * 이 메서드는 현재 FindCurriculumRequest 객체를 기반으로
     * CurriculumCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 커리큘럼 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findCurriculumRequest 커리큘럼 조회 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 CurriculumCriteria 객체
     */
    public CurriculumCriteria toCriteria(CurriculumDto.FindCurriculumRequest findCurriculumRequest) {
        return new CurriculumCriteria(
                findCurriculumRequest.curriculumIds(),
                findCurriculumRequest.studentIds(),
                StringUtils.stripToNull(findCurriculumRequest.name()),
                StringUtils.stripToNull(findCurriculumRequest.description()),
                findCurriculumRequest.statuses(),
                StringUtils.stripToNull(findCurriculumRequest.title())
        );
    }

    /**
     * Curriculum 엔티티를 기반으로 응답용 CurriculumResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param curriculum 변환할 Curriculum 엔티티
     * @return Curriculum 엔티티의 데이터를 담은 CurriculumResponse DTO, curriculum가 null이면 null 반환
     */
    public CurriculumDto.CurriculumResponse toResponseDto(Curriculum curriculum) {
        return new CurriculumDto.CurriculumResponse(
                curriculum.getId(),
                studentMapper.toResponseDto(curriculum.getStudent()),
                curriculum.getName(),
                curriculum.getDescription(),
                curriculum.getStartDate(),
                curriculum.getEndDate(),
                curriculum.getStatus(),
                curriculum.getCurriculumBooks().stream()
                        .map(curriculumBookMapper::toResponseDto)
                        .collect(Collectors.toList()),
                curriculum.getCreatedAt(),
                curriculum.getUpdatedAt()
        );
    }

    /**
     * Page 형식의 Curriculum 엔티티 목록을 CurriculumPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param curriculumPage Page 형태로 조회된 Curriculum 엔티티 목록
     * @return Curriculum 엔티티 리스트와 페이지 정보를 담은 CurriculumPageResponse DTO, curriculumPage가 null이면 null 반환
     */
    public CurriculumDto.CurriculumPageResponse toPageResponseDto(Page<Curriculum> curriculumPage) {
        return Optional.ofNullable(curriculumPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new CurriculumDto.CurriculumPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}