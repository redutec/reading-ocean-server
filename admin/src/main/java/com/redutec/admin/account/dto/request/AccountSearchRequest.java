package com.redutec.admin.account.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(defaultValue = "계정 목록 조회 요청 객체")
@Builder
@Getter
@AllArgsConstructor
public class AccountSearchRequest {
}
