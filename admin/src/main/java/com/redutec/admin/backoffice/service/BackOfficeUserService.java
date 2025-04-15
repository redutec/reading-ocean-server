package com.redutec.admin.backoffice.service;

import com.redutec.admin.backoffice.dto.BackOfficeUserDto;
import com.redutec.core.entity.v1.BotUser;

import java.security.NoSuchAlgorithmException;

public interface BackOfficeUserService {
    /**
     * 관리자 계정 등록
     * @param createBackOfficeUserRequest 관리자 계정 등록 정보를 담은 DTO
     * @return 등록된 관리자 계정 정보
     */
    BackOfficeUserDto.BackOfficeUserResponse create(BackOfficeUserDto.CreateBackOfficeUserRequest createBackOfficeUserRequest) throws NoSuchAlgorithmException;

    /**
     * 조건에 맞는 관리자 계정 목록 조회
     * @param findBackOfficeUserRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 계정 목록
     */
    BackOfficeUserDto.BackOfficeUserPageResponse find(BackOfficeUserDto.FindBackOfficeUserRequest findBackOfficeUserRequest);

    /**
     * 특정 관리자 계정 엔티티 조회
     * @param userNo 관리자 계정 고유번호
     * @return 특정 관리자 계정 엔티티 객체
     */
    BotUser getBackOfficeUser(Integer userNo);

    /**
     * 관리자 계정 수정
     * @param userNo 수정할 관리자 계정의 고유번호
     * @param updateBackOfficeUserRequest 수정할 정보를 담은 DTO
     */
    void update(Integer userNo, BackOfficeUserDto.UpdateBackOfficeUserRequest updateBackOfficeUserRequest) throws NoSuchAlgorithmException;

    /**
     * 관리자 계정 삭제
     * @param userNo 삭제할 관리자 계정의 고유번호
     */
    void delete(Integer userNo);
}