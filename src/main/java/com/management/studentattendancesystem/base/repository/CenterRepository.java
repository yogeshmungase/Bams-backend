package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Long> {
    List<Center> findAllByInstitutionIdAndStatus(String institutionId, String active);

    Center findByIdAndStatus(Long centerId, String active);
}
