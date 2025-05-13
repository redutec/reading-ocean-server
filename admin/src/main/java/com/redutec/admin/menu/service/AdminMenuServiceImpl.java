package com.redutec.admin.menu.service;

import com.redutec.admin.menu.dto.AdminMenuDto;
import com.redutec.admin.menu.mapper.AdminMenuMapper;
import com.redutec.core.entity.AdminMenu;
import com.redutec.core.repository.AdminMenuRepository;
import com.redutec.core.specification.AdminMenuSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AdminMenuServiceImpl implements AdminMenuService {
    private final AdminMenuMapper adminMenuMapper;
    private final AdminMenuRepository adminMenuRepository;

    /**
     * 어드민 메뉴 등록
     * @param createAdminMenuRequest 어드민 메뉴 등록 정보를 담은 DTO
     * @return 등록된 어드민 메뉴 정보
     */
    @Override
    @Transactional
    public AdminMenuDto.AdminMenuResponse create(AdminMenuDto.CreateAdminMenuRequest createAdminMenuRequest) {
        // 부모 메뉴 엔티티 조회
        AdminMenu parentMenu = Optional.ofNullable(createAdminMenuRequest.parentMenuId())
                .map(this::getAdminMenu)
                .orElse(null);
        // 자식 메뉴 엔티티 조회
        List<AdminMenu> childrenMenus = Optional.ofNullable(createAdminMenuRequest.childrenMenuIds())
                .orElse(List.of())
                .stream()
                .map(this::getAdminMenu)
                .toList();
        // 어드민 메뉴 등록 객체 생성 후 등록
        return adminMenuMapper.toResponseDto(
                adminMenuRepository.save(
                        adminMenuMapper.toEntity(
                                createAdminMenuRequest,
                                parentMenu,
                                childrenMenus
                        )
                )
        );
    }

    /**
     * 조건에 맞는 어드민 메뉴 목록 조회
     * @param findAdminMenuRequest 조회 조건을 담은 DTO
     * @return 조회된 어드민 메뉴 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public AdminMenuDto.AdminMenuPageResponse find(AdminMenuDto.FindAdminMenuRequest findAdminMenuRequest) {
        return adminMenuMapper.toPageResponseDto(adminMenuRepository.findAll(
                AdminMenuSpecification.findWith(adminMenuMapper.toCriteria(findAdminMenuRequest)),
                (findAdminMenuRequest.page() != null && findAdminMenuRequest.size() != null)
                        ? PageRequest.of(findAdminMenuRequest.page(), findAdminMenuRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 어드민 메뉴 조회
     * @param adminMenuId 어드민 메뉴 고유번호
     * @return 특정 어드민 메뉴 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public AdminMenuDto.AdminMenuResponse findById(Long adminMenuId) {
        return adminMenuMapper.toResponseDto(getAdminMenu(adminMenuId));
    }

    /**
     * 특정 어드민 메뉴 엔티티 조회
     * @param adminMenuId 어드민 메뉴 고유번호
     * @return 특정 어드민 메뉴 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public AdminMenu getAdminMenu(Long adminMenuId) {
        return adminMenuRepository.findById(adminMenuId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 어드민 메뉴입니다. id = " + adminMenuId));
    }

    /**
     * 어드민 메뉴 수정
     * @param adminMenuId 수정할 어드민 메뉴의 ID
     * @param updateAdminMenuRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long adminMenuId, AdminMenuDto.UpdateAdminMenuRequest updateAdminMenuRequest) {
        // 수정할 어드민 메뉴 엔티티 조회
        AdminMenu adminMenu = getAdminMenu(adminMenuId);
        // 부모 메뉴 엔티티 조회
        AdminMenu parentMenu = Optional.ofNullable(updateAdminMenuRequest.parentMenuId())
                .map(this::getAdminMenu)
                .orElse(null);
        // 자식 메뉴 엔티티 조회
        List<AdminMenu> childrenMenus = Optional.ofNullable(updateAdminMenuRequest.childrenMenuIds())
                .map(childrenMenuIds -> childrenMenuIds.stream()
                        .map(this::getAdminMenu)
                        .toList())
                .orElse(null);
        // UPDATE 도메인 메서드로 변환
        adminMenu.updateAdminMenu(
                updateAdminMenuRequest.name(),
                updateAdminMenuRequest.url(),
                updateAdminMenuRequest.description(),
                updateAdminMenuRequest.available(),
                updateAdminMenuRequest.accessibleRoles(),
                updateAdminMenuRequest.depth(),
                parentMenu,
                childrenMenus
        );
        // 어드민 메뉴 엔티티 UPDATE
        adminMenuRepository.save(adminMenu);
    }

    /**
     * 어드민 메뉴 삭제
     * @param adminMenuId 삭제할 어드민 메뉴의 ID
     */
    @Override
    @Transactional
    public void delete(Long adminMenuId) {
        adminMenuRepository.delete(getAdminMenu(adminMenuId));
    }
}