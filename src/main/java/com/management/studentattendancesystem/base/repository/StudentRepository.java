package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
