package com.management.studentattendancesystem.base.service;

import com.management.studentattendancesystem.base.db.model.Institution;
import com.management.studentattendancesystem.base.rest.model.request.InstitutionDTO;
import org.springframework.http.ResponseEntity;

public interface InstitutionService {

    public ResponseEntity<InstitutionDTO> addInstitution(InstitutionDTO  dto);

    ResponseEntity<InstitutionDTO> updateInstitution(InstitutionDTO dto);

    ResponseEntity<InstitutionDTO> getInstitutionDetails(String institutionId);
}
