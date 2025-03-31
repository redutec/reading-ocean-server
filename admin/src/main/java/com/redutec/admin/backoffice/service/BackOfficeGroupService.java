package com.redutec.admin.backoffice.service;

import com.redutec.admin.backoffice.dto.BackOfficeGroupDto;
import com.redutec.core.entity.BotGroup;

public interface BackOfficeGroupService {
    /**
     * 관리자 그룹 등록
     * @param createBackOfficeGroupRequest 관리자 그룹 등록 정보를 담은 DTO
     * @return 등록된 관리자 그룹 정보
     */
    BackOfficeGroupDto.BackOfficeGroupResponse create(BackOfficeGroupDto.CreateBackOfficeGroupRequest createBackOfficeGroupRequest);

    /**
     * 조건에 맞는 관리자 그룹 목록 조회
     * @param findBackOfficeGroupRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 그룹 목록
     */
    BackOfficeGroupDto.BackOfficeGroupPageResponse find(BackOfficeGroupDto.FindBackOfficeGroupRequest findBackOfficeGroupRequest);

    /**
     * 특정 관리자 그룹 상세 조회
     * @param groupNo 관리자 그룹 고유번호
     * @return 특정 관리자 그룹 응답 객체
     */
    BackOfficeGroupDto.BackOfficeGroupWithPermissionResponse findByGroupNo(Long groupNo);

    /**
     * 특정 관리자 그룹 엔티티 조회
     * @param groupNo 관리자 그룹 고유번호
     * @return 특정 관리자 그룹 엔티티 객체
     */
    BotGroup getBackOfficeGroup(Long groupNo);

    /**
     * 관리자 그룹 수정
     * @param groupNo 수정할 관리자 그룹의 고유번호
     * @param updateBackOfficeGroupRequest 수정할 정보를 담은 DTO
     */
    void update(Long groupNo, BackOfficeGroupDto.UpdateBackOfficeGroupRequest updateBackOfficeGroupRequest);

    /**
     * 관리자 그룹 삭제
     * @param groupNo 삭제할 관리자 그룹의 고유번호
     */
    void delete(Long groupNo);
}