package com.redutec.core.mapper;

import com.redutec.core.criteria.LearningMaterialCriteria;
import com.redutec.core.dto.LearningMaterialDto;
import com.redutec.core.entity.LearningMaterial;
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
public class LearningMaterialMapper {
    /**
     * CreateLearningMaterialRequest DTO를 기반으로 LearningMaterial 등록 엔티티를 생성합니다.
     *
     * @param createLearningMaterialRequest LearningMaterial 등록에 필요한 데이터를 담은 DTO
     * @return 생성된 LearningMaterial 등록 엔티티
     */
    public LearningMaterial toCreateEntity(
            LearningMaterialDto.CreateLearningMaterialRequest createLearningMaterialRequest,
            List<String> attachmentFileNames
    ) {
        return LearningMaterial.builder()
                .category(createLearningMaterialRequest.category())
                .title(createLearningMaterialRequest.title())
                .content(createLearningMaterialRequest.content())
                .author(createLearningMaterialRequest.author())
                .attachmentFileNames(attachmentFileNames)
                .available(createLearningMaterialRequest.available())
                .build();
    }

    /**
     * UpdateLearningMaterialRequest DTO를 기반으로 LearningMaterial 수정 엔티티를 생성합니다.
     *
     * @param learningMaterial 수정할 LearningMaterial 엔티티
     * @param attachmentFileNames 첨부 파일명 목록
     * @param updateLearningMaterialRequest LearningMaterial 수정에 필요한 데이터를 담은 DTO
     * @return 생성된 LearningMaterial 등록 엔티티
     */
    public LearningMaterial toUpdateEntity(
            LearningMaterial learningMaterial,
            List<String> attachmentFileNames,
            LearningMaterialDto.UpdateLearningMaterialRequest updateLearningMaterialRequest
    ) {
        return LearningMaterial.builder()
                .id(learningMaterial.getId())
                .category(Optional.ofNullable(updateLearningMaterialRequest.category()).orElse(learningMaterial.getCategory()))
                .title(Optional.ofNullable(updateLearningMaterialRequest.title()).orElse(learningMaterial.getTitle()))
                .content(Optional.ofNullable(updateLearningMaterialRequest.content()).orElse(learningMaterial.getContent()))
                .author(Optional.ofNullable(updateLearningMaterialRequest.author()).orElse(learningMaterial.getAuthor()))
                .attachmentFileNames(Optional.ofNullable(attachmentFileNames).orElse(learningMaterial.getAttachmentFileNames()))
                .available(Optional.ofNullable(updateLearningMaterialRequest.available()).orElse(learningMaterial.getAvailable()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindLearningMaterialRequest 객체를 기반으로
     * LearningMaterialCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 이용안내 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 LearningMaterialCriteria 객체
     */
    public LearningMaterialCriteria toCriteria(
            LearningMaterialDto.FindLearningMaterialRequest findLearningMaterialRequest
    ) {
        return new LearningMaterialCriteria(
                findLearningMaterialRequest.learningMaterialIds(),
                findLearningMaterialRequest.categories(),
                findLearningMaterialRequest.title(),
                findLearningMaterialRequest.content(),
                findLearningMaterialRequest.authors(),
                findLearningMaterialRequest.available()
        );
    }

    /**
     * LearningMaterial 엔티티를 기반으로 응답용 LearningMaterialResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param learningMaterial 변환할 LearningMaterial 엔티티 (null 가능)
     * @return LearningMaterial 엔티티의 데이터를 담은 LearningMaterialResponse DTO, learningMaterial가 null이면 null 반환
     */
    public LearningMaterialDto.LearningMaterialResponse toResponseDto(
            LearningMaterial learningMaterial
    ) {
        return Optional.ofNullable(learningMaterial)
                .map(hd -> new LearningMaterialDto.LearningMaterialResponse(
                        hd.getId(),
                        hd.getCategory(),
                        hd.getTitle(),
                        hd.getContent(),
                        hd.getAuthor(),
                        hd.getAttachmentFileNames(),
                        hd.getAvailable(),
                        hd.getCreatedAt(),
                        hd.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 LearningMaterial 엔티티 목록을 LearningMaterialPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param learningMaterialPage Page 형태로 조회된 LearningMaterial 엔티티 목록 (null 가능)
     * @return LearningMaterial 엔티티 리스트와 페이지 정보를 담은 LearningMaterialPageResponse DTO, learningMaterialPage가 null이면 null 반환
     */
    public LearningMaterialDto.LearningMaterialPageResponse toPageResponseDto(
            Page<LearningMaterial> learningMaterialPage
    ) {
        return Optional.ofNullable(learningMaterialPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new LearningMaterialDto.LearningMaterialPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}