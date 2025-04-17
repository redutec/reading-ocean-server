package com.redutec.core.criteria;

import com.redutec.core.meta.AdministratorRole;
import com.redutec.core.meta.AuthenticationStatus;

import java.util.List;

public record AdministratorCriteria(
    List<Long> administratorIds,
    String nickname,
    String email,
    List<AdministratorRole> roles,
    List<AuthenticationStatus> authenticationStatuses
) {}