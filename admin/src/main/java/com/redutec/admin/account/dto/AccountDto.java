package com.redutec.admin.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountDto {
    @Schema(defaultValue = "계정 목록 조회 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class FindAccount {
    }

    @Schema(defaultValue = "계정 조회 응답 객체")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AccountResponse {
        private Integer accountNo;
        private String accountId;
    }
}
