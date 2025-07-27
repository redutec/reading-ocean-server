package com.redutec.core.meta;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthenticationEventType {
    LOGIN_SUCCESS("로그인 성공"),
    LOGIN_FAILURE("로그인 실패"),
    LOGOUT("로그아웃"),
    PASSWORD_CHANGE("비밀번호 변경"),
    PASSWORD_RESET("비밀번호 초기화"),
    USERNAME_RECOVERY("아이디 찾기"),
    TOKEN_REFRESH("토큰 갱신");

    private final String displayName;
}