package com.redutec.admin.menu.teachingocean.service;

import com.redutec.core.dto.TeachingOceanMenuDto;

public interface TeachingOceanMenuService {
    /**
     * 티칭오션 메뉴 등록
     * @param createTeachingOceanMenuRequest 티칭오션 메뉴 등록 정보를 담은 DTO
     * @return 등록된 티칭오션 메뉴 정보
     */
    TeachingOceanMenuDto.TeachingOceanMenuResponse create(TeachingOceanMenuDto.CreateTeachingOceanMenuRequest createTeachingOceanMenuRequest);

    /**
     * 조건에 맞는 티칭오션 메뉴 목록 조회
     * @param findTeachingOceanMenuRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 티칭오션 메뉴 목록 및 페이징 정보
     */
    TeachingOceanMenuDto.TeachingOceanMenuPageResponse find(TeachingOceanMenuDto.FindTeachingOceanMenuRequest findTeachingOceanMenuRequest);

    /**
     * 특정 티칭오션 메뉴 조회
     * @param teachingOceanMenuId 티칭오션 메뉴 고유번호
     * @return 특정 티칭오션 메뉴 응답 객체
     */
    TeachingOceanMenuDto.TeachingOceanMenuResponse findById(Long teachingOceanMenuId);

    /**
     * 티칭오션 메뉴 수정
     * @param teachingOceanMenuId 수정할 티칭오션 메뉴의 ID
     * @param updateTeachingOceanMenuRequest 티칭오션 메뉴 수정 요청 객체
     */
    void update(Long teachingOceanMenuId, TeachingOceanMenuDto.UpdateTeachingOceanMenuRequest updateTeachingOceanMenuRequest);

    /**
     * 티칭오션 메뉴 삭제
     * @param teachingOceanMenuId 삭제할 티칭오션 메뉴의 ID
     */
    void delete(Long teachingOceanMenuId);
}