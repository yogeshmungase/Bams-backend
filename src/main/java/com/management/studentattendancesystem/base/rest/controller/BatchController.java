package com.management.studentattendancesystem.base.rest.controller;


import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.BatchDTO;
import com.management.studentattendancesystem.base.service.BatchService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/sas/batch")
public class BatchController {
    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private BatchService batchService;

    @PostMapping
    public ResponseEntity<GenericResponse> createBatch(@Valid @RequestBody BatchDTO batchDTO) {
        logger.info("ENTER StudentController:createBatch() with details {}", batchDTO);
        return batchService.createBatch(batchDTO);
    }

    @PutMapping("/{batchId}")
    public ResponseEntity<BatchDTO> editBatch(@PathVariable("batchId") Long batchId,@RequestBody BatchDTO batchDTO) {
        logger.info("ENTER StudentController:editBatch() with details {}", batchDTO);
        return batchService.editBatch(batchId,batchDTO);
    }

    @DeleteMapping("/{batchId}")
    public ResponseEntity<GenericResponse> softDeleteBatch(@PathVariable("batchId") Long batchId) {
        logger.info("ENTER StudentController:softDeleteBatch() with details batchId : {}",batchId );
        return batchService.softDeleteBatch(batchId);
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<BatchDTO> getBatchDetailAgainstBatchId(@PathVariable("batchId") Long batchId) {
        logger.info("ENTER StudentController:getBatchDetails() with details batchId : {} ", batchId);
        return batchService.getBatchDetailAgainstBatchId(batchId);
    }

    @GetMapping("/inst/{institutionId}")
    public ResponseEntity<List<BatchDTO>> getAllBatchDetails(@PathVariable("institutionId") String institutionId) {
        logger.info("ENTER StudentController:getAllBatchDetails()");
        return batchService.getAllBatchDetails(institutionId);
    }

    @GetMapping("/inst/{center}/{institutionId}")
    public ResponseEntity<List<BatchDTO>> getAllBatchDetails(@PathVariable("center")String center,@PathVariable("institutionId") String institutionId) {
        logger.info("ENTER StudentController:getAllBatchDetails()");
        return batchService.getAllBatchDetailsAgainstCenter(center,institutionId);
    }


}
