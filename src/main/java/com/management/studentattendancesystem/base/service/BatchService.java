package com.management.studentattendancesystem.base.service;

import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.BatchDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BatchService {
    ResponseEntity<GenericResponse> createBatch(BatchDTO batchDTO);

    ResponseEntity<BatchDTO> editBatch(BatchDTO batchDTO);

    ResponseEntity<GenericResponse> changeBatchStatus(Long batchId, boolean status);

    ResponseEntity<BatchDTO> getBatchDetailAgainstBatchId(Long batchId);

    ResponseEntity<List<BatchDTO>> getAllBatchDetails();

}
