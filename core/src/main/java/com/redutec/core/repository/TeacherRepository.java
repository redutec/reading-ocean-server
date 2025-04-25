package com.redutec.core.repository;

import com.redutec.core.entity.Institute;
import com.redutec.core.entity.Teacher;
import com.redutec.core.meta.TeacherRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {
    Optional<Teacher> findByInstituteAndRole(Institute institute, TeacherRole role);
}