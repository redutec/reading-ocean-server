package com.redutec.admin.menu.service;

import com.redutec.core.dto.AdminMenuDto;

public interface AdminMenuService {
    /**
     * 어드민 메뉴 등록
     * @param createAdminMenuRequest 어드민 메뉴 등록 정보를 담은 DTO
     * @return 등록된 어드민 메뉴 정보
     */
    AdminMenuDto.AdminMenuResponse create(AdminMenuDto.CreateAdminMenuRequest createAdminMenuRequest);

    /**
     * 조건에 맞는 어드민 메뉴 목록 조회
     * @param findAdminMenuRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 어드민 메뉴 목록 및 페이징 정보
     */
    AdminMenuDto.AdminMenuPageResponse find(AdminMenuDto.FindAdminMenuRequest findAdminMenuRequest);

    /**
     * 특정 어드민 메뉴 조회
     * @param adminMenuId 어드민 메뉴 고유번호
     * @return 특정 어드민 메뉴 응답 객체
     */
    AdminMenuDto.AdminMenuResponse get(Long adminMenuId);

    /**
     * 어드민 메뉴 수정
     * @param adminMenuId 수정할 어드민 메뉴의 ID
     * @param updateAdminMenuRequest 어드민 메뉴 수정 요청 객체
     */
    void update(Long adminMenuId, AdminMenuDto.UpdateAdminMenuRequest updateAdminMenuRequest);

    /**
     * 어드민 메뉴 삭제
     * @param adminMenuId 삭제할 어드민 메뉴의 ID
     */
    void delete(Long adminMenuId);
}