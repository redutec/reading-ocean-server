package com.redutec.core.repository;

import com.redutec.core.entity.StudentCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCartRepository extends JpaRepository<StudentCart, Long>, JpaSpecificationExecutor<StudentCart> {
}