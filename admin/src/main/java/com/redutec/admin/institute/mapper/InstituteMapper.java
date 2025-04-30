package com.redutec.admin.institute.mapper;

import com.redutec.admin.institute.dto.InstituteDto;
import com.redutec.core.criteria.InstituteCriteria;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Component
public class InstituteMapper {
    /**
     * CreateInstituteRequest DTO를 기반으로 Institute 엔티티를 생성합니다.
     *
     * @param createInstituteRequest 교육기관 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Institute 엔티티
     */
    public Institute toEntity(InstituteDto.CreateInstituteRequest createInstituteRequest, Branch branch) {
        return Institute.builder()
                .name(createInstituteRequest.name())
                .businessRegistrationName(createInstituteRequest.businessRegistrationName())
                .address(createInstituteRequest.address())
                .zipCode(createInstituteRequest.zipCode())
                .phoneNumber(createInstituteRequest.phoneNumber())
                .url(createInstituteRequest.url())
                .naverPlaceUrl(createInstituteRequest.naverPlaceUrl())
                .type(createInstituteRequest.type())
                .managementType(createInstituteRequest.managementType())
                .status(createInstituteRequest.status())
                .operationStatus(createInstituteRequest.operationStatus())
                .branch(branch)
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindInstituteRequest 객체를 기반으로
     * InstituteCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 교육기관 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 InstituteCriteria 객체
     */
    public InstituteCriteria toCriteria(InstituteDto.FindInstituteRequest findInstituteRequest) {
        return new InstituteCriteria(
                findInstituteRequest.instituteIds(),
                findInstituteRequest.name(),
                findInstituteRequest.businessRegistrationName(),
                findInstituteRequest.types(),
                findInstituteRequest.managementTypes(),
                findInstituteRequest.statuses(),
                findInstituteRequest.operationStatuses(),
                findInstituteRequest.branchIds()
        );
    }

    /**
     * Institute 엔티티를 기반으로 응답용 InstituteResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param institute 변환할 Institute 엔티티 (null 가능)
     * @return Institute 엔티티의 데이터를 담은 InstituteResponse DTO, institute가 null이면 null 반환
     */
    public InstituteDto.InstituteResponse toResponseDto(Institute institute, Teacher chiefTeacher, Branch branch) {
        return Optional.ofNullable(institute)
                .map(in -> new InstituteDto.InstituteResponse(
                        in.getId(),
                        in.getName(),
                        in.getBusinessRegistrationName(),
                        in.getAddress(),
                        in.getZipCode(),
                        in.getPhoneNumber(),
                        in.getUrl(),
                        in.getNaverPlaceUrl(),
                        in.getType(),
                        in.getManagementType(),
                        in.getStatus(),
                        in.getOperationStatus(),
                        chiefTeacher != null ? chiefTeacher.getId() : null,
                        chiefTeacher != null ? chiefTeacher.getName() : null,
                        branch != null ? branch.getId() : null,
                        branch != null ? branch.getName() : null,
                        in.getCreatedAt(),
                        in.getUpdatedAt()
                ))
                .orElse(null);
    }
}