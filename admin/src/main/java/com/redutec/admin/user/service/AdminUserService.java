package com.redutec.admin.user.service;


import com.redutec.core.dto.AdminUserDto;

public interface AdminUserService {
    /**
     * 어드민 사용자 등록
     * @param createAdminUserRequest 어드민 사용자 등록 정보를 담은 DTO
     * @return 등록된 어드민 사용자 정보
     */
    AdminUserDto.AdminUserResponse create(AdminUserDto.CreateAdminUserRequest createAdminUserRequest);

    /**
     * 조건에 맞는 어드민 사용자 목록 조회
     * @param findAdminUserRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 어드민 사용자 목록 및 페이징 정보
     */
    AdminUserDto.AdminUserPageResponse find(AdminUserDto.FindAdminUserRequest findAdminUserRequest);

    /**
     * 특정 어드민 사용자 조회
     * @param adminUserId 어드민 사용자 고유번호
     * @return 특정 어드민 사용자 응답 객체
     */
    AdminUserDto.AdminUserResponse findById(Long adminUserId);

    /**
     * 어드민 사용자 수정
     * @param adminUserId 수정할 어드민 사용자의 ID
     * @param updateAdminUserRequest 어드민 사용자 수정 요청 객체
     */
    void update(Long adminUserId, AdminUserDto.UpdateAdminUserRequest updateAdminUserRequest);

    /**
     * 어드민 사용자 삭제
     * @param adminUserId 삭제할 어드민 사용자의 ID
     */
    void delete(Long adminUserId);
}