package com.redutec.core.repository;

import com.redutec.core.entity.Homeroom;
import com.redutec.core.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface HomeroomRepository extends JpaRepository<Homeroom, Long>, JpaSpecificationExecutor<Homeroom> {
    Optional<Homeroom> findByNameAndInstitute(String homeroomName, Institute institute);
}