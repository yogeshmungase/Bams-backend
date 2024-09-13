package com.management.studentattendancesystem.base.service.impl;

import com.management.studentattendancesystem.base.db.model.Institution;
import com.management.studentattendancesystem.base.repository.InstitutionRepository;
import com.management.studentattendancesystem.base.repository.PermissionRepository;
import com.management.studentattendancesystem.base.repository.RoleRepository;
import com.management.studentattendancesystem.base.rest.mapper.UserMapper;
import com.management.studentattendancesystem.base.service.RolesService;
import com.management.studentattendancesystem.base.utils.constants.Constants;
import com.dox.ail.base.rest.model.Permission;
import com.dox.ail.base.rest.model.Role;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {


    private static Logger logger = LoggerFactory.getLogger(RolesServiceImpl.class);
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private InstitutionRepository  institutionRepository;

    @Override
    public ResponseEntity<Role> createRole(Role roleDto) {

        List<@Valid Permission> permissions = roleDto.getPermissions();
        if (!CollectionUtils.isEmpty(permissions)) {
            if (validatePermissionsInDB(permissions)) {
                com.management.studentattendancesystem.base.db.model.Role dbRole = UserMapper.convertToDBModelRole(roleDto);
                roleRepository.save(dbRole);
                logger.info("Role has been created with details : {}", dbRole);
                return new ResponseEntity<>(roleDto, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(roleDto, HttpStatus.BAD_REQUEST);

    }

    private boolean validatePermissionsInDB(List<Permission> permissions) {
        for (Permission permission : permissions) {
            com.management.studentattendancesystem.base.db.model.Permission byNameAndStatus = permissionRepository.findByName(permission.getName());

            logger.info("Permissions : {}", byNameAndStatus);
            if (null == byNameAndStatus) {
                logger.error("Permission not found in the database against name : {}", permission.getName());
                return false;
            }
        }
        return true;
    }

    @Override
    public ResponseEntity<Void> deleteRole(String roleId) {
        roleRepository.updateStatusByRoleId(Long.valueOf(roleId), Constants.INACTIVE);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Override
    public ResponseEntity<Role> getRoleById(String roleId) {

        com.management.studentattendancesystem.base.db.model.Role dbRole = roleRepository.findByIdAndStatus(Long.valueOf(roleId), Constants.ACTIVE);
        if (null != dbRole) {
            Role role = UserMapper.convertToRoleDto(dbRole);
            return new ResponseEntity<>(role, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Role(), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<Role>> getRoles(String instId) {
        Institution byInstitutionIdAndStatus = institutionRepository.findByInstitutionIdAndStatus(instId, Constants.ACTIVE);
        if (null != byInstitutionIdAndStatus) {
            Collection<com.management.studentattendancesystem.base.db.model.Role> dbRoles = byInstitutionIdAndStatus.getInst_roles();
            List<Role> rolesDto = UserMapper.convertToRoleDtos(dbRoles);
            return new ResponseEntity<>(rolesDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Role> updateRole(String roleId, Role role) {
        com.management.studentattendancesystem.base.db.model.Role dbRole = roleRepository.findByIdAndStatus(Long.valueOf(roleId), Constants.ACTIVE);
        if (null == dbRole) {
            return new ResponseEntity<>(role, HttpStatus.BAD_REQUEST);
        }

        logger.info("DB role before updating {}", dbRole);
        dbRole.setName(role.getName());
        dbRole.setPermissions(UserMapper.convertToDBPermissions(role.getPermissions()));
        roleRepository.save(dbRole);
        logger.info("DB role after updating {}", dbRole);

        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Role>> getRoles() {
        List<com.management.studentattendancesystem.base.db.model.Role> dbRoles = roleRepository.findAllByStatus(Constants.ACTIVE);
        if (!CollectionUtils.isEmpty(dbRoles)) {
            List<Role> rolesDto = UserMapper.convertToRoleDtos(dbRoles);
            return new ResponseEntity<>(rolesDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
