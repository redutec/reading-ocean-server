package com.redutec.admin.bot.service;

import com.redutec.admin.bot.dto.BotUserDto;

public interface BotUserService {
    /**
     * 관리자 계정 등록
     * @param createBotUserDto 관리자 계정 등록 정보를 담은 DTO
     * @return 등록된 관리자 계정 정보
     */
    BotUserDto.BotUserResponse create(BotUserDto.CreateBotUser createBotUserDto);
    /**
     * 조건에 맞는 관리자 계정 목록 조회
     * @param findBotUserDto 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 계정 목록
     */
    BotUserDto.BotUserPageResponse find(BotUserDto.FindBotUser findBotUserDto);
    /**
     * 관리자 계정 수정
     * @param userNo 수정할 관리자 계정의 고유번호
     * @param updateBotUserDto 수정할 정보를 담은 DTO
     */
    void update(Long userNo, BotUserDto.UpdateBotUser updateBotUserDto);
    /**
     * 관리자 계정 삭제
     * @param userNo 삭제할 관리자 계정의 고유번호
     */
    void delete(Long userNo);
}
