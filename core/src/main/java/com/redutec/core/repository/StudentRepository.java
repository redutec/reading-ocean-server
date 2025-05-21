package com.redutec.core.repository;

import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Optional<Student> findByInstituteAndId(Institute institute, Long studentId);
}