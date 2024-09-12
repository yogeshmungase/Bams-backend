package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.db.model.Batch;
import com.management.studentattendancesystem.base.repository.BatchRepository;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.BatchDTO;
import com.management.studentattendancesystem.base.service.BatchService;
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
public class BatchServiceImpl implements BatchService {

    private static Logger logger = LoggerFactory.getLogger(UserMapper.class);

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public ResponseEntity<GenericResponse> createBatch(BatchDTO batchDTO) {

        Batch batchByName = batchRepository.findTop1ByBatchNameAndEnabledAndInstitutionId(batchDTO.getBatchName(), Boolean.TRUE.booleanValue(),batchDTO.getInstitutionId());
        GenericResponse genericResponse = new GenericResponse();
        if (null != batchByName) {
            genericResponse.setStatus("FAILED");
            genericResponse.setMessage("Batch already exist with name " + batchByName.getBatchName());
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        }
        Batch batch = UserMapper.mapBatchDetails(batchDTO);
        batchRepository.save(batch);
        batchDTO.setId(batch.getId());
        batchDTO.setEnabled(batch.isEnabled());
        genericResponse.setStatus("SUCCESS");
        genericResponse.setMessage("Batch created successfully");
        logger.info("Batch created successfully with details : {}", batch);
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BatchDTO> editBatch(Long batchId, BatchDTO batchDTO) {

        Optional<Batch> batchById = batchRepository.findById(batchId);
        if (batchById.isPresent()) {
            Batch batch = batchById.get();
            batch.setBatchName(batch.getBatchName());
            batch.setStartDate(batchDTO.getStartDate());
            batch.setEndDate(batchDTO.getEndDate());
            batch.setStatus(batchDTO.getStatus());
            batchRepository.save(batch);
            logger.info("Batch details edited successfully for batch name : {}", batchDTO.getBatchName());
            return new ResponseEntity<>(batchDTO, HttpStatus.OK);

        }
        logger.error("No batch found against name : {}", batchDTO.getBatchName());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<GenericResponse> changeBatchStatus(Long batchId, boolean status) {
        Optional<Batch> batchById = batchRepository.findById(batchId);
        String batchStatus = status ? "Enabled" : "Disabled";
        GenericResponse genericResponse = new GenericResponse();
        if (batchById.isPresent()) {

            Batch batch = batchById.get();
            if ((batch.isEnabled() && status) || (!batch.isEnabled() && !status)) {
                logger.error("Batch status is already {}", batchStatus);
                genericResponse.setStatus("FAILED");
                genericResponse.setMessage("Can't change batch status as its already " + batchStatus);
                return new ResponseEntity<>(genericResponse, HttpStatus.OK);
            }
            genericResponse.setStatus("FAILED");
            genericResponse.setMessage("Batch status changed to " + batchStatus);
            batch.setEnabled(status);
            batchRepository.save(batch);
            logger.info("Batch status : {} saves successfully against batchId : {}", batchStatus, batchById);

            return new ResponseEntity<>(genericResponse, HttpStatus.OK);

        } else {
            logger.error("No batch found against batchId : {}", batchId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<GenericResponse> softDeleteBatch(Long batchId) {
        Optional<Batch> batchById = batchRepository.findById(batchId);

        GenericResponse genericResponse = new GenericResponse();
        if (batchById.isPresent()) {
            Batch batch = batchById.get();
            batch.setEnabled(Boolean.FALSE.booleanValue());
            batchRepository.save(batch);
            genericResponse.setMessage("Batch deleted successfully !!");
            genericResponse.setStatus("SUCCESS");
            logger.info("Batch  : {} soft deleted successfully {}", batch.getBatchName());
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);

        } else {
            logger.error("No batch found against batchId : {}", batchId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<BatchDTO> getBatchDetailAgainstBatchId(Long batchId) {
        Optional<Batch> batchById = batchRepository.findById(batchId);
        if (batchById.isPresent()) {
            Batch batch = batchById.get();
            BatchDTO batchDTO = new BatchDTO();
            batchDTO.setBatchName(batch.getBatchName());
            batchDTO.setStartDate(batch.getStartDate());
            batchDTO.setEndDate(batch.getEndDate());
            batchDTO.setInstitutionId(batch.getInstitutionId());
            batchDTO.setEnabled(batch.isEnabled());
            return new ResponseEntity<>(batchDTO, HttpStatus.OK);

        }
        logger.error("No batch found against name : {}", batchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<BatchDTO>> getAllBatchDetails(String institutionId) {
        List<Batch> batches = batchRepository.findAllByEnabledAndInstitutionId(Boolean.TRUE.booleanValue(),institutionId);
        List<BatchDTO> batchDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(batches)) {
            for (Batch batch : batches) {
                BatchDTO batchDTO = new BatchDTO();
                batchDTO.setId(batch.getId());
                batchDTO.setBatchName(batch.getBatchName());
                batchDTO.setStartDate(batch.getStartDate());
                batchDTO.setEndDate(batch.getEndDate());
                batchDTO.setInstitutionId(batch.getInstitutionId());
                batchDTO.setEnabled(batch.isEnabled());
                batchDTO.setStatus(batch.getStatus());
                batchDTOS.add(batchDTO);
            }
            return new ResponseEntity<>(batchDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
