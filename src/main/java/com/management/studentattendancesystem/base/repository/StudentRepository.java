package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.Student;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "Select * from student where batch_id = :batchId and is_active = true", nativeQuery = true)
    List<Student> findAllByBatchId(@Param("batchId") Long batchId);

    List<Student> findAllByBatchId(Long batchId, Pageable paging);

    @Query(value = "SELECT COUNT (student_id) from student where batch_id= :batchId and ", nativeQuery = true)
    Integer getTotalStudentCountAgainstBatch(@Param("batchId") Long batchId);

    @Query(value = "SELECT student_id, " +
            "student_attendance_id, batch_id, first_name, middle_name, last_name, email, mobile, address, is_active "+
            "FROM student "+
            "WHERE batch_id = :batchId AND is_active = :isActive ORDER BY student_id asc", nativeQuery = true)
    List<Tuple> findAllByBatchIdAndIsActive(@Param("batchId") Long batchId, boolean isActive);
}
