package com.redutec.core.mapper;

import com.redutec.core.criteria.TeachingOceanMenuCriteria;
import com.redutec.core.dto.TeachingOceanMenuDto;
import com.redutec.core.entity.TeachingOceanMenu;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class TeachingOceanMenuMapper {
    /**
     * CreateTeachingOceanMenuRequest DTO를 기반으로 TeachingOceanMenu 엔티티를 생성합니다.
     *
     * @param createTeachingOceanMenuRequest 티칭오션 메뉴 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 TeachingOceanMenu 엔티티
     */
    public TeachingOceanMenu createEntity(
            TeachingOceanMenuDto.CreateTeachingOceanMenuRequest createTeachingOceanMenuRequest,
            TeachingOceanMenu parentMenu,
            List<TeachingOceanMenu> childrenMenus
    ) {
        return TeachingOceanMenu.builder()
                .name(StringUtils.stripToNull(createTeachingOceanMenuRequest.name()))
                .url(StringUtils.stripToNull(createTeachingOceanMenuRequest.url()))
                .description(StringUtils.stripToNull(createTeachingOceanMenuRequest.description()))
                .available(createTeachingOceanMenuRequest.available())
                .accessibleRoles(createTeachingOceanMenuRequest.accessibleRoles())
                .depth(createTeachingOceanMenuRequest.depth())
                .parent(parentMenu)
                .children(childrenMenus)
                .build();
    }

    /**
     * UpdateTeachingOceanMenuRequest DTO를 기반으로 TeachingOceanMenu 엔티티를 수정합니다.
     *
     * @param updateTeachingOceanMenuRequest 어드민 메뉴 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(
            TeachingOceanMenu teachingOceanMenu,
            TeachingOceanMenuDto.UpdateTeachingOceanMenuRequest updateTeachingOceanMenuRequest,
            TeachingOceanMenu parentMenu,
            List<TeachingOceanMenu> childrenMenus
    ) {
        Optional.ofNullable(StringUtils.stripToNull(updateTeachingOceanMenuRequest.name()))
                .ifPresent(teachingOceanMenu::setName);
        Optional.ofNullable(StringUtils.stripToNull(updateTeachingOceanMenuRequest.url()))
                .ifPresent(teachingOceanMenu::setUrl);
        Optional.ofNullable(StringUtils.stripToNull(updateTeachingOceanMenuRequest.description()))
                .ifPresent(teachingOceanMenu::setDescription);
        Optional.ofNullable(updateTeachingOceanMenuRequest.available())
                .ifPresent(teachingOceanMenu::setAvailable);
        Optional.ofNullable(updateTeachingOceanMenuRequest.accessibleRoles())
                .ifPresent(teachingOceanMenu::setAccessibleRoles);
        Optional.ofNullable(updateTeachingOceanMenuRequest.depth())
                .ifPresent(teachingOceanMenu::setDepth);
        Optional.ofNullable(parentMenu)
                .ifPresent(teachingOceanMenu::setParent);
        Optional.ofNullable(childrenMenus)
                .ifPresent(teachingOceanMenu::setChildren);
    }
    
    /**
     * 이 메서드는 현재 FindTeachingOceanMenuRequest 객체를 기반으로
     * TeachingOceanMenuCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 티칭오션 메뉴 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 TeachingOceanMenuCriteria 객체
     */
    public TeachingOceanMenuCriteria toCriteria(TeachingOceanMenuDto.FindTeachingOceanMenuRequest findTeachingOceanMenuRequest) {
        return new TeachingOceanMenuCriteria(
                findTeachingOceanMenuRequest.teachingOceanMenuIds(),
                StringUtils.stripToNull(findTeachingOceanMenuRequest.name()),
                StringUtils.stripToNull(findTeachingOceanMenuRequest.url()),
                findTeachingOceanMenuRequest.available(),
                findTeachingOceanMenuRequest.accessibleRoles()
        );
    }

    /**
     * TeachingOceanMenu 엔티티를 기반으로 응답용 TeachingOceanMenuResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param teachingOceanMenu 변환할 TeachingOceanMenu 엔티티 (null 가능)
     * @return TeachingOceanMenu 엔티티의 데이터를 담은 TeachingOceanMenuResponse DTO, teachingOceanMenu가 null이면 null 반환
     */
    public TeachingOceanMenuDto.TeachingOceanMenuResponse toResponseDto(TeachingOceanMenu teachingOceanMenu) {
        return Optional.ofNullable(teachingOceanMenu)
                .map(menu -> {
                    TeachingOceanMenuDto.TeachingOceanMenuResponse parentMenu = Optional.ofNullable(menu.getParent())
                            .map(parent -> new TeachingOceanMenuDto.TeachingOceanMenuResponse(
                                    parent.getId(),
                                    parent.getName(),
                                    parent.getUrl(),
                                    parent.getDescription(),
                                    parent.getAvailable(),
                                    parent.getAccessibleRoles(),
                                    parent.getDepth(),
                                    null,
                                    List.of(),
                                    parent.getCreatedAt(),
                                    parent.getUpdatedAt()
                            ))
                            .orElse(null);
                    List<TeachingOceanMenuDto.TeachingOceanMenuResponse> childrenMenus = Optional.ofNullable(menu.getChildren())
                            .orElseGet(List::of)
                            .stream()
                            .map(child -> new TeachingOceanMenuDto.TeachingOceanMenuResponse(
                                    child.getId(),
                                    child.getName(),
                                    child.getUrl(),
                                    child.getDescription(),
                                    child.getAvailable(),
                                    child.getAccessibleRoles(),
                                    child.getDepth(),
                                    null,
                                    List.of(),
                                    child.getCreatedAt(),
                                    child.getUpdatedAt()
                            ))
                            .toList();
                    return new TeachingOceanMenuDto.TeachingOceanMenuResponse(
                            menu.getId(),
                            menu.getName(),
                            menu.getUrl(),
                            menu.getDescription(),
                            menu.getAvailable(),
                            menu.getAccessibleRoles(),
                            menu.getDepth(),
                            parentMenu,
                            childrenMenus,
                            menu.getCreatedAt(),
                            menu.getUpdatedAt()
                    );
                })
                .orElse(null);
    }

    /**
     * Page 형식의 TeachingOceanMenu 엔티티 목록을 TeachingOceanMenuPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param teachingOceanMenuPage Page 형태로 조회된 TeachingOceanMenu 엔티티 목록 (null 가능)
     * @return TeachingOceanMenu 엔티티 리스트와 페이지 정보를 담은 TeachingOceanMenuPageResponse DTO, teachingOceanMenuPage가 null이면 null 반환
     */
    public TeachingOceanMenuDto.TeachingOceanMenuPageResponse toPageResponseDto(Page<TeachingOceanMenu> teachingOceanMenuPage) {
        return Optional.ofNullable(teachingOceanMenuPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new TeachingOceanMenuDto.TeachingOceanMenuPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}