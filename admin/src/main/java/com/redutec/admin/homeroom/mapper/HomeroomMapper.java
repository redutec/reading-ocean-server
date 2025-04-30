package com.redutec.admin.homeroom.mapper;

import com.redutec.admin.homeroom.dto.HomeroomDto;
import com.redutec.core.criteria.HomeroomCriteria;
import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Institute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class HomeroomMapper {
    /**
     * CreateHomeroomRequest DTO를 기반으로 Homeroom 엔티티를 생성합니다.
     *
     * @param createHomeroomRequest 교육기관 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Institute 엔티티
     */
    public Homeroom toEntity(HomeroomDto.CreateHomeroomRequest createHomeroomRequest, Institute institute) {
        return Homeroom.builder()
                .name(createHomeroomRequest.name())
                .institute(institute)
                .description(createHomeroomRequest.description())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindInstituteRequest 객체를 기반으로
     * InstituteCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 교육기관 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 InstituteCriteria 객체
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
     * Homeroom 엔티티를 기반으로 응답용 InstituteResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param homeroom 변환할 Institute 엔티티 (null 가능)
     * @return Homeroom 엔티티의 데이터를 담은 HomeroomResponse DTO, homeroom가 null이면 null 반환
     */
    public HomeroomDto.HomeroomResponse toResponseDto(Homeroom homeroom) {
        return Optional.ofNullable(homeroom)
                .map(ic -> new HomeroomDto.HomeroomResponse(
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