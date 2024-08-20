package com.management.studentattendancesystem.base.service;

import com.dox.ail.base.rest.model.User;
import com.management.studentattendancesystem.base.rest.model.Response.GenericResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UsersService {
    ResponseEntity<User> createUser(User user);

    ResponseEntity<Void> deleteUser(String userId);

    ResponseEntity<List<User>> getAllUsers(String institutionId);

    ResponseEntity<User> updateUser(String userId, User user);

    ResponseEntity<User> getUserById(String userId);
}
