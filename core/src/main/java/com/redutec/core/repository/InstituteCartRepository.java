package com.redutec.core.repository;

import com.redutec.core.entity.InstituteCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteCartRepository extends JpaRepository<InstituteCart, Long>, JpaSpecificationExecutor<InstituteCart> {
}