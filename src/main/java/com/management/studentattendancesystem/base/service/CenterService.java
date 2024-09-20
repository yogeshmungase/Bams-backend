package com.management.studentattendancesystem.base.service;

import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.CenterDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CenterService {
    ResponseEntity<GenericResponse> createCenter(CenterDTO centerDTO);

    ResponseEntity<List<CenterDTO>> getCenters(String institutionId);

    ResponseEntity<GenericResponse> deleteCenter(Long centerId);
}
