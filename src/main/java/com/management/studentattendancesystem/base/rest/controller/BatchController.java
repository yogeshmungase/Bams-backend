package com.management.studentattendancesystem.base.rest.controller;


import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.BatchDTO;
import com.management.studentattendancesystem.base.service.BatchService;
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
    public ResponseEntity<GenericResponse> createBatch(@RequestBody BatchDTO batchDTO) {
        logger.info("ENTER StudentController:createBatch() with details {}", batchDTO);
        return batchService.createBatch(batchDTO);
    }

    @PutMapping
    public ResponseEntity<BatchDTO> editBatch(@RequestBody BatchDTO batchDTO) {
        logger.info("ENTER StudentController:editBatch() with details {}", batchDTO);
        return batchService.editBatch(batchDTO);
    }

    @GetMapping("/{batchId}/{status}")
    public ResponseEntity<GenericResponse> changeBatchStatus(@PathVariable("batchId") Long batchId, @PathVariable("status") boolean status) {
        logger.info("ENTER StudentController:changeBatchStatus() with details batchId : {} and status :{}", batchId, status);
        return batchService.changeBatchStatus(batchId, status);
    }

    @GetMapping("/{batchId}")
    public ResponseEntity<BatchDTO> getBatchDetailAgainstBatchId(@PathVariable("batchId") Long batchId) {
        logger.info("ENTER StudentController:getBatchDetails() with details batchId : {} ", batchId);
        return batchService.getBatchDetailAgainstBatchId(batchId);
    }

    @GetMapping
    public ResponseEntity<List<BatchDTO>> getAllBatchDetails() {
        logger.info("ENTER StudentController:getAllBatchDetails()");
        return batchService.getAllBatchDetails();
    }


}
