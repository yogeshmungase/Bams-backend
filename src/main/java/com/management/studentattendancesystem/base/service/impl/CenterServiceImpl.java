package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.db.model.Center;
import com.management.studentattendancesystem.base.repository.CenterRepository;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import com.management.studentattendancesystem.base.rest.model.request.CenterDTO;
import com.management.studentattendancesystem.base.service.CenterService;
import com.management.studentattendancesystem.base.utils.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CenterServiceImpl implements CenterService {

    private static final Logger logger = LoggerFactory.getLogger(CenterServiceImpl.class);
    @Autowired
    private CenterRepository centerRepository;

    @Override
    public ResponseEntity<GenericResponse> createCenter(CenterDTO centerDTO) {

        Center center = UserMapper.mapCenterFromDto(centerDTO);
        centerRepository.save(center);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus("SUCCESS");
        genericResponse.setMessage("Center added successfully");

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CenterDTO>> getCenters(String institutionId) {
        List<Center> centers = centerRepository.findAllByInstitutionIdAndStatus(institutionId, Constants.ACTIVE);
        List<CenterDTO> centerDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(centers)) {
            for (Center center : centers) {
                centerDTOList.add(UserMapper.mapCenterToDTO(center));
            }
            return new ResponseEntity<>(centerDTOList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<GenericResponse> deleteCenter(Long centerId) {
        Center center = centerRepository.findByIdAndStatus(centerId, Constants.ACTIVE);
        if (null != center) {
            center.setStatus(Constants.INACTIVE);
            centerRepository.save(center);
        }
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus("SUCCESS");
        genericResponse.setMessage("Center deleted successfully");
        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}

