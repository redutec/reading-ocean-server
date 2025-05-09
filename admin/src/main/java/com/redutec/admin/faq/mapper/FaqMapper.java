package com.redutec.admin.faq.mapper;

import com.redutec.admin.faq.dto.FaqDto;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.FaqCriteria;
import com.redutec.core.entity.Faq;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class FaqMapper {
    private final FileUtil fileUtil;

    /**
     * CreateFaqRequest DTO를 기반으로 Faq 엔티티를 생성합니다.
     *
     * @param createFaqRequest 이용안내 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Faq 엔티티
     */
    public Faq toEntity(FaqDto.CreateFaqRequest createFaqRequest) {
        return Faq.builder()
                .domain(createFaqRequest.domain())
                .title(createFaqRequest.title())
                .content(createFaqRequest.content())
                .visible(createFaqRequest.visible())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindFaqRequest 객체를 기반으로
     * FaqCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 이용안내 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 FaqCriteria 객체
     */
    public FaqCriteria toCriteria(FaqDto.FindFaqRequest findFaqRequest) {
        return new FaqCriteria(
                findFaqRequest.faqIds(),
                findFaqRequest.domains(),
                findFaqRequest.title(),
                findFaqRequest.content(),
                findFaqRequest.visible()
        );
    }

    /**
     * Faq 엔티티를 기반으로 응답용 FaqResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param faq 변환할 Faq 엔티티 (null 가능)
     * @return Faq 엔티티의 데이터를 담은 FaqResponse DTO, faq가 null이면 null 반환
     */
    public FaqDto.FaqResponse toResponseDto(Faq faq) {
        return Optional.ofNullable(faq)
                .map(f -> new FaqDto.FaqResponse(
                        f.getId(),
                        f.getDomain(),
                        f.getTitle(),
                        f.getContent(),
                        f.getVisible(),
                        f.getCreatedAt(),
                        f.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Faq 엔티티 목록을 FaqPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param faqPage Page 형태로 조회된 Faq 엔티티 목록 (null 가능)
     * @return Faq 엔티티 리스트와 페이지 정보를 담은 FaqPageResponse DTO, faqPage가 null이면 null 반환
     */
    public FaqDto.FaqPageResponse toPageResponseDto(Page<Faq> faqPage) {
        return Optional.ofNullable(faqPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new FaqDto.FaqPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}