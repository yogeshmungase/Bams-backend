package com.management.studenattendancesystem.base.service;

import com.dox.ail.base.rest.model.Permission;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PermissionsService {
    ResponseEntity<Permission> createPermission(Permission permission);

    ResponseEntity<List<Permission>> getPermissions();

    ResponseEntity<Permission> getPermissionById(String permissionId);

    ResponseEntity<Void> deletePermission(String permissionId);

    ResponseEntity<Permission> updatePermission(String permissionId, Permission permission);
}
