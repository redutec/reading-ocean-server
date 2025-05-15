package com.redutec.teachingocean.branch.mapper;

import com.redutec.core.entity.Branch;
import com.redutec.teachingocean.branch.dto.BranchDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Component
public class BranchMapper {
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
}