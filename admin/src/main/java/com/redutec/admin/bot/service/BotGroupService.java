package com.redutec.admin.bot.service;

import com.redutec.admin.bot.dto.BotGroupDto;

public interface BotGroupService {
    /**
     * 관리자 그룹 등록
     * @param createBotGroupDto 관리자 그룹 등록 정보를 담은 DTO
     * @return 등록된 관리자 그룹 정보
     */
    BotGroupDto.BotGroupResponse create(BotGroupDto.CreateBotGroup createBotGroupDto);
    /**
     * 조건에 맞는 관리자 그룹 목록 조회
     * @param findBotGroupDto 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 그룹 목록
     */
    BotGroupDto.BotGroupPageResponse find(BotGroupDto.FindBotGroup findBotGroupDto);
    /**
     * 관리자 그룹 수정
     * @param userNo 수정할 관리자 그룹의 고유번호
     * @param updateBotGroupDto 수정할 정보를 담은 DTO
     */
    void update(Long userNo, BotGroupDto.UpdateBotGroup updateBotGroupDto);
    /**
     * 관리자 그룹 삭제
     * @param userNo 삭제할 관리자 그룹의 고유번호
     */
    void delete(Long userNo);
}