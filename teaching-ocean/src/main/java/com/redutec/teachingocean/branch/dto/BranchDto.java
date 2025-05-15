package com.redutec.teachingocean.branch.dto;

import com.redutec.core.meta.BranchStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BranchDto {
        @Schema(description = "지사 응답 객체")
        public record BranchResponse(
                Long branchId,
                Long managerTeacherId,
                String managerTeacherAccountId,
                String managerTeacherName,
                Long managerTeacherInstituteId,
                String managerTeacherInstituteName,
                String region,
                String name,
                BranchStatus status,
                String businessArea,
                String contractFileName,
                LocalDate contractDate,
                LocalDate renewalDate,
                String description,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {}
}