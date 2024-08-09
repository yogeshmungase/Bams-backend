package com.management.studentattendancesystem.base.rest.controller;

import com.management.studentattendancesystem.base.service.RolesService;
import com.dox.ail.base.rest.RolesApi;
import com.dox.ail.base.rest.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/amdil/roles")
public class RoleController implements RolesApi {


    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RolesService rolesService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return RolesApi.super.getRequest();
    }

    @PostMapping
    @Override
    public ResponseEntity<Role> createRole(Role role) {
        logger.info("ENTER RoleController:createRole() with details {}", role);
        return rolesService.createRole(role);
    }

    @Override
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable(value = "roleId") String roleId) {
        logger.info("ENTER RoleController:deleteRole() with details {}", roleId);
        return rolesService.deleteRole(roleId);
    }

    @GetMapping("/{roleId}")
    @Override
    public ResponseEntity<Role> getRoleById(@PathVariable(value = "roleId") String roleId) {
        logger.info("ENTER RoleController:getRoleById() with details {}", roleId);
        return rolesService.getRoleById(roleId);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<Role>> getRoles() {
        logger.info("ENTER RoleController:getRoles()");
        return rolesService.getRoles();
    }

    @PutMapping("/{roleId}")
    @Override
    public ResponseEntity<Role> updateRole(@PathVariable(value = "roleId") String roleId, @RequestBody Role role) {
        logger.info("ENTER RoleController:updateRole() with details roleId : {} and role details : {}", roleId, role);
        return rolesService.updateRole(roleId, role);
    }
}
