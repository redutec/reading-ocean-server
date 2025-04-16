package com.redutec.core.meta.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountStatus {
    WAIT("승인대기"),
    ACTIVE("활성화"),
    INACTIVE("비활성화"),
    DORMANT("휴면"),
    WITHDRAWAL_REQUEST("탈퇴 신청"),
    WITHDRAWAL("탈퇴"),
    NO_AFFILIATION("미소속");

    private final String description;
}