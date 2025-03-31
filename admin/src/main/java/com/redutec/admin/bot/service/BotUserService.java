package com.redutec.admin.bot.service;

import com.redutec.admin.bot.dto.BotUserDto;
import com.redutec.core.entity.BotUser;

import java.security.NoSuchAlgorithmException;

public interface BotUserService {
    /**
     * 관리자 계정 등록
     * @param createBotUserRequest 관리자 계정 등록 정보를 담은 DTO
     * @return 등록된 관리자 계정 정보
     */
    BotUserDto.BotUserResponse create(BotUserDto.CreateBotUserRequest createBotUserRequest) throws NoSuchAlgorithmException;
    /**
     * 조건에 맞는 관리자 계정 목록 조회
     * @param findBotUserRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 계정 목록
     */
    BotUserDto.BotUserPageResponse find(BotUserDto.FindBotUserRequest findBotUserRequest);
    /**
     * 특정 관리자 계정 엔티티 조회
     * @param userNo 관리자 계정 고유번호
     * @return 특정 관리자 계정 엔티티 객체
     */
    BotUser getBotUser(Integer userNo);
    /**
     * 관리자 계정 수정
     * @param userNo 수정할 관리자 계정의 고유번호
     * @param updateBotUserRequest 수정할 정보를 담은 DTO
     */
    void update(Integer userNo, BotUserDto.UpdateBotUserRequest updateBotUserRequest) throws NoSuchAlgorithmException;
    /**
     * 관리자 계정 삭제
     * @param userNo 삭제할 관리자 계정의 고유번호
     */
    void delete(Integer userNo);
}