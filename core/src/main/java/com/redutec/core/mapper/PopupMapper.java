package com.redutec.core.mapper;

import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.PopupCriteria;
import com.redutec.core.dto.PopupDto;
import com.redutec.core.entity.Popup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class PopupMapper {
    private final FileUtil fileUtil;

    /**
     * CreatePopupRequest DTO를 기반으로 Popup 등록 엔티티를 생성합니다.
     *
     * @param createPopupRequest 팝업 등록에 필요한 데이터를 담은 DTO
     * @return 등록할 Popup 엔티티
     */
    public Popup createEntity(
            PopupDto.CreatePopupRequest createPopupRequest
    ) {
        return Popup.builder()
                .domain(createPopupRequest.domain())
                .title(createPopupRequest.title())
                .content(createPopupRequest.content())
                .visible(createPopupRequest.visible())
                .visibleStartAt(createPopupRequest.visibleStartAt())
                .visibleEndAt(createPopupRequest.visibleEndAt())
                .build();
    }

    /**
     * UpdatePopupRequest DTO를 기반으로 Popup 엔티티를 수정합니다.
     * @param popup 수정할 Popup 엔티티
     * @param updatePopupRequest 팝업 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(
            Popup popup,
            PopupDto.UpdatePopupRequest updatePopupRequest
    ) {
        Optional.ofNullable(updatePopupRequest.domain()).ifPresent(popup::setDomain);
        Optional.ofNullable(updatePopupRequest.title()).ifPresent(popup::setTitle);
        Optional.ofNullable(updatePopupRequest.content()).ifPresent(popup::setContent);
        Optional.ofNullable(updatePopupRequest.visible()).ifPresent(popup::setVisible);
        Optional.ofNullable(updatePopupRequest.visibleStartAt()).ifPresent(popup::setVisibleStartAt);
        Optional.ofNullable(updatePopupRequest.visibleEndAt()).ifPresent(popup::setVisibleEndAt);
    }
    
    /**
     * 이 메서드는 현재 FindPopupRequest 객체를 기반으로
     * PopupCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 팝업 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 PopupCriteria 객체
     */
    public PopupCriteria toCriteria(PopupDto.FindPopupRequest findPopupRequest) {
        return new PopupCriteria(
                findPopupRequest.popupIds(),
                findPopupRequest.domains(),
                findPopupRequest.title(),
                findPopupRequest.content(),
                findPopupRequest.visible()
        );
    }

    /**
     * Popup 엔티티를 기반으로 응답용 PopupResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param popup 변환할 Popup 엔티티 (null 가능)
     * @return Popup 엔티티의 데이터를 담은 PopupResponse DTO, popup가 null이면 null 반환
     */
    public PopupDto.PopupResponse toResponseDto(Popup popup) {
        return Optional.ofNullable(popup)
                .map(n -> new PopupDto.PopupResponse(
                        n.getId(),
                        n.getDomain(),
                        n.getTitle(),
                        n.getContent(),
                        n.getVisible(),
                        n.getVisibleStartAt(),
                        n.getVisibleEndAt(),
                        n.getCreatedAt(),
                        n.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Popup 엔티티 목록을 PopupPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param popupPage Page 형태로 조회된 Popup 엔티티 목록 (null 가능)
     * @return Popup 엔티티 리스트와 페이지 정보를 담은 PopupPageResponse DTO, popupPage가 null이면 null 반환
     */
    public PopupDto.PopupPageResponse toPageResponseDto(Page<Popup> popupPage) {
        return Optional.ofNullable(popupPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new PopupDto.PopupPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}