package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.db.model.Institution;
import com.management.studentattendancesystem.base.repository.InstitutionRepository;
import com.management.studentattendancesystem.base.rest.controller.AuthController;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.rest.model.request.InstitutionDTO;
import com.management.studentattendancesystem.base.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstitutionServiceImpl implements InstitutionService {

    private static final Logger logger = LoggerFactory.getLogger(InstitutionServiceImpl.class);

    @Autowired
    private InstitutionRepository institutionRepository;


    @Override
    public ResponseEntity<InstitutionDTO> addInstitution(InstitutionDTO dto) {
        Institution institution = UserMapper.getInstitution(dto);
        institutionRepository.save(institution);
        logger.info("Institution created successfully with details : {}", institution);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InstitutionDTO> updateInstitution(InstitutionDTO dto) {
        Optional<Institution> institutionById = institutionRepository.findById(dto.getInstitutionId());
        if (institutionById.isPresent()) {
            Institution institution = UserMapper.getInstitution(dto);
            institutionRepository.save(institution);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        logger.warn("No data found against institutionId : {}", dto.getInstitutionId());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<InstitutionDTO> getInstitutionDetails(String institutionId) {
        Optional<Institution> institution = institutionRepository.findById(institutionId);
        if (institution.isPresent()) {
            InstitutionDTO institutionDTO = UserMapper.getInstitutionDTO(institution.get());
            return new ResponseEntity<>(institutionDTO, HttpStatus.OK);
        }
        logger.warn("No data found against institutionId : {}", institutionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<InstitutionDTO>> getAllInstitutionDetails() {
        List<Institution> institutions = institutionRepository.findAll();
        if (!CollectionUtils.isEmpty(institutions)) {
            logger.info("Record count of institution in the database : {}", institutions.size());
            List<InstitutionDTO> institutionDTOS = new ArrayList<>();
            for (Institution institution : institutions) {
                institutionDTOS.add(UserMapper.getInstitutionDTO(institution));
            }
            return new ResponseEntity<>(institutionDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
