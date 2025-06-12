package com.redutec.core.repository;

import com.redutec.core.entity.TeacherInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherInquiryRepository extends JpaRepository<TeacherInquiry, Long>, JpaSpecificationExecutor<TeacherInquiry> {
}