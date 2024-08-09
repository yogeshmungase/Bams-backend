package com.management.studentattendancesystem.base.rest.controller;

import com.management.studentattendancesystem.base.service.PermissionsService;
import com.dox.ail.base.rest.PermissionsApi;
import com.dox.ail.base.rest.model.Permission;
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
@RequestMapping(value = "/api/v1/amdil/permissions")
public class PermissionController implements PermissionsApi {


    private static Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionsService permissionsService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return PermissionsApi.super.getRequest();
    }

    @Override
    @PostMapping
    public ResponseEntity<Permission> createPermission(Permission permission) {
        return permissionsService.createPermission(permission);
    }

    @DeleteMapping("/{permissionId}")
    @Override
    public ResponseEntity<Void> deletePermission(@PathVariable(name = "permissionId") String permissionId) {
        return permissionsService.deletePermission(permissionId);
    }

    @GetMapping("/{permissionId}")
    @Override
    public ResponseEntity<Permission> getPermissionById(@PathVariable(name = "permissionId") String permissionId) {
        return permissionsService.getPermissionById(permissionId);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Permission>> getPermissions() {
        return permissionsService.getPermissions();
    }

    @PutMapping("/{permissionId}")
    @Override
    public ResponseEntity<Permission> updatePermission(@PathVariable(name = "permissionId") String permissionId, @RequestBody Permission permission) {
        return permissionsService.updatePermission(permissionId, permission);
    }
}
