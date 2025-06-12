package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.InquiryCategory;
import com.redutec.core.meta.InquiryStatus;

import java.util.List;

public record StudentInquiryCriteria(
        List<Long> studentInquiryIds,
        List<Domain> domains,
        List<InquiryCategory> categories,
        List<InquiryStatus> statuses,
        String studentAccountId,
        String responderAccountId,
        String title,
        String content,
        String response
) {}