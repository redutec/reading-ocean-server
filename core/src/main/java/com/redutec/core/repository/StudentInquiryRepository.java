package com.redutec.core.repository;

import com.redutec.core.entity.StudentInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentInquiryRepository extends JpaRepository<StudentInquiry, Long>, JpaSpecificationExecutor<StudentInquiry> {
}