package com.redutec.admin.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentAdminUser {
    public static AdminAuthenticationUserDetails getAdminUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (AdminAuthenticationUserDetails) principal;
    }

    public static long getUserNo() {
        return CurrentAdminUser.getAdminUserInfo().getUserNo();
    }

    public static String getUserName() {
        return CurrentAdminUser.getAdminUserInfo().getUsername().trim();
    }

    public static String getUserId() {
        return CurrentAdminUser.getAdminUserInfo().getUserId();
    }

    public static long getGroupNo() {
        return CurrentAdminUser.getAdminUserInfo().getBotGroup().getGroupNo();
    }
}