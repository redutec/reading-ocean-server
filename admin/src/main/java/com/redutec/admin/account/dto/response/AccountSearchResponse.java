package com.redutec.admin.account.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(defaultValue = "계정 조회 응답 객체")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSearchResponse {
    private Integer accountNo;
    private String accountId;
}
