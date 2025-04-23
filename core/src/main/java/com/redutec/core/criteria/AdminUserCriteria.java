package com.redutec.core.criteria;

import com.redutec.core.meta.AdminUserRole;
import com.redutec.core.meta.AuthenticationStatus;

import java.util.List;

public record AdminUserCriteria(
    List<Long> adminUserIds,
    String email,
    String nickname,
    List<AdminUserRole> roles,
    List<AuthenticationStatus> authenticationStatuses
) {}