package com.redutec.admin.branch.mapper;

import com.redutec.admin.branch.dto.BranchDto;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.BranchCriteria;
import com.redutec.core.entity.Branch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BranchMapper {
    private final PasswordEncoder passwordEncoder;
    private final FileUtil fileUtil;

    /**
     * CreateBranchRequest DTO를 기반으로 Branch 엔티티를 생성합니다.
     *
     * @param createBranchRequest 지사 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Branch 엔티티
     */
    public Branch toEntity(
            BranchDto.CreateBranchRequest createBranchRequest
    ) {
        // 계약서 파일이 존재하는 경우 파일을 업로드하고 파일명을 가져오기(파일이 없으면 파일명은 null)
        String contractFileName = Optional.ofNullable(createBranchRequest.contractFile())
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    FileUploadResult result = fileUtil.uploadFile(file, "/branch");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        return Branch.builder()
                .accountId(createBranchRequest.accountId())
                .password(passwordEncoder.encode(createBranchRequest.password()))
                .region(createBranchRequest.region())
                .name(createBranchRequest.name())
                .status(createBranchRequest.status())
                .businessArea(createBranchRequest.businessArea())
                .managerName(createBranchRequest.managerName())
                .managerPhoneNumber(createBranchRequest.managerPhoneNumber())
                .managerEmail(createBranchRequest.managerEmail())
                .contractFileName(contractFileName)
                .contractDate(createBranchRequest.contractDate())
                .renewalDate(createBranchRequest.renewalDate())
                .description(createBranchRequest.description())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindBranchRequest 객체를 기반으로
     * BranchCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 지사 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 BranchCriteria 객체
     */
    public BranchCriteria toCriteria(
            BranchDto.FindBranchRequest findBranchRequest
    ) {
        return new BranchCriteria(
                findBranchRequest.branchIds(),
                findBranchRequest.accountId(),
                findBranchRequest.name(),
                findBranchRequest.statuses(),
                findBranchRequest.managerName()
        );
    }

    /**
     * Branch 엔티티를 기반으로 응답용 BranchResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param branch 변환할 Branch 엔티티 (null 가능)
     * @return Branch 엔티티의 데이터를 담은 BranchResponse DTO, branch가 null이면 null 반환
     */
    public BranchDto.BranchResponse toResponseDto(
            Branch branch
    ) {
        return Optional.ofNullable(branch)
                .map(br -> new BranchDto.BranchResponse(
                        br.getId(),
                        br.getAccountId(),
                        br.getName(),
                        br.getStatus(),
                        br.getBusinessArea(),
                        br.getManagerName(),
                        br.getManagerPhoneNumber(),
                        br.getManagerEmail(),
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