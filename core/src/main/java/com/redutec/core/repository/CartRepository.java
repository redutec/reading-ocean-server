package com.redutec.core.repository;

import com.redutec.core.entity.Cart;
import com.redutec.core.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    Optional<Cart> findByInstitute(Institute institute);
}