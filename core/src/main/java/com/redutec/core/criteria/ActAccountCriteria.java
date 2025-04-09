package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ActAccountCriteria {
    List<Integer> accountNoList;
    String academyName;
    String name;
    String accountId;
    List<Domain> signupDomainCodeList;
}