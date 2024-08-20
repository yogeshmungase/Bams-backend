package com.management.studentattendancesystem.base.rest.controller;


import com.management.studentattendancesystem.base.rest.model.request.InstitutionDTO;
import com.management.studentattendancesystem.base.service.InstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/sas/institution")
public class InstitutionController {

    private static final Logger logger = LoggerFactory.getLogger(InstitutionController.class);

    @Autowired
    private InstitutionService institutionService;


    @PostMapping
    public ResponseEntity<InstitutionDTO> addInstitution(@RequestBody InstitutionDTO dto) {
        logger.info("Inside addInstitution() with details : {}", dto);
        return institutionService.addInstitution(dto);
    }

    @PutMapping
    public ResponseEntity<InstitutionDTO> updateInstitution(@RequestBody InstitutionDTO dto) {
        logger.info("Inside updateInstitution() with details : {}", dto);
        return institutionService.updateInstitution(dto);
    }

    @GetMapping("/{institutionId}")
    public ResponseEntity<InstitutionDTO> findInstitutionAgainstId(@PathVariable("institutionId") String institutionId) {
        logger.info("Inside findInstitutionAgainstId() with details : {}", institutionId);
        return institutionService.getInstitutionDetails(institutionId);
    }

    @GetMapping
    public ResponseEntity<List<InstitutionDTO>> findAllInstitution() {
        logger.info("Inside findAllInstitution()");
        return institutionService.getAllInstitutionDetails();
    }


}
