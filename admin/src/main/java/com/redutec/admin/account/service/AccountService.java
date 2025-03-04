package com.redutec.admin.account.service;

import com.redutec.admin.account.dto.request.AccountSearchRequest;
import com.redutec.admin.account.dto.response.AccountSearchResponse;

import java.util.List;

public interface AccountService {
    /**
     * 조건에 맞는 계정 목록 조회 API
     *
     * @param accountSearchRequest 계정 조회 조건을 포함하는 DTO
     * @return 조회된 계정 목록과 관련된 추가 정보를 포함하는 응답 객체
     */
    List<AccountSearchResponse> searchAccounts(AccountSearchRequest accountSearchRequest);
}
