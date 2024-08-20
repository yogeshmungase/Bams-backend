package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.rest.model.request.InstitutionDTO;
import com.management.studentattendancesystem.base.service.InstitutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InstitutionServiceImpl implements InstitutionService {
    @Override
    public ResponseEntity<InstitutionDTO> addInstitution(InstitutionDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<InstitutionDTO> updateInstitution(InstitutionDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<InstitutionDTO> getInstitutionDetails(String institutionId) {
        return null;
    }
}
