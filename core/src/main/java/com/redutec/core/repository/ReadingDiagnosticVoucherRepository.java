package com.redutec.core.repository;

import com.redutec.core.entity.Institute;
import com.redutec.core.entity.ReadingDiagnosticVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReadingDiagnosticVoucherRepository extends JpaRepository<ReadingDiagnosticVoucher, Long>, JpaSpecificationExecutor<ReadingDiagnosticVoucher> {
    Optional<ReadingDiagnosticVoucher> findByIdAndInstitute(Long readingDiagnosticVoucherId, Institute institute);
}