package com.management.studentattendancesystem.base.repository;

import com.management.studentattendancesystem.base.db.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, String> {
    Institution findByInstitutionIdAndStatus(String institutionId, String active);
}
