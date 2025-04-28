package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthenticationStatus {
    ACTIVE("활성화", "계정이 활성화된 상태입니다."),
    INACTIVE("비활성화", "계정이 비활성화된 상태입니다."),
    SUSPENDED("정지", "계정의 보안상 이슈로 관리자에 의해 정지된 상태입니다."),
    LOCKED("잠김", "비밀번호 실패로 인해 계정이 잠긴 상태입니다."),
    PASSWORD_RESET("비밀번호 초기화", "사용자의 비밀번호가 초기화된 상태입니다."),
    WITHDRAWN("탈퇴", "사용자가 탈퇴한 상태입니다.");

    private final String displayName;
    private final String description;
}