package com.management.studentattendancesystem.base.service;

import com.dox.ail.base.rest.model.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RolesService {
    ResponseEntity<Role> createRole(Role role);

    ResponseEntity<Void> deleteRole(String roleId);

    ResponseEntity<Role> getRoleById(String roleId);

    ResponseEntity<List<Role>> getRoles();

    ResponseEntity<Role> updateRole(String roleId, Role role);
}
