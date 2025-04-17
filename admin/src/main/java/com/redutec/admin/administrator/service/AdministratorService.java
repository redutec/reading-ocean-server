package com.redutec.admin.administrator.service;


import com.redutec.admin.administrator.dto.AdministratorDto;
import com.redutec.core.entity.Administrator;

public interface AdministratorService {
    /**
     * 시스템 관리자 등록
     * @param createAdministratorRequest 시스템 관리자 등록 정보를 담은 DTO
     * @return 등록된 시스템 관리자 정보
     */
    AdministratorDto.AdministratorResponse create(AdministratorDto.CreateAdministratorRequest createAdministratorRequest);

    /**
     * 조건에 맞는 시스템 관리자 목록 조회
     * @param findAdministratorRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 시스템 관리자 목록 및 페이징 정보
     */
    AdministratorDto.AdministratorPageResponse find(AdministratorDto.FindAdministratorRequest findAdministratorRequest);

    /**
     * 특정 시스템 관리자 조회
     * @param administratorId 시스템 관리자 고유번호
     * @return 특정 시스템 관리자 응답 객체
     */
    AdministratorDto.AdministratorResponse findById(Long administratorId);

    /**
     * 특정 시스템 관리자 엔티티 조회
     * @param nickname 시스템 관리자 닉네임
     * @return 특정 시스템 관리자 엔티티 객체
     */
    Administrator findByNickname(String nickname);

    /**
     * 특정 시스템 관리자 엔티티 조회
     * @param administratorId 시스템 관리자 고유번호
     * @return 특정 시스템 관리자 엔티티 객체
     */
    Administrator getAdministrator(Long administratorId);

    /**
     * 시스템 관리자 수정
     * @param administratorId 수정할 시스템 관리자의 ID
     * @param updateAdministratorRequest 시스템 관리자 수정 요청 객체
     */
    void update(Long administratorId, AdministratorDto.UpdateAdministratorRequest updateAdministratorRequest);

    /**
     * 시스템 관리자 삭제
     * @param administratorId 삭제할 시스템 관리자의 ID
     */
    void delete(Long administratorId);
}