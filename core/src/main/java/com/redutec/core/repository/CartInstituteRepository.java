package com.redutec.core.repository;

import com.redutec.core.entity.CartInstitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CartInstituteRepository extends JpaRepository<CartInstitute, Long>, JpaSpecificationExecutor<CartInstitute> {
}