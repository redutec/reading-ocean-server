package com.redutec.core.repository;

import com.redutec.core.entity.ReadingDiagnosticTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ReadingDiagnosticTicketRepository extends JpaRepository<ReadingDiagnosticTicket, Long>, JpaSpecificationExecutor<ReadingDiagnosticTicket> {
    @Query("SELECT t.serial FROM ReadingDiagnosticTicket t WHERE t.serial IN :serials")
    List<String> findAllSerialsBySerialIn(@Param("serials") Set<String> serials);
    Optional<ReadingDiagnosticTicket> findBySerial(String serial);
}