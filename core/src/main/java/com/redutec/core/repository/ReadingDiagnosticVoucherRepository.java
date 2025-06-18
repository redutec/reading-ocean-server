package com.redutec.core.repository;

import com.redutec.core.entity.ReadingDiagnosticVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ReadingDiagnosticVoucherRepository extends JpaRepository<ReadingDiagnosticVoucher, Long>, JpaSpecificationExecutor<ReadingDiagnosticVoucher> {
    boolean existsByCode(String code);
    @Query("SELECT v.code FROM ReadingDiagnosticVoucher v WHERE v.code IN :codes")
    List<String> findAllCodesByCodeIn(@Param("codes") Set<String> codes);
}