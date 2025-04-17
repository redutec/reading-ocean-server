package com.redutec.core.criteria;

import com.redutec.core.meta.AdministratorRole;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdministratorMenuCriteria {
    private List<Long> administratorMenuIds;
    private String name;
    private String url;
    private Boolean available;
    private List<AdministratorRole> accessibleRoles;
}