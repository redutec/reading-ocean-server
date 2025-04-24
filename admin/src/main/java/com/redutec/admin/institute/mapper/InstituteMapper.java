package com.redutec.admin.institute.mapper;

import com.redutec.admin.institute.dto.InstituteDto;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.InstituteCriteria;
import com.redutec.core.entity.Institute;
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
public class InstituteMapper {
    private final PasswordEncoder passwordEncoder;
    private final FileUtil fileUtil;

    /**
     * CreateInstituteRequest DTO를 기반으로 Institute 엔티티를 생성합니다.
     *
     * @param createInstituteRequest 교육기관 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Institute 엔티티
     */
    public Institute toEntity(
            InstituteDto.CreateInstituteRequest createInstituteRequest
    ) {
        // 계약서 파일이 존재하는 경우 파일을 업로드하고 파일명을 가져오기(파일이 없으면 파일명은 null)
        String contractFileName = Optional.ofNullable(createInstituteRequest.contractFile())
                .filter(file -> !file.isEmpty())
                .map(file -> {
                    FileUploadResult result = fileUtil.uploadFile(file, "/institute");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        return Institute.builder()
                .accountId(createInstituteRequest.accountId())
                .password(passwordEncoder.encode(createInstituteRequest.password()))
                .region(createInstituteRequest.region())
                .name(createInstituteRequest.name())
                .status(createInstituteRequest.status())
                .businessArea(createInstituteRequest.businessArea())
                .managerName(createInstituteRequest.managerName())
                .managerPhoneNumber(createInstituteRequest.managerPhoneNumber())
                .managerEmail(createInstituteRequest.managerEmail())
                .contractFileName(contractFileName)
                .contractDate(createInstituteRequest.contractDate())
                .renewalDate(createInstituteRequest.renewalDate())
                .description(createInstituteRequest.description())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindInstituteRequest 객체를 기반으로
     * InstituteCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 교육기관 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 InstituteCriteria 객체
     */
    public InstituteCriteria toCriteria(
            InstituteDto.FindInstituteRequest findInstituteRequest
    ) {
        return new InstituteCriteria(
                findInstituteRequest.instituteIds(),
                findInstituteRequest.accountId(),
                findInstituteRequest.name(),
                findInstituteRequest.statuses(),
                findInstituteRequest.managerName()
        );
    }

    /**
     * Institute 엔티티를 기반으로 응답용 InstituteResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param institute 변환할 Institute 엔티티 (null 가능)
     * @return Institute 엔티티의 데이터를 담은 InstituteResponse DTO, institute가 null이면 null 반환
     */
    public InstituteDto.InstituteResponse toResponseDto(
            Institute institute
    ) {
        return Optional.ofNullable(institute)
                .map(br -> new InstituteDto.InstituteResponse(
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
     * Page 형식의 Institute 엔티티 목록을 InstitutePageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param institutePage Page 형태로 조회된 Institute 엔티티 목록 (null 가능)
     * @return Institute 엔티티 리스트와 페이지 정보를 담은 InstitutePageResponse DTO, institutePage가 null이면 null 반환
     */
    public InstituteDto.InstitutePageResponse toPageResponseDto(Page<Institute> institutePage) {
        return Optional.ofNullable(institutePage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new InstituteDto.InstitutePageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}