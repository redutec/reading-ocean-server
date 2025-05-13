package com.redutec.core.repository;

import com.redutec.core.entity.CartStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CartStudentRepository extends JpaRepository<CartStudent, Long>, JpaSpecificationExecutor<CartStudent> {
}