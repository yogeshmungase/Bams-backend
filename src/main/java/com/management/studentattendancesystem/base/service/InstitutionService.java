package com.management.studentattendancesystem.base.service;

import com.management.studentattendancesystem.base.db.model.Institution;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.InstitutionDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InstitutionService {

    public ResponseEntity<InstitutionDTO> addInstitution(InstitutionDTO  dto);

    ResponseEntity<InstitutionDTO> updateInstitution(InstitutionDTO dto);

    ResponseEntity<InstitutionDTO> getInstitutionDetails(String institutionId);

    ResponseEntity<List<InstitutionDTO>> getAllInstitutionDetails();

    GenericResponse validateInstitution(String institutionId);
}
