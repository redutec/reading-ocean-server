package com.redutec.core.mapper;

import com.redutec.core.criteria.HeadquartersDocumentCriteria;
import com.redutec.core.dto.HeadquartersDocumentDto;
import com.redutec.core.entity.HeadquartersDocument;
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
public class HeadquartersDocumentMapper {
    /**
     * CreateHeadquartersDocumentRequest DTO를 기반으로 HeadquartersDocument 등록 엔티티를 생성합니다.
     *
     * @param createHeadquartersDocumentRequest HeadquartersDocument 등록에 필요한 데이터를 담은 DTO
     * @return 생성된 HeadquartersDocument 등록 엔티티
     */
    public HeadquartersDocument toCreateEntity(
            HeadquartersDocumentDto.CreateHeadquartersDocumentRequest createHeadquartersDocumentRequest,
            List<String> attachmentFileNames
    ) {
        return HeadquartersDocument.builder()
                .category(createHeadquartersDocumentRequest.category())
                .title(createHeadquartersDocumentRequest.title())
                .content(createHeadquartersDocumentRequest.content())
                .author(createHeadquartersDocumentRequest.author())
                .attachmentFileNames(attachmentFileNames)
                .available(createHeadquartersDocumentRequest.available())
                .build();
    }

    /**
     * UpdateHeadquartersDocumentRequest DTO를 기반으로 HeadquartersDocument 수정 엔티티를 생성합니다.
     *
     * @param headquartersDocument 수정할 HeadquartersDocument 엔티티
     * @param attachmentFileNames 첨부 파일명 목록
     * @param updateHeadquartersDocumentRequest HeadquartersDocument 수정에 필요한 데이터를 담은 DTO
     * @return 생성된 HeadquartersDocument 등록 엔티티
     */
    public HeadquartersDocument toUpdateEntity(
            HeadquartersDocument headquartersDocument,
            List<String> attachmentFileNames,
            HeadquartersDocumentDto.UpdateHeadquartersDocumentRequest updateHeadquartersDocumentRequest
    ) {
        return HeadquartersDocument.builder()
                .id(headquartersDocument.getId())
                .category(Optional.ofNullable(updateHeadquartersDocumentRequest.category()).orElse(headquartersDocument.getCategory()))
                .title(Optional.ofNullable(updateHeadquartersDocumentRequest.title()).orElse(headquartersDocument.getTitle()))
                .content(Optional.ofNullable(updateHeadquartersDocumentRequest.content()).orElse(headquartersDocument.getContent()))
                .author(Optional.ofNullable(updateHeadquartersDocumentRequest.author()).orElse(headquartersDocument.getAuthor()))
                .attachmentFileNames(Optional.ofNullable(attachmentFileNames).orElse(headquartersDocument.getAttachmentFileNames()))
                .available(Optional.ofNullable(updateHeadquartersDocumentRequest.available()).orElse(headquartersDocument.getAvailable()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindHeadquartersDocumentRequest 객체를 기반으로
     * HeadquartersDocumentCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 이용안내 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 HeadquartersDocumentCriteria 객체
     */
    public HeadquartersDocumentCriteria toCriteria(
            HeadquartersDocumentDto.FindHeadquartersDocumentRequest findHeadquartersDocumentRequest
    ) {
        return new HeadquartersDocumentCriteria(
                findHeadquartersDocumentRequest.headquartersDocumentIds(),
                findHeadquartersDocumentRequest.categories(),
                findHeadquartersDocumentRequest.title(),
                findHeadquartersDocumentRequest.content(),
                findHeadquartersDocumentRequest.authors(),
                findHeadquartersDocumentRequest.available()
        );
    }

    /**
     * HeadquartersDocument 엔티티를 기반으로 응답용 HeadquartersDocumentResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param headquartersDocument 변환할 HeadquartersDocument 엔티티 (null 가능)
     * @return HeadquartersDocument 엔티티의 데이터를 담은 HeadquartersDocumentResponse DTO, headquartersDocument가 null이면 null 반환
     */
    public HeadquartersDocumentDto.HeadquartersDocumentResponse toResponseDto(
            HeadquartersDocument headquartersDocument
    ) {
        return Optional.ofNullable(headquartersDocument)
                .map(hd -> new HeadquartersDocumentDto.HeadquartersDocumentResponse(
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
     * Page 형식의 HeadquartersDocument 엔티티 목록을 HeadquartersDocumentPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param headquartersDocumentPage Page 형태로 조회된 HeadquartersDocument 엔티티 목록 (null 가능)
     * @return HeadquartersDocument 엔티티 리스트와 페이지 정보를 담은 HeadquartersDocumentPageResponse DTO, headquartersDocumentPage가 null이면 null 반환
     */
    public HeadquartersDocumentDto.HeadquartersDocumentPageResponse toPageResponseDto(
            Page<HeadquartersDocument> headquartersDocumentPage
    ) {
        return Optional.ofNullable(headquartersDocumentPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new HeadquartersDocumentDto.HeadquartersDocumentPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}