package com.redutec.core.repository;

import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Student;
import com.redutec.core.meta.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    List<Student> findByInstitute(Institute institute);
    Optional<Student> findByInstituteAndId(Institute institute, Long studentId);
    Optional<Student> findByAccountId(String accountId);
    Integer countByInstituteAndStatus(Institute institute, StudentStatus studentStatus);
}