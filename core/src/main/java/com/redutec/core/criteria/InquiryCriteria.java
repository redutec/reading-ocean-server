package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.InquirerType;
import com.redutec.core.meta.InquiryCategory;
import com.redutec.core.meta.InquiryStatus;

import java.util.List;

public record InquiryCriteria(
        List<Long> inquiryIds,
        List<Domain> domains,
        List<InquirerType> inquirerTypes,
        List<InquiryCategory> categories,
        List<InquiryStatus> statuses,
        String inquirerAccountId,
        String guestEmail,
        String responderAccountId,
        String title,
        String content,
        String response
) {}