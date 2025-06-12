package com.redutec.core.criteria;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.InquiryCategory;
import com.redutec.core.meta.InquiryStatus;

import java.util.List;

public record TeacherInquiryCriteria(
        List<Long> teacherInquiryIds,
        List<Domain> domains,
        List<InquiryCategory> categories,
        List<InquiryStatus> statuses,
        String teacherAccountId,
        String responderAccountId,
        String title,
        String content,
        String response
) {}