package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthenticationStatus {
    ACTIVE("Active", "계정이 활성화된 상태입니다."),
    INACTIVE("Inactive", "계정이 비활성화된 상태입니다."),
    SUSPENDED("Suspended", "계정의 보안상 이슈로 관리자에 의해 정지된 상태입니다."),
    LOCKED("Locked", "비밀번호 실패로 인해 계정이 잠긴 상태입니다."),
    PASSWORD_RESET("Password Reset", "사용자의 비밀번호가 초기화된 상태입니다."),
    WITHDRAWN("Withdrawn", "사용자가 탈퇴한 상태입니다."),
    DELETED("Deleted", "계정이 삭제된 상태입니다.");

    private final String name;
    private final String description;
}