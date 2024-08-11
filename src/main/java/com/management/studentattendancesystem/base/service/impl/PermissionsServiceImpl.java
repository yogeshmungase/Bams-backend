package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.repository.PermissionRepository;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.service.PermissionsService;
import com.management.studentattendancesystem.base.utils.constants.Constants;
import com.dox.ail.base.rest.model.Permission;
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
public class PermissionsServiceImpl implements PermissionsService {


    private static Logger logger = LoggerFactory.getLogger(PermissionsServiceImpl.class);
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public ResponseEntity<Permission> createPermission(Permission permissionDto) {
        com.management.studentattendancesystem.base.db.model.Permission permission = UserMapper.convertToDBPermission(permissionDto);
        permissionRepository.save(permission);
        return new ResponseEntity<>(permissionDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Permission>> getPermissions() {
        List<com.management.studentattendancesystem.base.db.model.Permission> allPermissions = permissionRepository.findAllByStatus(Constants.ACTIVE);
        List<Permission> permissions = UserMapper.convertToPermissionDtos(allPermissions);
        if (!CollectionUtils.isEmpty(permissions)) {
            return new ResponseEntity<>(permissions, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Permission> getPermissionById(String permissionId) {
        com.management.studentattendancesystem.base.db.model.Permission dbPermission = permissionRepository.findByIdAndStatus(Long.valueOf(permissionId), Constants.ACTIVE);
        if (null != dbPermission) {
            Permission permission = UserMapper.convertToPermissionDto(dbPermission);
            return new ResponseEntity<>(permission, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deletePermission(String permissionId) {
        permissionRepository.updateStatusByPermissionId(Long.valueOf(permissionId), Constants.INACTIVE);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Permission> updatePermission(String permissionId, Permission permission) {
        com.management.studentattendancesystem.base.db.model.Permission dbPermission = permissionRepository.findByIdAndStatus(Long.valueOf(permissionId), Constants.ACTIVE);
        if (null == dbPermission) {
            return new ResponseEntity<>(permission, HttpStatus.BAD_REQUEST);
        }

        logger.info("DB Permission before updating {}", dbPermission);
        dbPermission.setName(permission.getName());
        permissionRepository.save(dbPermission);
        logger.info("DB Permission after updating {}", dbPermission);

        return new ResponseEntity<>(permission, HttpStatus.OK);
    }
}
