package com.redutec.core.mapper;

import com.redutec.core.dto.AdminMenuDto;
import com.redutec.core.criteria.AdminMenuCriteria;
import com.redutec.core.entity.AdminMenu;
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
public class AdminMenuMapper {
    /**
     * CreateAdminMenuRequest DTO를 기반으로 AdminMenu 등록 엔티티를 생성합니다.
     *
     * @param createAdminMenuRequest 어드민 메뉴 생성에 필요한 데이터를 담은 DTO
     * @return 등록할 AdminMenu 엔티티
     */
    public AdminMenu createEntity(
            AdminMenuDto.CreateAdminMenuRequest createAdminMenuRequest,
            AdminMenu parentMenu,
            List<AdminMenu> childrenMenus
    ) {
        return AdminMenu.builder()
                .name(createAdminMenuRequest.name())
                .url(createAdminMenuRequest.url())
                .description(createAdminMenuRequest.description())
                .available(createAdminMenuRequest.available())
                .accessibleRoles(createAdminMenuRequest.accessibleRoles())
                .depth(createAdminMenuRequest.depth())
                .parent(parentMenu)
                .children(childrenMenus)
                .build();
    }

    /**
     * UpdateAdminMenuRequest DTO를 기반으로 AdminMenu 엔티티를 수정합니다.
     *
     * @param updateAdminMenuRequest 어드민 메뉴 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(
            AdminMenu adminMenu,
            AdminMenuDto.UpdateAdminMenuRequest updateAdminMenuRequest,
            AdminMenu parentMenu,
            List<AdminMenu> childrenMenus
    ) {
        Optional.ofNullable(updateAdminMenuRequest.name()).ifPresent(adminMenu::setName);
        Optional.ofNullable(updateAdminMenuRequest.url()).ifPresent(adminMenu::setUrl);
        Optional.ofNullable(updateAdminMenuRequest.description()).ifPresent(adminMenu::setDescription);
        Optional.ofNullable(updateAdminMenuRequest.available()).ifPresent(adminMenu::setAvailable);
        Optional.ofNullable(updateAdminMenuRequest.accessibleRoles()).ifPresent(adminMenu::setAccessibleRoles);
        Optional.ofNullable(updateAdminMenuRequest.depth()).ifPresent(adminMenu::setDepth);
        Optional.ofNullable(parentMenu).ifPresent(adminMenu::setParent);
        Optional.ofNullable(childrenMenus).ifPresent(adminMenu::setChildren);
    }
    
    /**
     * 이 메서드는 현재 FindAdminMenuRequest 객체를 기반으로
     * AdminMenuCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 어드민 메뉴 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 AdminMenuCriteria 객체
     */
    public AdminMenuCriteria toCriteria(AdminMenuDto.FindAdminMenuRequest findAdminMenuRequest) {
        return new AdminMenuCriteria(
                findAdminMenuRequest.adminMenuIds(),
                findAdminMenuRequest.name(),
                findAdminMenuRequest.url(),
                findAdminMenuRequest.available(),
                findAdminMenuRequest.accessibleRoles()
        );
    }

    /**
     * AdminMenu 엔티티를 기반으로 응답용 AdminMenuResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param adminMenu 변환할 AdminMenu 엔티티 (null 가능)
     * @return AdminMenu 엔티티의 데이터를 담은 AdminMenuResponse DTO, adminMenu가 null이면 null 반환
     */
    public AdminMenuDto.AdminMenuResponse toResponseDto(AdminMenu adminMenu) {
        return Optional.ofNullable(adminMenu)
                .map(menu -> {
                    AdminMenuDto.AdminMenuResponse parentMenu = Optional.ofNullable(menu.getParent())
                            .map(parent -> new AdminMenuDto.AdminMenuResponse(
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
                    List<AdminMenuDto.AdminMenuResponse> childrenMenus = Optional.ofNullable(menu.getChildren())
                            .orElseGet(List::of)
                            .stream()
                            .map(child -> new AdminMenuDto.AdminMenuResponse(
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
                    return new AdminMenuDto.AdminMenuResponse(
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
     * Page 형식의 AdminMenu 엔티티 목록을 AdminMenuPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param adminMenuPage Page 형태로 조회된 AdminMenu 엔티티 목록 (null 가능)
     * @return AdminMenu 엔티티 리스트와 페이지 정보를 담은 AdminMenuPageResponse DTO, adminMenuPage가 null이면 null 반환
     */
    public AdminMenuDto.AdminMenuPageResponse toPageResponseDto(Page<AdminMenu> adminMenuPage) {
        return Optional.ofNullable(adminMenuPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new AdminMenuDto.AdminMenuPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}