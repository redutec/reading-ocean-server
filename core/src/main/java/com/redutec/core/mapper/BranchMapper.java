package com.redutec.core.mapper;

import com.redutec.core.criteria.BranchCriteria;
import com.redutec.core.dto.BranchDto;
import com.redutec.core.entity.Branch;
import com.redutec.core.entity.Teacher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BranchMapper {
    /**
     * CreateBranchRequest DTO를 기반으로 Branch 등록 엔티티를 생성합니다.
     *
     * @param createBranchRequest 지사 등록에 필요한 데이터를 담은 DTO
     * @return 생성된 Branch 등록 엔티티
     */
    public Branch toCreateEntity(
            BranchDto.CreateBranchRequest createBranchRequest,
            Teacher managerTeacher,
            String contractFileName
    ) {
        return Branch.builder()
                .managerTeacher(managerTeacher)
                .region(createBranchRequest.region())
                .name(createBranchRequest.name())
                .status(createBranchRequest.status())
                .businessArea(createBranchRequest.businessArea())
                .contractFileName(contractFileName)
                .contractDate(createBranchRequest.contractDate())
                .renewalDate(createBranchRequest.renewalDate())
                .description(createBranchRequest.description())
                .build();
    }

    /**
     * UpdateBranchRequest DTO를 기반으로 Branch 수정 엔티티를 생성합니다.
     *
     * @param updateBranchRequest 지사 수정에 필요한 데이터를 담은 DTO
     * @return 생성된 Branch 수정 엔티티
     */
    public Branch toUpdateEntity(
            Branch branch,
            BranchDto.UpdateBranchRequest updateBranchRequest,
            Teacher managerTeacher,
            String contractFileName
    ) {
        return Branch.builder()
                .id(branch.getId())
                .managerTeacher(Optional.ofNullable(managerTeacher).orElse(branch.getManagerTeacher()))
                .region(Optional.ofNullable(updateBranchRequest.region()).orElse(branch.getRegion()))
                .name(Optional.ofNullable(updateBranchRequest.name()).orElse(branch.getName()))
                .status(Optional.ofNullable(updateBranchRequest.status()).orElse(branch.getStatus()))
                .businessArea(Optional.ofNullable(updateBranchRequest.businessArea()).orElse(branch.getBusinessArea()))
                .contractFileName(Optional.ofNullable(contractFileName).orElse(branch.getContractFileName()))
                .contractDate(Optional.ofNullable(updateBranchRequest.contractDate()).orElse(branch.getContractDate()))
                .renewalDate(Optional.ofNullable(updateBranchRequest.renewalDate()).orElse(branch.getRenewalDate()))
                .description(Optional.ofNullable(updateBranchRequest.description()).orElse(branch.getDescription()))
                .build();
    }

    /**
     * 이 메서드는 현재 FindBranchRequest 객체를 기반으로
     * BranchCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 지사 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 BranchCriteria 객체
     */
    public BranchCriteria toCriteria(BranchDto.FindBranchRequest findBranchRequest) {
        return new BranchCriteria(
                findBranchRequest.branchIds(),
                findBranchRequest.name(),
                findBranchRequest.statuses(),
                findBranchRequest.managerTeacherName(),
                findBranchRequest.managerTeacherAccountId()
        );
    }

    /**
     * Branch 엔티티를 기반으로 응답용 BranchResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param branch 변환할 Branch 엔티티 (null 가능)
     * @return Branch 엔티티의 데이터를 담은 BranchResponse DTO, branch가 null이면 null 반환
     */
    public BranchDto.BranchResponse toResponseDto(Branch branch) {
        return Optional.ofNullable(branch)
                .map(br -> new BranchDto.BranchResponse(
                        br.getId(),
                        br.getManagerTeacher().getId(),
                        br.getManagerTeacher().getAccountId(),
                        br.getManagerTeacher().getName(),
                        br.getManagerTeacher().getInstitute().getId(),
                        br.getManagerTeacher().getInstitute().getName(),
                        br.getRegion(),
                        br.getName(),
                        br.getStatus(),
                        br.getBusinessArea(),
                        br.getContractFileName(),
                        br.getContractDate(),
                        br.getRenewalDate(),
                        br.getDescription(),
                        br.getCreatedAt(),
                        br.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Branch 엔티티 목록을 BranchPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param branchPage Page 형태로 조회된 Branch 엔티티 목록 (null 가능)
     * @return Branch 엔티티 리스트와 페이지 정보를 담은 BranchPageResponse DTO, branchPage가 null이면 null 반환
     */
    public BranchDto.BranchPageResponse toPageResponseDto(Page<Branch> branchPage) {
        return Optional.ofNullable(branchPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new BranchDto.BranchPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}