package com.redutec.core.mapper;

import com.redutec.core.dto.InstituteDto;
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
     * CreateInstituteRequest DTO를 기반으로 Institute 등록 엔티티를 생성합니다.
     *
     * @param createInstituteRequest 교육기관 등록에 필요한 데이터를 담은 DTO
     * @param branch 교육기관이 소속할 지사 엔티티
     * @return 등록할 Institute 엔티티
     */
    public Institute createEntity(
            InstituteDto.CreateInstituteRequest createInstituteRequest,
            Branch branch
    ) {
        return Institute.builder()
                .name(createInstituteRequest.name())
                .businessRegistrationName(createInstituteRequest.businessRegistrationName())
                .address(createInstituteRequest.address())
                .postalCode(createInstituteRequest.postalCode())
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
     * UpdateInstituteRequest DTO를 기반으로 Institute 엔티티를 수정합니다.
     *
     * @param institute 수정할 Institute 엔티티
     * @param updateInstituteRequest 교육기관 수정에 필요한 데이터를 담은 DTO
     * @param branch 교육기관이 소속할 지사 엔티티
     */
    public void updateEntity(
            Institute institute,
            InstituteDto.UpdateInstituteRequest updateInstituteRequest,
            Branch branch
    ) {
        Optional.ofNullable(updateInstituteRequest.name()).ifPresent(institute::setName);
        Optional.ofNullable(updateInstituteRequest.businessRegistrationName()).ifPresent(institute::setBusinessRegistrationName);
        Optional.ofNullable(updateInstituteRequest.address()).ifPresent(institute::setAddress);
        Optional.ofNullable(updateInstituteRequest.postalCode()).ifPresent(institute::setPostalCode);
        Optional.ofNullable(updateInstituteRequest.phoneNumber()).ifPresent(institute::setPhoneNumber);
        Optional.ofNullable(updateInstituteRequest.url()).ifPresent(institute::setUrl);
        Optional.ofNullable(updateInstituteRequest.naverPlaceUrl()).ifPresent(institute::setNaverPlaceUrl);
        Optional.ofNullable(updateInstituteRequest.type()).ifPresent(institute::setType);
        Optional.ofNullable(updateInstituteRequest.managementType()).ifPresent(institute::setManagementType);
        Optional.ofNullable(updateInstituteRequest.status()).ifPresent(institute::setStatus);
        Optional.ofNullable(updateInstituteRequest.operationStatus()).ifPresent(institute::setOperationStatus);
        Optional.ofNullable(branch).ifPresent(institute::setBranch);
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
     * @param chiefTeacher 원장교사 엔티티
     * @return Institute 엔티티의 데이터를 담은 InstituteResponse DTO, institute가 null이면 null 반환
     */
    public InstituteDto.InstituteResponse toResponseDto(Institute institute, Teacher chiefTeacher) {
        return Optional.ofNullable(institute)
                .map(in -> new InstituteDto.InstituteResponse(
                        in.getId(),
                        in.getName(),
                        in.getBusinessRegistrationName(),
                        in.getAddress(),
                        in.getPostalCode(),
                        in.getPhoneNumber(),
                        in.getUrl(),
                        in.getNaverPlaceUrl(),
                        in.getType(),
                        in.getManagementType(),
                        in.getStatus(),
                        in.getOperationStatus(),
                        Optional.ofNullable(chiefTeacher).map(Teacher::getId).orElse(null),
                        Optional.ofNullable(chiefTeacher).map(Teacher::getName).orElse(null),
                        Optional.ofNullable(institute.getBranch()).map(Branch::getId).orElse(null),
                        Optional.ofNullable(institute.getBranch()).map(Branch::getName).orElse(null),
                        in.getCreatedAt(),
                        in.getUpdatedAt()
                ))
                .orElse(null);
    }
}