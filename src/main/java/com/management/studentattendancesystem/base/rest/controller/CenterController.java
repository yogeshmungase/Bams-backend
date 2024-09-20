package com.management.studentattendancesystem.base.rest.controller;


import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.CenterDTO;
import com.management.studentattendancesystem.base.service.CenterService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/sas/centers")
public class CenterController {

    private static final Logger logger = LoggerFactory.getLogger(CenterController.class);

    @Autowired
    private CenterService centerService;

    @PostMapping
    public ResponseEntity<GenericResponse> createCenter(@Valid @RequestBody CenterDTO centerDTO) {
        logger.info("ENTER CenterController:createCenter() with details {}", centerDTO);
        return centerService.createCenter(centerDTO);
    }

    @GetMapping("/{institutionId}")
    public ResponseEntity<List<CenterDTO>> getCenters(@PathVariable("institutionId") String institutionId) {
        logger.info("ENTER CenterController:getCenters() with details {}", institutionId);
        return centerService.getCenters(institutionId);
    }

    @DeleteMapping("/{centerId}")
    public ResponseEntity<GenericResponse> deleteCenter(@PathVariable("centerId") Long centerId) {
        logger.info("ENTER CenterController:deleteCenter() with details {}", centerId);
        return centerService.deleteCenter(centerId);
    }
}
