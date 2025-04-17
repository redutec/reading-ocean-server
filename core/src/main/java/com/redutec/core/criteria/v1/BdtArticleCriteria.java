package com.redutec.core.criteria.v1;

import com.redutec.core.meta.Domain;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BdtArticleCriteria {
    private List<Integer> articleNoList;
    private String articleTitle;
    private String articleContent;
    private String articleContentDetail;
    private String displayYn;
    private List<Domain> domainList;
}