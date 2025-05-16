package com.redutec.core.criteria;

import com.redutec.core.meta.AdminUserRole;
import com.redutec.core.meta.AuthenticationStatus;

import java.util.List;

public record AdminUserCriteria(
    List<Long> adminUserIds,
    String accountId,
    String nickname,
    String email,
    List<AdminUserRole> roles,
    List<AuthenticationStatus> authenticationStatuses
) {}