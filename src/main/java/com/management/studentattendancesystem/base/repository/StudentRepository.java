package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "Select * from student where batch_id = :batchId and is_active = true", nativeQuery = true)
    List<Student> findAllByBatchId(@Param("batchId") Long batchId);
}
