package com.redutec.core.repository;

import com.redutec.core.entity.StudentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStudentRepository extends JpaRepository<StudentOrder, Long>, JpaSpecificationExecutor<StudentOrder> {
}