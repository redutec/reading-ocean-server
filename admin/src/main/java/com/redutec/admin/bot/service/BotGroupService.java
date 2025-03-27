package com.redutec.admin.bot.service;

import com.redutec.admin.bot.dto.BotGroupDto;
import com.redutec.core.entity.BotGroup;

public interface BotGroupService {
    /**
     * 관리자 그룹 등록
     * @param createBotGroupRequest 관리자 그룹 등록 정보를 담은 DTO
     * @return 등록된 관리자 그룹 정보
     */
    BotGroupDto.BotGroupResponse create(BotGroupDto.CreateBotGroupRequest createBotGroupRequest);
    /**
     * 조건에 맞는 관리자 그룹 목록 조회
     * @param findBotGroupRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 그룹 목록
     */
    BotGroupDto.BotGroupPageResponse find(BotGroupDto.FindBotGroupRequest findBotGroupRequest);
    /**
     * 특정 관리자 그룹 조회
     * @param groupNo 관리자 그룹 고유번호
     * @return 특정 관리자 그룹 엔티티 객체
     */
    BotGroup findByGroupNo(Long groupNo);
    /**
     * 관리자 그룹 수정
     * @param groupNo 수정할 관리자 그룹의 고유번호
     * @param updateBotGroupRequest 수정할 정보를 담은 DTO
     */
    void update(Long groupNo, BotGroupDto.UpdateBotGroupRequest updateBotGroupRequest);
    /**
     * 관리자 그룹 삭제
     * @param groupNo 삭제할 관리자 그룹의 고유번호
     */
    void delete(Long groupNo);
}