package com.redutec.admin.instituteclass.mapper;

import com.redutec.admin.instituteclass.dto.InstituteClassDto;
import com.redutec.core.criteria.InstituteClassCriteria;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.InstituteClass;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class InstituteClassMapper {
    /**
     * CreateInstituteClassRequest DTO를 기반으로 InstituteClass 엔티티를 생성합니다.
     *
     * @param createInstituteClassRequest 교육기관 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Institute 엔티티
     */
    public InstituteClass toEntity(
            InstituteClassDto.CreateInstituteClassRequest createInstituteClassRequest,
            Institute institute
    ) {
        return InstituteClass.builder()
                .name(createInstituteClassRequest.name())
                .institute(institute)
                .description(createInstituteClassRequest.description())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindInstituteRequest 객체를 기반으로
     * InstituteCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 교육기관 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 InstituteCriteria 객체
     */
    public InstituteClassCriteria toCriteria(
            InstituteClassDto.FindInstituteClassRequest findInstituteClassRequest
    ) {
        return new InstituteClassCriteria(
                findInstituteClassRequest.instituteClassIds(),
                findInstituteClassRequest.name(),
                findInstituteClassRequest.description(),
                findInstituteClassRequest.instituteIds()
        );
    }

    /**
     * InstituteClass 엔티티를 기반으로 응답용 InstituteResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param instituteClass 변환할 Institute 엔티티 (null 가능)
     * @return InstituteClass 엔티티의 데이터를 담은 InstituteClassResponse DTO, instituteClass가 null이면 null 반환
     */
    public InstituteClassDto.InstituteClassResponse toResponseDto(
            InstituteClass instituteClass
    ) {
        return Optional.ofNullable(instituteClass)
                .map(ic -> new InstituteClassDto.InstituteClassResponse(
                        ic.getId(),
                        ic.getName(),
                        ic.getDescription(),
                        ic.getInstitute().getId(),
                        ic.getInstitute().getName(),
                        ic.getCreatedAt(),
                        ic.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 InstituteClass 엔티티 목록을 InstituteClassPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param instituteClassPage Page 형태로 조회된 InstituteClass 엔티티 목록 (null 가능)
     * @return InstituteClass 엔티티 리스트와 페이지 정보를 담은 InstituteClassPageResponse DTO, instituteClassPage가 null이면 null 반환
     */
    public InstituteClassDto.InstituteClassPageResponse toPageResponseDto(Page<InstituteClass> instituteClassPage) {
        return Optional.ofNullable(instituteClassPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new InstituteClassDto.InstituteClassPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}